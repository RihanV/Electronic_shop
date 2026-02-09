package backend.pages;

import backend.db.DB;
import backend.entity.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Backend functions used ONLY by SupplierManagement JFrame.
 * (Typically contains actions like deactivate supplier, etc.)
 */
public class SupplierManagementBackend {

  /** Loads active suppliers for management views. */
  public List<Supplier> loadSuppliers() throws SQLException {
    String sql = "SELECT supplier_id, supplier_name, contact, is_active FROM suppliers WHERE is_active=1 ORDER BY supplier_name";
    List<Supplier> out = new ArrayList<>();
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        out.add(new Supplier(
            rs.getInt("supplier_id"),
            rs.getString("supplier_name"),
            rs.getString("contact"),
            null,
            null,
            rs.getInt("is_active") == 1
        ));
      }
    }
    return out;
  }

  /** Soft-deactivates a supplier by id. */
  public boolean deactivateSupplier(int supplierId) throws SQLException {
    if (supplierId <= 0) throw new IllegalArgumentException("Invalid supplier id");
    String sql = "UPDATE suppliers SET is_active=0 WHERE supplier_id=?";
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, supplierId);
      return ps.executeUpdate() > 0;
    }
  }

  /** Updates supplier name/contact by id. */
  public boolean updateSupplier(int supplierId, String name, String contact) throws SQLException {
    if (supplierId <= 0) throw new IllegalArgumentException("Invalid supplier id");
    if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Supplier name is required");

    String sql = "UPDATE suppliers SET supplier_name=?, contact=? WHERE supplier_id=?";
    try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, name.trim());
      ps.setString(2, nullableTrim(contact));
      ps.setInt(3, supplierId);
      return ps.executeUpdate() > 0;
    }
  }

  /** Returns trimmed string or null if blank. */
  private String nullableTrim(String s) {
    if (s == null) return null;
    String t = s.trim();
    return t.isEmpty() ? null : t;
  }
}
