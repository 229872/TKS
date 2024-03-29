package pl.lodz.p.edu.adapter.rest.users.adapter.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.rest.users.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Address;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Admin;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Client;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Employee;

@ApplicationScoped
public class UserFromDTOToDomainMapper {
    private Address convertAddressToDomainModel(AddressDTO addressDTO) {
        return new Address(
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
                clientDTO.getLastName(),
                convertAddressToDomainModel(clientDTO.getAddress())
        );
    }

    public Admin convertInputAdminToDomainModel(AdminInputDTO adminDTO) {
        return new Admin (
                adminDTO.getLogin(),
                adminDTO.getPassword(),
                adminDTO.getFavouriteIceCream()
        );
    }

    public Employee convertInputEmployeeToDomainModel(EmployeeInputDTO employeeDTO) {
        return new Employee(
                employeeDTO.getLogin(),
                employeeDTO.getPassword(),
                employeeDTO.getDesk()
        );
    }

    public Client convertOutputClientToDomainModel(ClientOutputDTO clientDTO) {
        return new Client(
                clientDTO.getUserId(),
                clientDTO.getLogin()
        );
    }
}
