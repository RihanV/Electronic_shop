package backend.dao;

import backend.db.DB;
import backend.entity.Delivery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
  public int create(Delivery d) throws SQLException {
    String sql = "INSERT INTO deliveries(customer_name, customer_phone, address, invoice_id, status) VALUES (?,?,?,?,?)";
    try (Connection c = DB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, d.getCustomerName());
      ps.setString(2, d.getCustomerPhone());
      ps.setString(3, d.getAddress());
      if (d.getInvoiceId() == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, d.getInvoiceId());
      ps.setString(5, d.getStatus());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    }
  }

  public boolean updateStatus(int id, String status) throws SQLException {
    String sql = "UPDATE deliveries SET status=? WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, status);
      ps.setInt(2, id);
      return ps.executeUpdate() > 0;
    }
  }

  public List<Delivery> listAll() throws SQLException {
    String sql = "SELECT id, customer_name, customer_phone, address, invoice_id, status FROM deliveries ORDER BY id DESC";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Delivery> out = new ArrayList<>();
      while (rs.next()) {
        out.add(map(rs));
      }
      return out;
    }
  }

  public List<Delivery> search(String keyword) throws SQLException {
    String sql = "SELECT id, customer_name, customer_phone, address, invoice_id, status FROM deliveries " +
        "WHERE customer_name LIKE ? OR customer_phone LIKE ? OR status LIKE ? ORDER BY id DESC";
    String like = "%" + keyword + "%";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like);
      ps.setString(2, like);
      ps.setString(3, like);
      try (ResultSet rs = ps.executeQuery()) {
        List<Delivery> out = new ArrayList<>();
        while (rs.next()) out.add(map(rs));
        return out;
      }
    }
  }

  private Delivery map(ResultSet rs) throws SQLException {
    Integer invoiceId = rs.getObject("invoice_id") == null ? null : rs.getInt("invoice_id");
    return new Delivery(
        rs.getInt("id"),
        rs.getString("customer_name"),
        rs.getString("customer_phone"),
        rs.getString("address"),
        invoiceId,
        rs.getString("status")
    );
  }
}
