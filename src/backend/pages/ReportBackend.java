package backend.pages;

import backend.db.DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportBackend {
  public List<Object[]> lowStock(int threshold) throws SQLException {
    if (threshold < 0) threshold = 0;
    String sqlPid = "SELECT product_id, name, quantity FROM products WHERE is_active=1 AND quantity <= ? ORDER BY quantity ASC";
    String sqlId = "SELECT id, name, quantity FROM products WHERE is_active=1 AND quantity <= ? ORDER BY quantity ASC";
    try (Connection con = DB.getConnection()) {
      try {
        return runLowStock(con, sqlPid, threshold);
      } catch (SQLException ex) {
        return runLowStock(con, sqlId, threshold);
      }
    }
  }

  private List<Object[]> runLowStock(Connection con, String sql, int threshold) throws SQLException {
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, threshold);
      try (ResultSet rs = ps.executeQuery()) {
        List<Object[]> out = new ArrayList<>();
        while (rs.next()) {
          out.add(new Object[] { rs.getInt(1), rs.getString("name"), rs.getInt("quantity") });
        }
        return out;
      }
    }
  }

  public List<Object[]> topSelling(int limit) throws SQLException {
    if (limit <= 0) limit = 10;
    String sql = "SELECT product_id, product_name, SUM(quantity) AS qty " +
        "FROM invoice_items GROUP BY product_id, product_name ORDER BY qty DESC LIMIT ?";
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, limit);
      try (ResultSet rs = ps.executeQuery()) {
        List<Object[]> out = new ArrayList<>();
        while (rs.next()) {
          out.add(new Object[] { rs.getInt("product_id"), rs.getString("product_name"), rs.getInt("qty") });
        }
        return out;
      }
    }
  }

  public List<Object[]> profitReport() throws SQLException {
    try (Connection con = DB.getConnection()) {
      String productIdCol = hasColumn(con, "products", "product_id") ? "product_id" : "id";
      boolean hasCostPrice = hasColumn(con, "products", "cost_price");
      String costExpr = hasCostPrice ? "p.cost_price" : "0";
      String sql = "SELECT p." + productIdCol + ", p.name, " +
          "SUM((ii.unit_price - " + costExpr + ") * ii.quantity) AS profit " +
          "FROM invoice_items ii JOIN products p ON p." + productIdCol + " = ii.product_id " +
          "GROUP BY p." + productIdCol + ", p.name ORDER BY profit DESC";
      return runProfit(con, sql);
    }
  }

  private List<Object[]> runProfit(Connection con, String sql) throws SQLException {
    try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Object[]> out = new ArrayList<>();
      while (rs.next()) {
        out.add(new Object[] { rs.getInt(1), rs.getString("name"), rs.getDouble("profit") });
      }
      return out;
    }
  }

  private boolean hasColumn(Connection con, String table, String column) throws SQLException {
    DatabaseMetaData meta = con.getMetaData();
    try (ResultSet rs = meta.getColumns(con.getCatalog(), null, table, column)) {
      if (rs.next()) return true;
    }
    try (ResultSet rs = meta.getColumns(con.getCatalog(), null, table.toUpperCase(), column.toUpperCase())) {
      return rs.next();
    }
  }
}
