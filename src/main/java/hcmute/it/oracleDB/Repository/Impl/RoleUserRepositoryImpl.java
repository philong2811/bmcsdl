package hcmute.it.oracleDB.Repository.Impl;

import hcmute.it.oracleDB.Repository.AccountRepository;
import hcmute.it.oracleDB.Repository.RoleUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleUserRepositoryImpl implements RoleUserRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private AccountRepository accountRepository;

  boolean checkRoleExists(String roleName){
    String sql = "SELECT COUNT(*) FROM DBA_ROLES WHERE ROLE = UPPER(?)";
    Integer count = jdbcTemplate.queryForObject(sql, new Object[]{roleName}, Integer.class);
    return count != null && count > 0;
  }

  public String assignRoleToUser(String role, String username){
    if(!checkRoleExists(role))
      return "Role does not exists";
    if(accountRepository.findByUsername(username).isEmpty())
      return "user does not exists";
    String sql = "GRANT ? TO ?";
    jdbcTemplate.update(sql, role, username);
    return "Success";
  }

}
