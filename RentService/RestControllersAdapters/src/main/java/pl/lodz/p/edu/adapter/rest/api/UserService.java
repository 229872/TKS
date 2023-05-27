package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.output.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<ClientOutputDTO> getAll();

    ClientOutputDTO get(UUID uuid) throws ObjectNotFoundRestException;
}
