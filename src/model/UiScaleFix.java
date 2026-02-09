package model;

/**
 * Applies a conservative UI scale fix to avoid click-offset issues on HiDPI systems.
 * Call this before creating any Swing components.
 */
public final class UiScaleFix {
  private UiScaleFix() {}

  public static void apply() {
    // Detect OS and set conservative scaling flags to avoid click-offset issues.
    String os = System.getProperty("os.name", "").toLowerCase();
    boolean isMac = os.contains("mac");

    if (isMac && System.getProperty("apple.awt.uiScale") == null) {
      System.setProperty("apple.awt.uiScale", "1");
    }
    if (isMac && System.getProperty("sun.java2d.metal") == null) {
      System.setProperty("sun.java2d.metal", "false");
    }
    if (System.getProperty("sun.java2d.uiScale") == null) {
      System.setProperty("sun.java2d.uiScale", "1");
    }
    if (System.getProperty("sun.java2d.uiScale.enabled") == null) {
      System.setProperty("sun.java2d.uiScale.enabled", "false");
    }
    if (System.getProperty("sun.java2d.dpiaware") == null) {
      System.setProperty("sun.java2d.dpiaware", "true");
    }
  }
}
