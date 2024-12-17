package hcmute.it.oracleDB.Config;

public class UserCredentials {

  private String username;
  private String password;

  public UserCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public UserCredentials() {

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // Getter và Setter cho password
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // Phương thức để xóa thông tin
  public void clear() {
    this.username = null;
    this.password = null;
  }

}
