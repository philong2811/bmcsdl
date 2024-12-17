package hcmute.it.oracleDB.DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {
  private String username;
  private String accountStatus;
  private Date lockDate;
  private Date createdDate;
  private String defaultTablespace;
  private String temporaryTablespace;
  private String profile;
}
