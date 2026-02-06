package backend.pages;

import backend.db.DB;
import backend.entity.Supplier;

import java.sql.*;

/**
 * Backend functions used ONLY by Addsupplierr JFrame.
 */
public class AddSupplierBackend {

  public int addSupplier(Supplier s) throws SQLException {
    if (s == null) throw new IllegalArgumentException("Supplier is null");
    if (s.getName() == null || s.getName().trim().isEmpty()) throw new IllegalArgumentException("Supplier name is required");

    String sql = "INSERT INTO suppliers (supplier_name, contact, is_active) VALUES (?,?,1)";

    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, s.getName().trim());
      ps.setString(2, nullableTrim(s.getPhone()));

      int affected = ps.executeUpdate();
      if (affected == 0) throw new SQLException("Insert failed (no rows affected)");

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) return keys.getInt(1);
      }
    }
    throw new SQLException("Insert succeeded but supplier_id not returned");
  }

  private String nullableTrim(String s) {
    if (s == null) return null;
    String t = s.trim();
    return t.isEmpty() ? null : t;
  }
}
