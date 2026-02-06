package backend.pages;

import backend.dao.CategoryDAO;
import backend.entity.Category;
import java.sql.SQLException;
import java.util.List;

public class CategoryBackend {
  private final CategoryDAO dao = new CategoryDAO();

  public int addCategory(String name) throws SQLException {
    if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Category name required");
    return dao.create(name.trim());
  }

  public boolean renameCategory(int id, String name) throws SQLException {
    if (id <= 0) throw new IllegalArgumentException("Invalid category id");
    if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Category name required");
    return dao.rename(id, name.trim());
  }

  public boolean deactivateCategory(int id) throws SQLException {
    if (id <= 0) throw new IllegalArgumentException("Invalid category id");
    return dao.deactivate(id);
  }

  public List<Category> loadActive() throws SQLException { return dao.listActive(); }
  public List<Category> search(String keyword) throws SQLException { return dao.search(keyword == null ? "" : keyword); }
}
