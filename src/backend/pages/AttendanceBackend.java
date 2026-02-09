package backend.pages;

import backend.dao.AttendanceDAO;
import backend.dao.ShiftDAO;
import backend.entity.Attendance;
import backend.entity.Shift;
import java.sql.SQLException;
import java.util.List;

/** Backend actions used by AttendanceManagement UI. */
public class AttendanceBackend {
  private final AttendanceDAO attendanceDao = new AttendanceDAO();
  private final ShiftDAO shiftDao = new ShiftDAO();

  /** Validates and inserts an attendance record. */
  public int markAttendance(Attendance a) throws SQLException {
    if (a == null) throw new IllegalArgumentException("Attendance is null");
    if (a.getEmployeeId() <= 0) throw new IllegalArgumentException("Employee required");
    if (a.getShiftId() <= 0) throw new IllegalArgumentException("Shift required");
    if (a.getDate() == null || a.getDate().trim().isEmpty()) throw new IllegalArgumentException("Date required");
    if (a.getStatus() == null || a.getStatus().trim().isEmpty()) a.setStatus("Present");
    return attendanceDao.markAttendance(a);
  }

  /** Loads attendance history for an employee. */
  public List<Attendance> loadByEmployee(int employeeId) throws SQLException {
    if (employeeId <= 0) throw new IllegalArgumentException("Employee required");
    return attendanceDao.listByEmployee(employeeId);
  }

  /** Loads all shifts for selection. */
  public List<Shift> loadShifts() throws SQLException { return shiftDao.listAll(); }
}
