package hcmute.it.oracleDB.Repository;

import hcmute.it.oracleDB.DTO.UserInfoDTO;
import java.util.Optional;

public interface UserInfoRepository {
  Iterable<UserInfoDTO> getAll();

  Optional<UserInfoDTO> getUserENV();
}
