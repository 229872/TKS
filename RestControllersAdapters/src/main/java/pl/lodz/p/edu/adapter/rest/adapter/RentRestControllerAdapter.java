package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.rent.RentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.rent.RentFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.RentService;
import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.RentOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestBusinessLogicInterruptException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalDateException;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.core.domain.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
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

@ApplicationScoped
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
    public void add(RentInputDTO rentInputDTO) throws RestObjectNotValidException, ObjectNotFoundRestException, RestIllegalDateException, RestBusinessLogicInterruptException {
        try {
            Client client = (Client) userServicePort.get(rentInputDTO.getClientUUIDFromString());
            Equipment equipment = equipmentServicePort.get(rentInputDTO.getEquipmentUUIDFromString());

            Rent rent = convertToDomainModel(rentInputDTO, equipment, client);
            rentServicePort.add(rent);
//            return convertToDTO(rentServicePort.add(rent));

            //ObjectNotFound throws RestObjectNotValid because those not found entities are parts of Rent that will be created
        } catch (ObjectNotValidException | ObjectNotFoundServiceException e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());
        } catch (BusinessLogicInterruptException e) {
            throw new RestBusinessLogicInterruptException(e.getMessage(), e.getCause());
        } catch (ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        } catch (RestIllegalDateException e) {
            throw new RestIllegalDateException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<RentOutputDTO> getRentsByClientId(UUID clientUuid) {
        return rentServicePort.getRentsByClientId(clientUuid).stream()
                .map(this::convertToOutputDTO)
                .toList();
    }

    @Override
    public List<RentInputDTO> getRentsByEquipment(EquipmentOutputDTO equipmentOutputDTO) {
        Equipment equipment = equipmentToDomainMapper.convertToDomainModelFromOutputDTO(equipmentOutputDTO);
        List<Rent> rentList = rentServicePort.getRentsByEquipment(equipment);
        return rentList.stream().map(this::convertToInputDTO).collect(Collectors.toList());
    }

    @Override
    public List<RentOutputDTO> getAll() {
        List<Rent> rentList = rentServicePort.getAll();
        return rentList.stream().map(this::convertToOutputDTO).collect(Collectors.toList());
    }

    @Override
    public RentOutputDTO get(UUID uuid) throws ObjectNotFoundRestException {
        try {
            Rent rent = rentServicePort.get(uuid);
            return convertToOutputDTO(rent);

        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public RentInputDTO update(UUID entityId, RentInputDTO rentInputDTO) throws RestObjectNotValidException,
            RestBusinessLogicInterruptException, ObjectNotFoundRestException {
        try {
            Equipment equipment = equipmentServicePort.get(rentInputDTO.getEquipmentUUIDFromString());
            Client client = (Client) userServicePort.get(rentInputDTO.getClientUUIDFromString());
            Rent rent = convertToDomainModel(rentInputDTO, equipment, client);
            Rent rentReturn = rentServicePort.update(entityId, rent);
            return convertToInputDTO(rentReturn);

        } catch (ObjectNotValidException | RestIllegalDateException e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());

        } catch (BusinessLogicInterruptException e) {
            throw new RestBusinessLogicInterruptException(e.getMessage(), e.getCause());

        } catch (ObjectNotFoundServiceException | ClassCastException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void remove(UUID rentUuid) throws RestBusinessLogicInterruptException, ObjectNotFoundRestException {
        try {
            rentServicePort.remove(rentUuid);
        } catch (BusinessLogicInterruptException e) {
            throw new RestBusinessLogicInterruptException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean checkEquipmentAvailable(EquipmentOutputDTO equipmentOutputDTO, LocalDateTime dateTime) {
        Equipment equipment = equipmentToDomainMapper.convertToDomainModelFromOutputDTO(equipmentOutputDTO);
        return rentServicePort.checkEquipmentAvailable(equipment, dateTime);
    }

    private RentInputDTO convertToInputDTO(Rent rent) {
        return toDTOMapper.convertToInputDTO(rent);
    }

    private RentOutputDTO convertToOutputDTO(Rent rent) {
        return toDTOMapper.convertToOutputDTO(rent);
    }

    private Rent convertToDomainModel(RentInputDTO rentInputDTO, Equipment equipment, Client client)
            throws RestObjectNotValidException, RestIllegalDateException {
        return toDomainModel.convertToDomainModel(rentInputDTO, equipment, client);
    }
}
