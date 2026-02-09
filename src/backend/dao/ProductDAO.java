package backend.dao;

import backend.db.DB;
import backend.entity.Product;
import java.sql.*;
import java.util.*;

/** DAO for product table CRUD operations. */
public class ProductDAO {
  /** Inserts a new product and returns generated id. */
  public int create(Product p) throws SQLException {
    String sql = "INSERT INTO products(name,category,supplier_id,price,quantity,is_active) VALUES (?,?,?,?,?,1)";
    try (Connection c = DB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, p.getName());
      ps.setString(2, p.getCategory());
      ps.setInt(3, p.getSupplierId());
      ps.setDouble(4, p.getPrice());
      ps.setInt(5, p.getQuantity());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    }
  }

  /** Updates an existing product. */
  public boolean update(Product p) throws SQLException {
    String sql = "UPDATE products SET name=?, category=?, supplier_id=?, price=?, quantity=? WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, p.getName());
      ps.setString(2, p.getCategory());
      ps.setInt(3, p.getSupplierId());
      ps.setDouble(4, p.getPrice());
      ps.setInt(5, p.getQuantity());
      ps.setInt(6, p.getId());
      return ps.executeUpdate() > 0;
    }
  }

  /** Soft-deactivates a product by id. */
  public boolean deactivate(int productId) throws SQLException {
    String sql = "UPDATE products SET is_active=0 WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, productId);
      return ps.executeUpdate() > 0;
    }
  }

  /** Lists active products ordered by newest first. */
  public List<Product> listActive() throws SQLException {
    String sql = "SELECT id,name,category,supplier_id,price,quantity,is_active FROM products WHERE is_active=1 ORDER BY id DESC";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Product> out = new ArrayList<>();
      while (rs.next()) out.add(map(rs));
      return out;
    }
  }

  /** Searches active products by id or name. */
  public List<Product> searchActive(String q) throws SQLException {
    String sql = "SELECT id,name,category,supplier_id,price,quantity,is_active FROM products WHERE is_active=1 AND (CAST(id AS CHAR) LIKE ? OR name LIKE ?) ORDER BY id DESC";
    String like = "%" + q + "%";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like);
      ps.setString(2, like);
      try (ResultSet rs = ps.executeQuery()) {
        List<Product> out = new ArrayList<>();
        while (rs.next()) out.add(map(rs));
        return out;
      }
    }
  }

  /** Maps a result set row into a Product object. */
  private Product map(ResultSet rs) throws SQLException {
    return new Product(
      rs.getInt("id"),
      rs.getString("name"),
      rs.getString("category"),
      rs.getInt("supplier_id"),
      rs.getDouble("price"),
      rs.getInt("quantity"),
      rs.getInt("is_active") == 1
    );
  }
}
