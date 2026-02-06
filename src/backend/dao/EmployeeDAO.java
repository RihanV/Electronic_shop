package backend.dao;

import backend.db.DB;
import backend.entity.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
  public int create(Employee e) throws SQLException {
    String sql = "INSERT INTO employees(name, role, phone, email, is_active) VALUES (?,?,?,?,1)";
    try (Connection c = DB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, e.getName());
      ps.setString(2, e.getRole());
      ps.setString(3, e.getPhone());
      ps.setString(4, e.getEmail());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    }
  }

  public boolean update(Employee e) throws SQLException {
    String sql = "UPDATE employees SET name=?, role=?, phone=?, email=? WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, e.getName());
      ps.setString(2, e.getRole());
      ps.setString(3, e.getPhone());
      ps.setString(4, e.getEmail());
      ps.setInt(5, e.getId());
      return ps.executeUpdate() > 0;
    }
  }

  public boolean deactivate(int id) throws SQLException {
    String sql = "UPDATE employees SET is_active=0 WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    }
  }

  public List<Employee> listActive() throws SQLException {
    String sql = "SELECT id,name,role,phone,email,is_active FROM employees WHERE is_active=1 ORDER BY id DESC";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Employee> out = new ArrayList<>();
      while (rs.next()) out.add(map(rs));
      return out;
    }
  }

  public List<Employee> search(String keyword) throws SQLException {
    String sql = "SELECT id,name,role,phone,email,is_active FROM employees " +
        "WHERE name LIKE ? OR role LIKE ? OR phone LIKE ? ORDER BY id DESC";
    String like = "%" + keyword + "%";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like);
      ps.setString(2, like);
      ps.setString(3, like);
      try (ResultSet rs = ps.executeQuery()) {
        List<Employee> out = new ArrayList<>();
        while (rs.next()) out.add(map(rs));
        return out;
      }
    }
  }

  private Employee map(ResultSet rs) throws SQLException {
    return new Employee(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("role"),
        rs.getString("phone"),
        rs.getString("email"),
        rs.getInt("is_active") == 1
    );
  }
}
