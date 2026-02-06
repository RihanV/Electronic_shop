package backend.pages;

import backend.dao.DeliveryDAO;
import backend.entity.Delivery;
import java.sql.SQLException;
import java.util.List;

public class DeliveryBackend {
  private final DeliveryDAO dao = new DeliveryDAO();

  public int addDelivery(Delivery d) throws SQLException {
    if (d == null) throw new IllegalArgumentException("Delivery is null");
    if (d.getCustomerName() == null || d.getCustomerName().trim().isEmpty()) {
      throw new IllegalArgumentException("Customer name required");
    }
    if (d.getAddress() == null || d.getAddress().trim().isEmpty()) {
      throw new IllegalArgumentException("Address required");
    }
    if (d.getStatus() == null || d.getStatus().trim().isEmpty()) {
      d.setStatus("Pending");
    }
    return dao.create(d);
  }

  public boolean updateStatus(int id, String status) throws SQLException {
    if (id <= 0) throw new IllegalArgumentException("Invalid delivery id");
    if (status == null || status.trim().isEmpty()) throw new IllegalArgumentException("Status required");
    return dao.updateStatus(id, status.trim());
  }

  public List<Delivery> loadAll() throws SQLException { return dao.listAll(); }
  public List<Delivery> search(String keyword) throws SQLException { return dao.search(keyword == null ? "" : keyword); }
}
