package backend.pages;

import backend.db.DB;
import backend.entity.InvoiceItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Backend functions used ONLY by Invoice JFrame.
 */
public class InvoiceBackend {

  public double loadGrandTotal(int invoiceId) throws SQLException {
    if (invoiceId <= 0) throw new IllegalArgumentException("Invalid invoice id");
    String sql = "SELECT grand_total FROM invoices WHERE invoice_id=?";
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, invoiceId);
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return 0.0;
        return rs.getDouble("grand_total");
      }
    }
  }

  public List<InvoiceItem> loadItems(int invoiceId) throws SQLException {
    if (invoiceId <= 0) throw new IllegalArgumentException("Invalid invoice id");
    String sql = "SELECT product_id, product_name, quantity, unit_price, line_total " +
        "FROM invoice_items WHERE invoice_id=? ORDER BY item_id";
    List<InvoiceItem> out = new ArrayList<>();
    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, invoiceId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          out.add(new InvoiceItem(
              rs.getInt("product_id"),
              rs.getString("product_name"),
              rs.getInt("quantity"),
              rs.getDouble("unit_price"),
              rs.getDouble("line_total")
          ));
        }
      }
    }
    return out;
  }
}
