package backend.pages;

import backend.db.DB;
import backend.entity.Product;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Backend functions used ONLY by AddProduct JFrame.
 */
public class AddProductBackend {

  /**
   * Returns supplier options: supplier_id -> supplier_name (active suppliers only).
   */
  public Map<Integer, String> loadSupplierOptions() throws SQLException {
    String sql = "SELECT supplier_id, supplier_name FROM suppliers WHERE is_active=1 ORDER BY supplier_name";
    Map<Integer, String> out = new LinkedHashMap<>();

    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.put(rs.getInt("supplier_id"), rs.getString("supplier_name"));
      }
    }
    return out;
  }

  /**
   * Returns active category names.
   */
  public java.util.List<String> loadCategoryOptions() throws SQLException {
    String sql = "SELECT name FROM categories WHERE is_active=1 ORDER BY name";
    java.util.List<String> out = new java.util.ArrayList<>();
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        String name = rs.getString(1);
        if (name != null && !name.trim().isEmpty()) out.add(name);
      }
    }
    return out;
  }

  /**
   * Inserts a product and returns the generated product_id.
   */
  public int addProduct(Product p) throws SQLException {
    if (p == null) throw new IllegalArgumentException("Product is null");
    if (p.getName() == null || p.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Product name is required");
    }
    if (p.getCategory() == null || p.getCategory().trim().isEmpty()) {
      throw new IllegalArgumentException("Category is required");
    }
    if (p.getSupplierId() <= 0) {
      throw new IllegalArgumentException("Supplier is required");
    }
    if (p.getPrice() < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    }
    if (p.getQuantity() < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    String sql = "INSERT INTO products (name, category, supplier_id, price, quantity, is_active) VALUES (?,?,?,?,?,1)";

    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, p.getName().trim());
      ps.setString(2, p.getCategory().trim());
      ps.setInt(3, p.getSupplierId());
      ps.setDouble(4, p.getPrice());
      ps.setInt(5, p.getQuantity());

      int affected = ps.executeUpdate();
      if (affected == 0) throw new SQLException("Insert failed (no rows affected)");

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) return keys.getInt(1);
      }
    }
    throw new SQLException("Insert succeeded but product_id not returned");
  }
}
