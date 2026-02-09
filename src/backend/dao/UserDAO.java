package backend.dao;

import backend.db.DB;
import backend.entity.User;
import java.sql.*;

/** DAO for user authentication. */
public class UserDAO {
  /** Returns a user if username/password is valid and active, otherwise null. */
  public User authenticate(String username, String password) throws SQLException {
    String sql = "SELECT id,username,is_active FROM users WHERE username=? AND password=? AND is_active=1 LIMIT 1";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, username);
      ps.setString(2, password);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("is_active")==1);
      }
    }
  }
}
