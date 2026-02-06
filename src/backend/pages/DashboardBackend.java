package backend.pages;

import backend.db.DB;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Backend functions used ONLY by Dashboard JFrame.
 */
public class DashboardBackend {

  /**
   * Returns some simple counts for dashboard widgets.
   * Keys: products, suppliers, users
   */
  public Map<String, Integer> loadCounts() throws SQLException {
    Map<String, Integer> out = new LinkedHashMap<>();
    out.put("products", singleInt("SELECT COUNT(*) FROM products WHERE is_active=1"));
    out.put("suppliers", singleInt("SELECT COUNT(*) FROM suppliers WHERE is_active=1"));
    out.put("users", singleInt("SELECT COUNT(*) FROM users WHERE is_active=1"));
    return out;
  }

  /**
   * Counts products with quantity <= threshold.
   */
  public int countLowStock(int threshold) throws SQLException {
    String sql = "SELECT COUNT(*) FROM products WHERE is_active=1 AND quantity <= ?";
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, threshold);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    }
  }

  private int singleInt(String sql) throws SQLException {
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      return rs.next() ? rs.getInt(1) : 0;
    }
  }
}
