package hcmute.it.oracleDB.Config;

import hcmute.it.oracleDB.Auth.AuthReq;

public class UserContext {
  public static final UserCredentials currentUser = new UserCredentials();

  public UserContext() {

  }

  // Thiết lập thông tin người dùng
  public static void setCurrentUser(String username, String password) {
    currentUser.setUsername(username);
    currentUser.setPassword(password);
  }

  // Lấy thông tin người dùng
  public static UserCredentials getCurrentUser() {
    return currentUser;
  }

  // Xóa thông tin người dùng
  public static void clear() {
    currentUser.clear();
  }
}

