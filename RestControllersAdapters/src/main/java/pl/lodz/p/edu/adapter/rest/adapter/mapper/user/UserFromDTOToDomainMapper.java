package pl.lodz.p.edu.adapter.rest.adapter.mapper.user;

import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
import pl.lodz.p.edu.core.domain.model.Address;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;

public class UserFromDTOToDomainMapper {
    private Address convertAddressToDomainModel(AddressDTO addressDTO) {
        return new Address (
                addressDTO.getCity(),
                addressDTO.getStreet(),
                addressDTO.getStreetNr()
        );
    }

    public Client convertClientToDomainModel(ClientDTO clientDTO) {
        return new Client (
                clientDTO.getLogin(),
                clientDTO.getPassword(),
                clientDTO.getFirstName(),
                clientDTO.getLastName(),
                convertAddressToDomainModel(clientDTO.getAddress())
        );
    }

    public Admin convertAdminToDomainModel(AdminDTO adminDTO) {
        return new Admin (
                adminDTO.getLogin(),
                adminDTO.getPassword(),
                adminDTO.getFavouriteIceCream()
        );
    }

    public Employee convertEmployeeToDomainModel(EmployeeDTO employeeDTO) {
        return new Employee (
                employeeDTO.getLogin(),
                employeeDTO.getPassword(),
                employeeDTO.getDesk()
        );
    }
}
