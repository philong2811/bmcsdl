package hcmute.it.oracleDB.DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransDTO {
  private String fromUser;
  private String toUser;
  private Integer receive;
  private Date date;
}
