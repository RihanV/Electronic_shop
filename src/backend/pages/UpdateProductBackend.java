package backend.pages;

import backend.db.DB;
import backend.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Backend functions used ONLY by UpdateProduct JFrame.
 */
public class UpdateProductBackend {

  /** Loads active product options (id -> name), with schema fallback. */
  public Map<Integer, String> loadProductOptions() throws SQLException {
    String sqlWithActive = "SELECT product_id, name FROM products WHERE is_active=1 ORDER BY name";
    String sqlSimple = "SELECT product_id, name FROM products ORDER BY name";
    try (Connection con = DB.getConnection()) {
      try {
        return loadProductOptions(con, sqlWithActive);
      } catch (SQLException ex) {
        return loadProductOptions(con, sqlSimple);
      }
    }
  }

  /** Loads supplier options (id -> name), with schema fallback. */
  public Map<Integer, String> loadSupplierOptions() throws SQLException {
    String sqlWithActive = "SELECT supplier_id, supplier_name FROM suppliers WHERE is_active=1 ORDER BY supplier_name";
    String sqlSimple = "SELECT supplier_id, supplier_name FROM suppliers ORDER BY supplier_name";
    try (Connection con = DB.getConnection()) {
      try {
        return loadSupplierOptions(con, sqlWithActive);
      } catch (SQLException ex) {
        return loadSupplierOptions(con, sqlSimple);
      }
    }
  }

  /** Loads active category names. */
  public List<String> loadCategoryOptions() throws SQLException {
    String sql = "SELECT name FROM categories WHERE is_active=1 ORDER BY name";
    try (Connection con = DB.getConnection()) {
      return loadCategoryOptions(con, sql);
    }
  }

  /** Loads a product by id, with schema fallback for is_active. */
  public Product loadProductById(int productId) throws SQLException {
    String sqlWithActive = "SELECT product_id, name, category, supplier_id, price, quantity, is_active FROM products WHERE product_id=?";
    String sqlSimple = "SELECT product_id, name, category, supplier_id, price, quantity FROM products WHERE product_id=?";
    try (Connection con = DB.getConnection()) {
      try {
        return loadProductById(con, sqlWithActive, productId, true);
      } catch (SQLException ex) {
        return loadProductById(con, sqlSimple, productId, false);
      }
    }
  }

  /** Validates and updates a product record. */
  public boolean updateProduct(Product p) throws SQLException {
    if (p == null) throw new IllegalArgumentException("Product is null");
    if (p.getId() <= 0) throw new IllegalArgumentException("Invalid product id");
    if (p.getName() == null || p.getName().trim().isEmpty()) throw new IllegalArgumentException("Name is required");
    if (p.getCategory() == null || p.getCategory().trim().isEmpty()) throw new IllegalArgumentException("Category is required");
    if (p.getSupplierId() <= 0) throw new IllegalArgumentException("Supplier is required");
    if (p.getPrice() < 0) throw new IllegalArgumentException("Price cannot be negative");
    if (p.getQuantity() < 0) throw new IllegalArgumentException("Quantity cannot be negative");

    String sql = "UPDATE products SET name=?, category=?, supplier_id=?, price=?, quantity=? WHERE product_id=?";
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, p.getName().trim());
      ps.setString(2, p.getCategory().trim());
      ps.setInt(3, p.getSupplierId());
      ps.setDouble(4, p.getPrice());
      ps.setInt(5, p.getQuantity());
      ps.setInt(6, p.getId());
      return ps.executeUpdate() > 0;
    }
  }

  /** Executes product options query. */
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

  /** Executes supplier options query. */
  private Map<Integer, String> loadSupplierOptions(Connection con, String sql) throws SQLException {
    Map<Integer, String> out = new LinkedHashMap<>();
    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.put(rs.getInt("supplier_id"), rs.getString("supplier_name"));
      }
    }
    return out;
  }

  /** Executes category options query. */
  private List<String> loadCategoryOptions(Connection con, String sql) throws SQLException {
    List<String> out = new ArrayList<>();
    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        String category = rs.getString(1);
        if (category != null && !category.trim().isEmpty()) {
          out.add(category);
        }
      }
    }
    return out;
  }

  /** Executes product-by-id query and maps to Product. */
  private Product loadProductById(Connection con, String sql, int productId, boolean hasIsActive) throws SQLException {
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, productId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        boolean isActive = hasIsActive ? rs.getInt("is_active") == 1 : true;
        return new Product(
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getString("category"),
            rs.getInt("supplier_id"),
            rs.getDouble("price"),
            rs.getInt("quantity"),
            isActive
        );
      }
    }
  }
}
