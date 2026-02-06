package backend.pages;

import backend.dao.WarrantyDAO;
import backend.entity.WarrantyRecord;
import java.sql.SQLException;
import java.util.List;

public class WarrantyBackend {
  private final WarrantyDAO dao = new WarrantyDAO();

  public List<WarrantyRecord> loadAll() throws SQLException { return dao.listAll(); }
  public List<WarrantyRecord> search(String keyword) throws SQLException { return dao.search(keyword == null ? "" : keyword); }
}
