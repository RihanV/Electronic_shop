package model;

/**
 * Bootstrap launcher to apply UI scale properties before any Swing classes load.
 */
public final class AppLauncher {
  private AppLauncher() {}

  public static void main(String[] args) {
    // Apply HiDPI fixes before any UI is created, then hand off to Login screen.
    UiScaleFix.apply();
    Login.main(args);
  }
}
