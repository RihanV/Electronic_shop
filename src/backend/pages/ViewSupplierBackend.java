package backend.pages;

import backend.db.DB;
import backend.entity.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Backend functions used ONLY by ViewSupplier JFrame.
 */
public class ViewSupplierBackend {

  /** Loads all active suppliers. */
  public List<Supplier> loadAllSuppliers() throws SQLException {
    String sql = "SELECT supplier_id, supplier_name, contact, is_active FROM suppliers WHERE is_active=1 ORDER BY supplier_id DESC";
    List<Supplier> out = new ArrayList<>();
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
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

  /** Searches active suppliers by name or contact. */
  public List<Supplier> searchSuppliers(String keyword) throws SQLException {
    if (keyword == null) keyword = "";
    keyword = keyword.trim();
    if (keyword.isEmpty()) return loadAllSuppliers();

    String sql = "SELECT supplier_id, supplier_name, contact, is_active " +
        "FROM suppliers WHERE is_active=1 AND (supplier_name LIKE ? OR contact LIKE ?) ORDER BY supplier_id DESC";

    List<Supplier> out = new ArrayList<>();
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

      String like = "%" + keyword + "%";
      ps.setString(1, like);
      ps.setString(2, like);

      try (ResultSet rs = ps.executeQuery()) {
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
    }
    return out;
  }
}
