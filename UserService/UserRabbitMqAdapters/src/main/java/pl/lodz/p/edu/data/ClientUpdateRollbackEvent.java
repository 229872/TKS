package pl.lodz.p.edu.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateRollbackEvent {

  private String login;
  private String name;
  private String lastName;
}
