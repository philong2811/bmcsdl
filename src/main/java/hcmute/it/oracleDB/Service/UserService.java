package hcmute.it.oracleDB.Service;

import hcmute.it.oracleDB.DTO.TransDTO;
import hcmute.it.oracleDB.DTO.UserDTO;
import hcmute.it.oracleDB.DTO.UserInfoDTO;
import java.util.List;
import java.util.Optional;

public interface UserService {
  public UserDTO getByUserName(String username);

}
