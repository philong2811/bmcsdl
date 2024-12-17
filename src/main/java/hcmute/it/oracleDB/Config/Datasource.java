package hcmute.it.oracleDB.Config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Datasource {
  public static DriverManagerDataSource createDataSource(String username, String password) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521/orclpdb");
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }
}
