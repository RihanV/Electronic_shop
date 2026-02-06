package backend.pages;

import backend.db.DB;
import backend.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Backend functions used ONLY by stock JFrame.
 */
public class StockBackend {

  public List<Product> loadAllProducts() throws SQLException {
    String sql = "SELECT product_id, name, category, supplier_id, price, quantity, is_active FROM products WHERE is_active=1 ORDER BY product_id DESC";
    List<Product> out = new ArrayList<>();
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.add(new Product(
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getString("category"),
            rs.getInt("supplier_id"),
            rs.getDouble("price"),
            rs.getInt("quantity"),
            rs.getInt("is_active") == 1
        ));
      }
    }
    return out;
  }

  public List<Product> searchProducts(String keyword) throws SQLException {
    if (keyword == null) keyword = "";
    keyword = keyword.trim();
    if (keyword.isEmpty()) return loadAllProducts();

    String sql = "SELECT product_id, name, category, supplier_id, price, quantity, is_active " +
        "FROM products WHERE is_active=1 AND (name LIKE ? OR category LIKE ?) ORDER BY product_id DESC";

    List<Product> out = new ArrayList<>();
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

      String like = "%" + keyword + "%";
      ps.setString(1, like);
      ps.setString(2, like);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          out.add(new Product(
              rs.getInt("product_id"),
              rs.getString("name"),
              rs.getString("category"),
              rs.getInt("supplier_id"),
              rs.getDouble("price"),
              rs.getInt("quantity"),
              rs.getInt("is_active") == 1
          ));
        }
      }
    }
    return out;
  }
}
