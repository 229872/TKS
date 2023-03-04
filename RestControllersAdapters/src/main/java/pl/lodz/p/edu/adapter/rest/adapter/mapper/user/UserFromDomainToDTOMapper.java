package pl.lodz.p.edu.adapter.rest.adapter.mapper.user;

import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
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

    public ClientDTO convertClientToClientDTO(Client client) {
        return new ClientDTO(
                client.getLogin(),
                client.getPassword(),
                client.getFirstName(),
                client.getLastName(),
                convertAddressToDTO(client.getAddress())
        );
    }

    public AdminDTO convertAdminToAdminDTO(Admin admin) {
        return new AdminDTO(
                admin.getLogin(),
                admin.getPassword(),
                admin.getFavouriteIceCream()
        );
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getLogin(),
                employee.getPassword(),
                employee.getDesk()
        );
    }
}
