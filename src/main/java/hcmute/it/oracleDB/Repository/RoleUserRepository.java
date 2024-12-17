package hcmute.it.oracleDB.Repository;

public interface RoleUserRepository {
  public String assignRoleToUser(String role, String username);
}
