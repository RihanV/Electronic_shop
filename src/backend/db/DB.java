package backend.db;

import java.sql.*;
import java.util.Properties;

/**
 * Central DB helper to open JDBC connections using DbConfig.
 */
public final class DB {
  private static volatile boolean driverLoaded = false;
  private DB() {}

  /** Loads JDBC driver once and returns a new connection. */
  public static Connection getConnection() throws SQLException {
    Properties p = DbConfig.load();
    try {
      if (!driverLoaded) {
        Class.forName(p.getProperty("db.driver"));
        driverLoaded = true;
      }
    } catch (ClassNotFoundException e) {
      throw new SQLException("JDBC Driver not found: " + p.getProperty("db.driver"), e);
    }
    return DriverManager.getConnection(
        p.getProperty("db.url"),
        p.getProperty("db.user"),
        p.getProperty("db.password")
    );
  }
}
