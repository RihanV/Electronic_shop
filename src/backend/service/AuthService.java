package backend.service;

import backend.dao.UserDAO;
import backend.entity.User;

public class AuthService {
  private final UserDAO userDAO = new UserDAO();
  public User login(String username, String password) throws Exception {
    if (username == null || username.isBlank() || password == null || password.isBlank()) return null;
    return userDAO.authenticate(username.trim(), password.trim());
  }
}
