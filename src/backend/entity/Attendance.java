package backend.entity;

/** Simple POJO representing an attendance record. */
public class Attendance {
  private int id;
  private int employeeId;
  private int shiftId;
  private String date;
  private String status;
  private String note;

  public Attendance() {}

  public Attendance(int id, int employeeId, int shiftId, String date, String status, String note) {
    this.id = id;
    this.employeeId = employeeId;
    this.shiftId = shiftId;
    this.date = date;
    this.status = status;
    this.note = note;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public int getEmployeeId() { return employeeId; }
  public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

  public int getShiftId() { return shiftId; }
  public void setShiftId(int shiftId) { this.shiftId = shiftId; }

  public String getDate() { return date; }
  public void setDate(String date) { this.date = date; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public String getNote() { return note; }
  public void setNote(String note) { this.note = note; }
}
