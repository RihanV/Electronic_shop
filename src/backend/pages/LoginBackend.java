package backend.pages;

import backend.db.DB;

import java.sql.*;

/**
 * Backend functions used ONLY by Login JFrame.
 */
public class LoginBackend {

  /**
   * Returns true if username/password is valid and active.
   */
  public boolean authenticate(String username, String password) throws SQLException {
    if (username == null || username.trim().isEmpty()) {
      throw new IllegalArgumentException("Username is required");
    }
    if (password == null || password.trim().isEmpty()) {
      throw new IllegalArgumentException("Password is required");
    }

    // Some schemas have an `is_active` column, others don't.
    // We try the stricter query first, then fall back to a simple check.
    String sqlWithActive = "SELECT 1 FROM users WHERE username=? AND password=? AND is_active=1 LIMIT 1";
    String sqlSimple = "SELECT 1 FROM users WHERE username=? AND password=? LIMIT 1";

    try (Connection con = DB.getConnection()) {
      try {
        return run(con, sqlWithActive, username, password);
      } catch (SQLException ex) {
        // Unknown column 'is_active' or similar -> fallback
        return run(con, sqlSimple, username, password);
      }
    }
  }

  private boolean run(Connection con, String sql, String username, String password) throws SQLException {
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, username.trim());
      ps.setString(2, password.trim());
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }
}
