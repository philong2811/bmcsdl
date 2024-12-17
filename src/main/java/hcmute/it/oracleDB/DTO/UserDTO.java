package hcmute.it.oracleDB.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private String username;
  private String firstName;
  private String lastName;
  private String address;
  private String email;
  private String sex;
  private Integer age;
  private Integer balance;
  private String role;
  private String status;
}
