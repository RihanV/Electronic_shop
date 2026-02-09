package backend.pages;

import backend.dao.EmployeeDAO;
import backend.entity.Employee;
import java.sql.SQLException;
import java.util.List;

/** Backend actions used by EmployeeManagement UI. */
public class EmployeeBackend {
  private final EmployeeDAO dao = new EmployeeDAO();

  /** Validates and creates a new employee. */
  public int addEmployee(Employee e) throws SQLException {
    if (e == null) throw new IllegalArgumentException("Employee is null");
    if (e.getName() == null || e.getName().trim().isEmpty()) throw new IllegalArgumentException("Name required");
    return dao.create(e);
  }

  /** Validates and updates an employee. */
  public boolean updateEmployee(Employee e) throws SQLException {
    if (e == null) throw new IllegalArgumentException("Employee is null");
    if (e.getId() <= 0) throw new IllegalArgumentException("Invalid employee id");
    return dao.update(e);
  }

  /** Validates and deactivates an employee. */
  public boolean deactivateEmployee(int id) throws SQLException {
    if (id <= 0) throw new IllegalArgumentException("Invalid employee id");
    return dao.deactivate(id);
  }

  /** Loads active employees. */
  public List<Employee> loadActive() throws SQLException { return dao.listActive(); }
  /** Searches employees by keyword. */
  public List<Employee> search(String keyword) throws SQLException { return dao.search(keyword == null ? "" : keyword); }
}
