package backend.pages;

import backend.db.DB;
import backend.entity.InvoiceItem;
import backend.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Backend functions used ONLY by Billing JFrame.
 */
public class BillingBackend {

  /** Returns active product options for dropdowns (id -> name). */
  public Map<Integer, String> loadProductOptions() throws SQLException {
    try (Connection con = DB.getConnection()) {
      Columns cols = getColumns(con);
      String sql = cols.hasIsActive
          ? "SELECT product_id, name FROM products WHERE is_active=1 ORDER BY name"
          : "SELECT product_id, name FROM products ORDER BY name";
      return loadProductOptions(con, sql);
    }
  }

  /** Loads a single product by id (active if supported by schema). */
  public Product loadProductById(int productId) throws SQLException {
    if (productId <= 0) throw new IllegalArgumentException("Invalid product id");
    try (Connection con = DB.getConnection()) {
      Columns cols = getColumns(con);
      String sql = cols.hasIsActive
          ? "SELECT " + productSelect(cols) + " FROM products WHERE product_id=? AND is_active=1"
          : "SELECT " + productSelect(cols) + " FROM products WHERE product_id=?";
      try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, productId);
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) return null;
          return mapProduct(rs);
        }
      }
    }
  }

  /** Loads a single product by exact name (active if supported by schema). */
  public Product loadProductByName(String name) throws SQLException {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Product name is required");
    }
    try (Connection con = DB.getConnection()) {
      Columns cols = getColumns(con);
      String sql = cols.hasIsActive
          ? "SELECT " + productSelect(cols) + " FROM products WHERE is_active=1 AND name=? LIMIT 1"
          : "SELECT " + productSelect(cols) + " FROM products WHERE name=? LIMIT 1";
      try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, name.trim());
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) return null;
          return mapProduct(rs);
        }
      }
    }
  }

  /** Searches products by id or name (active if supported by schema). */
  public List<Product> searchProducts(String keyword) throws SQLException {
    if (keyword == null) keyword = "";
    keyword = keyword.trim();
    try (Connection con = DB.getConnection()) {
      Columns cols = getColumns(con);
      String sql = cols.hasIsActive
          ? "SELECT " + productSelect(cols) + " FROM products " +
            "WHERE is_active=1 AND (CAST(product_id AS CHAR) LIKE ? OR name LIKE ?) " +
            "ORDER BY product_id DESC"
          : "SELECT " + productSelect(cols) + " FROM products " +
            "WHERE (CAST(product_id AS CHAR) LIKE ? OR name LIKE ?) " +
            "ORDER BY product_id DESC";

      List<Product> out = new ArrayList<>();
      try (PreparedStatement ps = con.prepareStatement(sql)) {
        String like = "%" + keyword + "%";
        ps.setString(1, like);
        ps.setString(2, like);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            out.add(mapProduct(rs));
          }
        }
      }
      return out;
    }
  }

  /** Decrements stock if quantity is available. */
  public boolean reduceStock(int productId, int qty) throws SQLException {
    if (productId <= 0) throw new IllegalArgumentException("Invalid product id");
    if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");

    try (Connection con = DB.getConnection()) {
      Columns cols = getColumns(con);
      String sql = cols.hasIsActive
          ? "UPDATE products SET quantity = quantity - ? " +
            "WHERE product_id=? AND is_active=1 AND quantity >= ?"
          : "UPDATE products SET quantity = quantity - ? " +
            "WHERE product_id=? AND quantity >= ?";
      try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, qty);
        ps.setInt(2, productId);
        ps.setInt(3, qty);
        return ps.executeUpdate() > 0;
      }
    }
  }

  /** Creates invoice, items, optional warranties, and updates stock in one transaction. */
  public int createInvoice(List<InvoiceItem> items) throws SQLException {
    if (items == null || items.isEmpty()) throw new IllegalArgumentException("Invoice is empty");

    double total = 0.0;
    for (InvoiceItem item : items) {
      if (item.getQuantity() <= 0) throw new IllegalArgumentException("Invalid quantity");
      total += item.getTotal();
    }

    String updateStockSqlWithActive = "UPDATE products SET quantity = quantity - ? " +
        "WHERE product_id=? AND is_active=1 AND quantity >= ?";
    String updateStockSqlSimple = "UPDATE products SET quantity = quantity - ? " +
        "WHERE product_id=? AND quantity >= ?";
    String insertInvoiceSql = "INSERT INTO invoices (grand_total) VALUES (?)";
    String insertItemSql = "INSERT INTO invoice_items (invoice_id, product_id, product_name, quantity, unit_price, line_total) " +
        "VALUES (?,?,?,?,?,?)";
    String insertWarrantySql = "INSERT INTO product_warranties " +
        "(product_id, invoice_item_id, warranty_months, start_date, end_date, status) " +
        "VALUES (?,?,?,?,?,?)";

    try (Connection con = DB.getConnection()) {
      Columns cols = getColumns(con);
      boolean canCreateWarranty = cols.hasWarrantyMonths
          && hasColumn(con, "product_warranties", "warranty_months");
      String updateStockSql = cols.hasIsActive ? updateStockSqlWithActive : updateStockSqlSimple;

      con.setAutoCommit(false);
      try {
        for (InvoiceItem item : items) {
          try (PreparedStatement ps = con.prepareStatement(updateStockSql)) {
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            int updated = ps.executeUpdate();
            if (updated == 0) {
              throw new SQLException("Insufficient stock for product: " + item.getProductId());
            }
          }
        }

        int invoiceId;
        try (PreparedStatement ps = con.prepareStatement(insertInvoiceSql, Statement.RETURN_GENERATED_KEYS)) {
          ps.setDouble(1, total);
          int affected = ps.executeUpdate();
          if (affected == 0) throw new SQLException("Failed to create invoice");
          try (ResultSet keys = ps.getGeneratedKeys()) {
            if (!keys.next()) throw new SQLException("Invoice id not returned");
            invoiceId = keys.getInt(1);
          }
        }

        try (PreparedStatement psItem = con.prepareStatement(insertItemSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psWarranty = con.prepareStatement(insertWarrantySql)) {
          for (InvoiceItem item : items) {
            psItem.setInt(1, invoiceId);
            psItem.setInt(2, item.getProductId());
            psItem.setString(3, item.getName());
            psItem.setInt(4, item.getQuantity());
            psItem.setDouble(5, item.getUnitPrice());
            psItem.setDouble(6, item.getTotal());
            int affected = psItem.executeUpdate();
            if (affected == 0) throw new SQLException("Failed to create invoice item");

            int itemId;
            try (ResultSet keys = psItem.getGeneratedKeys()) {
              if (!keys.next()) throw new SQLException("Invoice item id not returned");
              itemId = keys.getInt(1);
            }

            if (canCreateWarranty) {
              int warrantyMonths = item.getWarrantyMonths();
              if (warrantyMonths > 0) {
                psWarranty.setInt(1, item.getProductId());
                psWarranty.setInt(2, itemId);
                psWarranty.setInt(3, warrantyMonths);
                psWarranty.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                psWarranty.setDate(5, addMonths(new java.sql.Date(System.currentTimeMillis()), warrantyMonths));
                psWarranty.setString(6, "Active");
                psWarranty.addBatch();
              }
            }
          }
          if (canCreateWarranty) {
            psWarranty.executeBatch();
          }
        }

        con.commit();
        return invoiceId;
      } catch (Exception ex) {
        con.rollback();
        if (ex instanceof SQLException) throw (SQLException) ex;
        throw new SQLException("Failed to create invoice", ex);
      } finally {
        con.setAutoCommit(true);
      }
    }
  }

  /** Adds months to a SQL date. */
  private java.sql.Date addMonths(java.sql.Date start, int months) {
    java.util.Calendar cal = java.util.Calendar.getInstance();
    cal.setTime(start);
    cal.add(java.util.Calendar.MONTH, months);
    return new java.sql.Date(cal.getTimeInMillis());
  }

  private static final class Columns {
    final boolean hasIsActive;
    final boolean hasCategory;
    final boolean hasSupplierId;
    final boolean hasCostPrice;
    final boolean hasWarrantyMonths;

    Columns(boolean hasIsActive, boolean hasCategory, boolean hasSupplierId, boolean hasCostPrice, boolean hasWarrantyMonths) {
      this.hasIsActive = hasIsActive;
      this.hasCategory = hasCategory;
      this.hasSupplierId = hasSupplierId;
      this.hasCostPrice = hasCostPrice;
      this.hasWarrantyMonths = hasWarrantyMonths;
    }
  }

  /** Detects available columns for backward-compatible queries. */
  private Columns getColumns(Connection con) {
    boolean hasIsActive = hasColumn(con, "products", "is_active");
    boolean hasCategory = hasColumn(con, "products", "category");
    boolean hasSupplierId = hasColumn(con, "products", "supplier_id");
    boolean hasCostPrice = hasColumn(con, "products", "cost_price");
    boolean hasWarrantyMonths = hasColumn(con, "products", "warranty_months");
    return new Columns(hasIsActive, hasCategory, hasSupplierId, hasCostPrice, hasWarrantyMonths);
  }

  /** Checks if a column exists by running a lightweight query. */
  private boolean hasColumn(Connection con, String table, String column) {
    String sql = "SELECT " + column + " FROM " + table + " LIMIT 1";
    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      return true;
    } catch (SQLException ex) {
      return false;
    }
  }

  /** Builds a SELECT list based on available schema columns. */
  private String productSelect(Columns cols) {
    String categoryExpr = cols.hasCategory ? "category" : "'' AS category";
    String supplierExpr = cols.hasSupplierId ? "supplier_id" : "0 AS supplier_id";
    String activeExpr = cols.hasIsActive ? "is_active" : "1 AS is_active";
    String costExpr = cols.hasCostPrice ? "cost_price" : "0 AS cost_price";
    String warrantyExpr = cols.hasWarrantyMonths ? "warranty_months" : "0 AS warranty_months";
    return "product_id, name, " + categoryExpr + ", " + supplierExpr + ", " + costExpr + ", price, quantity, " + warrantyExpr + ", " + activeExpr;
  }

  /** Loads product options using the given SQL. */
  private Map<Integer, String> loadProductOptions(Connection con, String sql) throws SQLException {
    Map<Integer, String> out = new LinkedHashMap<>();
    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.put(rs.getInt("product_id"), rs.getString("name"));
      }
    }
    return out;
  }

  /** Maps a result set row into a Product object. */
  private Product mapProduct(ResultSet rs) throws SQLException {
    boolean isActive = rs.getInt("is_active") == 1;
    return new Product(
        rs.getInt("product_id"),
        rs.getString("name"),
        rs.getString("category"),
        rs.getInt("supplier_id"),
        rs.getDouble("cost_price"),
        rs.getDouble("price"),
        rs.getInt("quantity"),
        rs.getInt("warranty_months"),
        isActive
    );
  }
}
