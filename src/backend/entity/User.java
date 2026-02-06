package backend.entity;

public class User {
  private int id;
  private String username;
  private boolean active;

  public User() {}
  public User(int id,String username,boolean active) { this.id=id; this.username=username; this.active=active; }
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }
}
