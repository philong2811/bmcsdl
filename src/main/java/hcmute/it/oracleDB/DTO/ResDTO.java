package hcmute.it.oracleDB.DTO;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResDTO<T> {
  private T object =  null;
  private String message;
  private String status;

}
