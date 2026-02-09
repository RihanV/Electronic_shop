package backend.dao;

import backend.db.DB;
import backend.entity.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO for category table CRUD operations. */
public class CategoryDAO {
  /** Inserts a new category and returns generated id. */
  public int create(String name) throws SQLException {
    String sql = "INSERT INTO categories(name,is_active) VALUES (?,1)";
    try (Connection c = DB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, name);
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    }
  }

  /** Renames a category by id. */
  public boolean rename(int id, String name) throws SQLException {
    String sql = "UPDATE categories SET name=? WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, name);
      ps.setInt(2, id);
      return ps.executeUpdate() > 0;
    }
  }

  /** Soft-deactivates a category by id. */
  public boolean deactivate(int id) throws SQLException {
    String sql = "UPDATE categories SET is_active=0 WHERE id=?";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    }
  }

  /** Lists active categories. */
  public List<Category> listActive() throws SQLException {
    String sql = "SELECT id,name,is_active FROM categories WHERE is_active=1 ORDER BY name";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Category> out = new ArrayList<>();
      while (rs.next()) {
        out.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("is_active") == 1));
      }
      return out;
    }
  }

  /** Searches categories by name keyword. */
  public List<Category> search(String keyword) throws SQLException {
    String sql = "SELECT id,name,is_active FROM categories WHERE name LIKE ? ORDER BY name";
    String like = "%" + keyword + "%";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, like);
      try (ResultSet rs = ps.executeQuery()) {
        List<Category> out = new ArrayList<>();
        while (rs.next()) {
          out.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("is_active") == 1));
        }
        return out;
      }
    }
  }
}
