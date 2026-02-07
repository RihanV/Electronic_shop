package backend.dao;

import backend.db.DB;
import backend.entity.WarrantyRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarrantyDAO {
  public List<WarrantyRecord> listAll() throws SQLException {
    String sql = "SELECT pw.id, pw.product_id, p.name AS product_name, pw.invoice_item_id, " +
        "pw.warranty_months, pw.start_date, pw.end_date, pw.status " +
        "FROM product_warranties pw " +
        "LEFT JOIN products p ON p.product_id = pw.product_id " +
        "ORDER BY pw.id DESC";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<WarrantyRecord> out = new ArrayList<>();
      while (rs.next()) out.add(map(rs));
      return out;
    }
  }

  public List<WarrantyRecord> search(String keyword) throws SQLException {
    String sql = "SELECT pw.id, pw.product_id, p.name AS product_name, pw.invoice_item_id, " +
        "pw.warranty_months, pw.start_date, pw.end_date, pw.status " +
        "FROM product_warranties pw " +
        "LEFT JOIN products p ON p.product_id = pw.product_id " +
        "WHERE pw.status LIKE ? OR p.name LIKE ? OR CAST(pw.product_id AS CHAR) LIKE ? " +
        "ORDER BY pw.id DESC";
    String like = "%" + keyword + "%";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like);
      ps.setString(2, like);
      ps.setString(3, like);
      try (ResultSet rs = ps.executeQuery()) {
        List<WarrantyRecord> out = new ArrayList<>();
        while (rs.next()) out.add(map(rs));
        return out;
      }
    }
  }

  private WarrantyRecord map(ResultSet rs) throws SQLException {
    Integer invoiceItemId = rs.getObject("invoice_item_id") == null ? null : rs.getInt("invoice_item_id");
    return new WarrantyRecord(
        rs.getInt("id"),
        rs.getInt("product_id"),
        rs.getString("product_name"),
        invoiceItemId,
        rs.getInt("warranty_months"),
        rs.getDate("start_date").toString(),
        rs.getDate("end_date").toString(),
        rs.getString("status")
    );
  }
}
