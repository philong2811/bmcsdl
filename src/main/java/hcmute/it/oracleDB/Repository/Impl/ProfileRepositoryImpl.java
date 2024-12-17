package hcmute.it.oracleDB.Repository.Impl;

import hcmute.it.oracleDB.Config.Datasource;
import hcmute.it.oracleDB.Config.UserContext;
import hcmute.it.oracleDB.DTO.ProfileDTO;
import hcmute.it.oracleDB.DTO.ProfileInfoDTO;
import hcmute.it.oracleDB.Repository.ProfileRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository

public class ProfileRepositoryImpl implements ProfileRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Iterable<ProfileInfoDTO> findAllProfile() {
    String sql = "SELECT p.PROFILE AS PROFILE,\n"
        + "        p.RESOURCE_NAME AS RESOURCE_NAME,\n"
        + "        p.RESOURCE_TYPE AS RESOURCE_TYPE,\n"
        + "        p.LIMIT AS PROFILE_LIMIT\n"
        + "FROM DBA_PROFILES p\n"
        + "WHERE p.PROFILE NOT IN ('DEFAULT', 'ORA_STIG_PROFILE') \n"
        + "AND p.RESOURCE_NAME IN ('SESSIONS_PER_USER', 'CONNECT_TIME', 'IDLE_TIME')\n";

    return jdbcTemplate.query(sql, (rs, rowNum) -> {
      ProfileInfoDTO profileInfo = new ProfileInfoDTO();
      profileInfo.setProfileName(rs.getString("PROFILE"));
      profileInfo.setResourceName(rs.getString("RESOURCE_NAME"));
      profileInfo.setResourceType(rs.getString("RESOURCE_TYPE"));
      profileInfo.setLimit(rs.getString("PROFILE_LIMIT"));
      return profileInfo;
    });
  }

  @Override
  public boolean checkProfileExist(String profileName) {
    String sql = "SELECT DISTINCT PROFILE\n"
        + "FROM DBA_PROFILES \n"
        + "WHERE PROFILE NOT IN ('DEFAULT', 'ORA_STIG_PROFILE') "
        + "AND PROFILE = UPPER(?)";
    List<String> profiles = jdbcTemplate.query(sql, new Object[]{profileName},
        (rs, rowNum) -> rs.getString("PROFILE"));

    return !profiles.isEmpty();
  }

  @Override
  public String createProfile(ProfileDTO request) {
    String sql = "CALL star_CreateProfile(?, ?, ?, ?)";
    jdbcTemplate.update(sql,
        request.getProfileName(),
        request.getSessionPerUser(),
        request.getConnectTime(),
        request.getIdleTime());
    return "Profile created successfully";
  }

  @Override
  public String updateProfile(ProfileDTO request) {
    String sql = "CALL star_AlterProfile(?, ?, ?, ?)";
    jdbcTemplate.update(sql,
        request.getProfileName(),
        request.getSessionPerUser(),
        request.getConnectTime(),
        request.getIdleTime());
    return "Profile updated successfully";
  }

  @Override
  public String deleteProfile(String profileName) {
    String sql = "CALL star_DropProfile(?)";
    jdbcTemplate.update(sql, profileName);
    return "Profile deteled successfully";
  }

  @Override
  public String assignProfileToUser(String profileName, String username) {
    String sql = "ALTER USER ? PROFILE ?";
    jdbcTemplate.update(sql, username, profileName);
    return "assign profile successfully";
  }

  @Override
  public List<String> testConnect(String username, String password) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(Datasource.createDataSource(username, password));

    if (jdbcTemplate == null) {
      System.out.println("Repository thread: " + Thread.currentThread().getName());
      throw new IllegalStateException("JdbcTemplate is not initialized!");
    } else {
      String sql = "SELECT SYS_CONTEXT('USERENV', 'SESSION_USER') AS session_user FROM dual"; // Thay đổi theo nhu cầu
      List<String> results = jdbcTemplate.query(sql, new RowMapper<String>() {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
          return rs.getString("session_user");
        }
      });
      return results;
    }
  }
}
