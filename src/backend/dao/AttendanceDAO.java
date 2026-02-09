package backend.dao;

import backend.db.DB;
import backend.entity.Attendance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO for attendance table CRUD operations. */
public class AttendanceDAO {
  /** Inserts an attendance record and returns generated id. */
  public int markAttendance(Attendance a) throws SQLException {
    String sql = "INSERT INTO attendance(employee_id, shift_id, att_date, status, note) VALUES (?,?,?,?,?)";
    try (Connection c = DB.getConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setInt(1, a.getEmployeeId());
      ps.setInt(2, a.getShiftId());
      ps.setDate(3, Date.valueOf(a.getDate()));
      ps.setString(4, a.getStatus());
      ps.setString(5, a.getNote());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    }
  }

  /** Returns attendance history for a specific employee. */
  public List<Attendance> listByEmployee(int employeeId) throws SQLException {
    String sql = "SELECT id, employee_id, shift_id, att_date, status, note FROM attendance WHERE employee_id=? ORDER BY att_date DESC";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, employeeId);
      try (ResultSet rs = ps.executeQuery()) {
        List<Attendance> out = new ArrayList<>();
        while (rs.next()) {
          out.add(new Attendance(
              rs.getInt("id"),
              rs.getInt("employee_id"),
              rs.getInt("shift_id"),
              rs.getDate("att_date").toString(),
              rs.getString("status"),
              rs.getString("note")
          ));
        }
        return out;
      }
    }
  }
}
