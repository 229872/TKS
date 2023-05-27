package pl.lodz.p.edu.adapter.rest.dto.output;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientOutputDTO {
    private UUID userId;
    private String firstName;
    private String lastName;
}
