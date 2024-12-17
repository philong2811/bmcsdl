package hcmute.it.oracleDB.Service;

import hcmute.it.oracleDB.Config.UserContext;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;

public interface AccountService {
  public List<User> getAll();

  public  Optional<User> getUser(String username);

  public List<User> getUserByRole(String role);

  public Optional<UserInfoDTO> getUserENV();

  public List<String> testConnect(String username, String password);

}
