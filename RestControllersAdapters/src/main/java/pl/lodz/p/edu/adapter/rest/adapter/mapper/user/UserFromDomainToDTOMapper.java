package pl.lodz.p.edu.adapter.rest.adapter.mapper.user;

import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.core.domain.model.Address;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;

public class UserFromDomainToDTOMapper {
    private AddressDTO convertAddressToDTO(Address address) {
        return new AddressDTO(
                address.getCity(),
                address.getStreet(),
                address.getStreetNr()
        );
    }

    public ClientInputDTO convertClientToClientDTO(Client client) {
        return new ClientInputDTO(
                client.getLogin(),
                client.getPassword(),
                client.getFirstName(),
                client.getLastName(),
                convertAddressToDTO(client.getAddress())
        );
    }

    public AdminInputDTO convertAdminToAdminDTO(Admin admin) {
        return new AdminInputDTO(
                admin.getLogin(),
                admin.getPassword(),
                admin.getFavouriteIceCream()
        );
    }

    public EmployeeInputDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeInputDTO(
                employee.getLogin(),
                employee.getPassword(),
                employee.getDesk()
        );
    }
}
