package hcmute.it.oracleDB.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegister {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private String address;
  private String sex;
  private Integer age;
}
