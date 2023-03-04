package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.rent.RentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.rent.RentFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.RentService;
import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.dto.RentDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestBusinessLogicInterruptException;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.core.domain.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.users.*;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;
import pl.lodz.p.edu.ports.incoming.RentServicePort;
import pl.lodz.p.edu.ports.incoming.UserServicePort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RentRestControllerAdapter implements RentService {

    @Inject
    private RentServicePort rentServicePort;

    @Inject
    private UserServicePort userServicePort;

    @Inject
    private EquipmentServicePort equipmentServicePort;

    @Inject
    private RentFromDTOToDomainMapper toDomainModel;

    @Inject
    private RentFromDomainToDTOMapper toDTOMapper;

    @Inject
    private EquipmentFromDTOToDomainMapper equipmentToDomainMapper;

    @Inject
    private UserFromDTOToDomainMapper userToDomainMapper;

    @Override
    public RentDTO add(RentDTO rentDTO) throws RestObjectNotValidException {
        User client = userServicePort.getUserByUuidOfType("CLIENT",
                rentDTO.getClientUUIDFromString());

        Equipment equipment = equipmentServicePort.get(rentDTO.getEquipmentUUIDFromString());
        Rent rent = convertToDomainModel(rentDTO, equipment, (Client) client);
        try {
            rentServicePort.add(rent);
        } catch (ObjectNotValidException | BusinessLogicInterruptException e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());
        }
        return rentDTO;
    }

    @Override
    public List<RentDTO> getRentsByClient(ClientDTO clientDTO) {
        Client client = userToDomainMapper.convertClientToDomainModel(clientDTO);
        return rentServicePort.getRentsByClient(client).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<RentDTO> getRentByEq(EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentToDomainMapper.convertToDomainModel(equipmentDTO);
        List<Rent> rentList = rentServicePort.getRentByEq(equipment);
        return rentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<RentDTO> getAll() {
        List<Rent> rentList = rentServicePort.getAll();
        return rentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RentDTO get(UUID uuid) {
        Rent rent = rentServicePort.get(uuid);
        return convertToDTO(rent);
    }

    @Override
    public RentDTO update(UUID entityId, RentDTO rentDTO) throws RestObjectNotValidException, RestBusinessLogicInterruptException {
        Equipment equipment = equipmentServicePort.get(rentDTO.getEquipmentUUIDFromString());

        User user = userServicePort.getUserByUuidOfType("CLIENT", rentDTO.getClientUUIDFromString());
        Rent rent = convertToDomainModel(rentDTO, equipment, (Client) user); //FIXME XD
        try {
            Rent rentReturn = rentServicePort.update(entityId, rent);
            return convertToDTO(rentReturn);
        } catch (ObjectNotValidException e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());
        } catch (BusinessLogicInterruptException e) {
            throw new RestBusinessLogicInterruptException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void remove(UUID rentUuid) throws RestBusinessLogicInterruptException {
        try {
            rentServicePort.remove(rentUuid);
        } catch (BusinessLogicInterruptException e) {
            throw new RestBusinessLogicInterruptException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean checkEquipmentAvailable(EquipmentDTO equipmentDTO, LocalDateTime dateTime) {
        Equipment equipment = equipmentToDomainMapper.convertToDomainModel(equipmentDTO);
        return rentServicePort.checkEquipmentAvailable(equipment, dateTime);
    }

    private RentDTO convertToDTO(Rent rent) {
        return toDTOMapper.convertToDTO(rent);
    }

    private Rent convertToDomainModel(RentDTO rentDTO, Equipment equipment, Client client)
            throws RestObjectNotValidException {
        return toDomainModel.convertToDomainModel(rentDTO, equipment, client);
    }
}
