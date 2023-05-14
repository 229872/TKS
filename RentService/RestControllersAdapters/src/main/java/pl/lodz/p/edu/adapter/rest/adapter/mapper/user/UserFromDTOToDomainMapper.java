package pl.lodz.p.edu.adapter.rest.adapter.mapper.user;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.core.domain.model.Address;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;

@ApplicationScoped
public class UserFromDTOToDomainMapper {
    private Address convertAddressToDomainModel(AddressDTO addressDTO) {
        return new Address (
                addressDTO.getCity(),
                addressDTO.getStreet(),
                addressDTO.getStreetNr()
        );
    }

    public Client convertInputClientToDomainModel(ClientInputDTO clientDTO) {
        return new Client (
                clientDTO.getLogin(),
                clientDTO.getPassword(),
                clientDTO.getFirstName(),
                clientDTO.getLastName()
        );
    }

    public Admin convertInputAdminToDomainModel(AdminInputDTO adminDTO) {
        return new Admin (
                adminDTO.getLogin(),
                adminDTO.getPassword()
        );
    }

    public Employee convertInputEmployeeToDomainModel(EmployeeInputDTO employeeDTO) {
        return new Employee (
                employeeDTO.getLogin(),
                employeeDTO.getPassword()
        );
    }

    public Client convertOutputClientToDomainModel(ClientOutputDTO clientDTO) {
        return new Client(
                clientDTO.getUserId(),
                clientDTO.getLogin()
        );
    }
}
