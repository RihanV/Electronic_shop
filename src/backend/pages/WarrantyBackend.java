package backend.pages;

import backend.dao.WarrantyDAO;
import backend.entity.WarrantyRecord;
import java.sql.SQLException;
import java.util.List;

/** Backend actions used by WarrantyManagement UI. */
public class WarrantyBackend {
  private final WarrantyDAO dao = new WarrantyDAO();

  /** Loads all warranty records. */
  public List<WarrantyRecord> loadAll() throws SQLException { return dao.listAll(); }
  /** Searches warranty records by keyword. */
  public List<WarrantyRecord> search(String keyword) throws SQLException { return dao.search(keyword == null ? "" : keyword); }
}
