package hcmute.it.oracleDB.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {
  private String profileName;
  private Integer sessionPerUser;
  private Integer connectTime;
  private Integer idleTime;
}
