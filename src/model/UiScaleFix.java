package model;

/**
 * Applies a conservative UI scale fix to avoid click-offset issues on HiDPI systems.
 * Call this before creating any Swing components.
 */
public final class UiScaleFix {
  private UiScaleFix() {}

  public static void apply() {
    if (System.getProperty("sun.java2d.uiScale") == null) {
      System.setProperty("sun.java2d.uiScale", "1.0");
    }
    if (System.getProperty("sun.java2d.dpiaware") == null) {
      System.setProperty("sun.java2d.dpiaware", "true");
    }
  }
}
