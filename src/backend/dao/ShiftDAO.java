package backend.dao;

import backend.db.DB;
import backend.entity.Shift;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO {
  public List<Shift> listAll() throws SQLException {
    String sql = "SELECT id,name,start_time,end_time FROM shifts ORDER BY id";
    try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      List<Shift> out = new ArrayList<>();
      while (rs.next()) {
        out.add(new Shift(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("start_time"),
            rs.getString("end_time")
        ));
      }
      return out;
    }
  }
}
