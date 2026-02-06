package backend.dao;

import backend.db.DB;
import backend.entity.WarrantyRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarrantyDAO {
  public List<WarrantyRecord> listAll() throws SQLException {
    String sql = "SELECT id, product_id, invoice_item_id, warranty_months, start_date, end_date, status " +
        "FROM product_warranties ORDER BY id DESC";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<WarrantyRecord> out = new ArrayList<>();
      while (rs.next()) out.add(map(rs));
      return out;
    }
  }

  public List<WarrantyRecord> search(String keyword) throws SQLException {
    String sql = "SELECT id, product_id, invoice_item_id, warranty_months, start_date, end_date, status " +
        "FROM product_warranties WHERE status LIKE ? ORDER BY id DESC";
    String like = "%" + keyword + "%";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like);
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
        invoiceItemId,
        rs.getInt("warranty_months"),
        rs.getDate("start_date").toString(),
        rs.getDate("end_date").toString(),
        rs.getString("status")
    );
  }
}
