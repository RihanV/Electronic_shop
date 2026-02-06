package backend.dao;

import backend.db.DB;
import backend.entity.Supplier;
import java.sql.*;
import java.util.*;

public class SupplierDAO {
  public int create(Supplier s) throws SQLException {
    String sql = "INSERT INTO suppliers(name,phone,email,address,is_active) VALUES (?,?,?,?,1)";
    try (Connection c = DB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, s.getName());
      ps.setString(2, s.getPhone());
      ps.setString(3, s.getEmail());
      ps.setString(4, s.getAddress());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
    }
  }

  public List<Supplier> listActive() throws SQLException {
    String sql = "SELECT id,name,phone,email,address,is_active FROM suppliers WHERE is_active=1 ORDER BY name";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Supplier> out = new ArrayList<>();
      while (rs.next()) out.add(map(rs));
      return out;
    }
  }

  public boolean deactivate(int supplierId) throws SQLException {
    String sql = "UPDATE suppliers SET is_active=0 WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, supplierId);
      return ps.executeUpdate() > 0;
    }
  }

  public Supplier findByName(String name) throws SQLException {
    String sql = "SELECT id,name,phone,email,address,is_active FROM suppliers WHERE name=? LIMIT 1";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, name);
      try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
    }
  }

  private Supplier map(ResultSet rs) throws SQLException {
    return new Supplier(
      rs.getInt("id"),
      rs.getString("name"),
      rs.getString("phone"),
      rs.getString("email"),
      rs.getString("address"),
      rs.getInt("is_active") == 1
    );
  }
}
