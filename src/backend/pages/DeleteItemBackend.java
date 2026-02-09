package backend.pages;

import backend.db.DB;
import backend.entity.Product;

import java.sql.*;

/**
 * Backend functions used ONLY by DeleteItem JFrame.
 * This uses soft-delete (sets is_active=0) to avoid losing data.
 */
public class DeleteItemBackend {

  /** Soft-deactivates a product by id. */
  public boolean deactivateProduct(int productId) throws SQLException {
    if (productId <= 0) throw new IllegalArgumentException("Invalid product id");
    String sql = "UPDATE products SET is_active=0 WHERE product_id=?";
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, productId);
      return ps.executeUpdate() > 0;
    }
  }

  /** Soft-deactivates a supplier by id. */
  public boolean deactivateSupplier(int supplierId) throws SQLException {
    if (supplierId <= 0) throw new IllegalArgumentException("Invalid supplier id");
    String sql = "UPDATE suppliers SET is_active=0 WHERE supplier_id=?";
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, supplierId);
      return ps.executeUpdate() > 0;
    }
  }

  /** Loads a product by id (active or inactive). */
  public Product loadProductById(int productId) throws SQLException {
    if (productId <= 0) throw new IllegalArgumentException("Invalid product id");
    String sql = "SELECT product_id, name, category, quantity, price, is_active FROM products WHERE product_id=?";
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, productId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        return new Product(
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getString("category"),
            0,
            rs.getDouble("price"),
            rs.getInt("quantity"),
            rs.getInt("is_active") == 1
        );
      }
    }
  }
}
