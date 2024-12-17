package hcmute.it.oracleDB.Repository.Impl;

import hcmute.it.oracleDB.DTO.UserInfoDTO;
import hcmute.it.oracleDB.Repository.UserInfoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Iterable<UserInfoDTO> getAll() {
    String sql = "SELECT username, "
        + "account_status AS accountStatus, "
        + "lock_date AS lockDate, " +
        "created_date AS createdDate, "
        + "default_tablespace AS defaultTablespace, " +
        "temporary_tablespace AS temporaryTablespace, "
        + "profile " +
        "FROM DBA_USERS";

    return jdbcTemplate.query(sql, (rs, rowNum) -> {
      UserInfoDTO userInfo = new UserInfoDTO();
      userInfo.setUsername(rs.getString("username"));
      userInfo.setAccountStatus(rs.getString("accountStatus"));
      userInfo.setLockDate(rs.getDate("lockDate"));
      userInfo.setCreatedDate(rs.getDate("createdDate"));
      userInfo.setDefaultTablespace(rs.getString("defaultTablespace"));
      userInfo.setTemporaryTablespace(rs.getString("temporaryTablespace"));
      userInfo.setProfile(rs.getString("profile"));
      return userInfo;
    });
  }

  @Override
  public Optional<UserInfoDTO> getUserENV() {
    String sql = "SELECT username, "
        + "account_status AS accountStatus, "
        + "lock_date AS lockDate, " +
        "created_date AS createdDate, "
        + "default_tablespace AS defaultTablespace, " +
        "temporary_tablespace AS temporaryTablespace, "
        + "profile " +
        "FROM USER_USERS";
    return jdbcTemplate.query(sql, rs -> {
      UserInfoDTO userInfo = new UserInfoDTO();
      userInfo.setUsername(rs.getString("username"));
      userInfo.setAccountStatus(rs.getString("accountStatus"));
      userInfo.setLockDate(rs.getDate("lockDate"));
      userInfo.setCreatedDate(rs.getDate("createdDate"));
      userInfo.setDefaultTablespace(rs.getString("defaultTablespace"));
      userInfo.setTemporaryTablespace(rs.getString("temporaryTablespace"));
      userInfo.setProfile(rs.getString("profile"));
      return Optional.of(userInfo);
    });
  }
}
