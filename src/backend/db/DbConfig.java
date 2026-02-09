package backend.db;

import java.io.*;
import java.util.*;

/**
 * Loads database connection properties with fallbacks.
 */
public final class DbConfig {
  private DbConfig() {}

  /** Loads config from system property, file, or classpath (in that order). */
  public static Properties load() {
    Properties p = new Properties();
    p.setProperty("db.driver","com.mysql.cj.jdbc.Driver");
    p.setProperty("db.url","jdbc:mysql://localhost:3306/electronic_shop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Colombo");
    p.setProperty("db.user","root");
    p.setProperty("db.password","Rihan@123");

    String path = System.getProperty("app.db.config");
    if (path != null && !path.isBlank()) {
      try (InputStream in = new FileInputStream(path)) { p.load(in); return p; } catch (Exception ignored) {}
    }
    try (InputStream in = new FileInputStream("db.properties")) { p.load(in); return p; } catch (Exception ignored) {}
    try (InputStream in = DbConfig.class.getResourceAsStream("/db.properties")) {
      if (in != null) { p.load(in); return p; }
    } catch (Exception ignored) {}
    return p;
  }
}
