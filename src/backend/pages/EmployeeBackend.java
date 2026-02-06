package backend.pages;

import backend.dao.EmployeeDAO;
import backend.entity.Employee;
import java.sql.SQLException;
import java.util.List;

public class EmployeeBackend {
  private final EmployeeDAO dao = new EmployeeDAO();

  public int addEmployee(Employee e) throws SQLException {
    if (e == null) throw new IllegalArgumentException("Employee is null");
    if (e.getName() == null || e.getName().trim().isEmpty()) throw new IllegalArgumentException("Name required");
    return dao.create(e);
  }

  public boolean updateEmployee(Employee e) throws SQLException {
    if (e == null) throw new IllegalArgumentException("Employee is null");
    if (e.getId() <= 0) throw new IllegalArgumentException("Invalid employee id");
    return dao.update(e);
  }

  public boolean deactivateEmployee(int id) throws SQLException {
    if (id <= 0) throw new IllegalArgumentException("Invalid employee id");
    return dao.deactivate(id);
  }

  public List<Employee> loadActive() throws SQLException { return dao.listActive(); }
  public List<Employee> search(String keyword) throws SQLException { return dao.search(keyword == null ? "" : keyword); }
}
