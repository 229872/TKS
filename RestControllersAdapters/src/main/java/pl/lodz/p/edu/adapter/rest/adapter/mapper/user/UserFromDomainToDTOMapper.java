package pl.lodz.p.edu.adapter.rest.adapter.mapper.user;

import pl.lodz.p.edu.adapter.rest.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.AdminOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.EmployeeOutputDTO;
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

    public ClientOutputDTO convertClientToClientOutputDTO(Client client) {
        return new ClientOutputDTO(
                client.getEntityId(),
                client.getLogin(),
                client.getFirstName(),
                client.getLastName(),
                client.isActive(),
                convertAddressToDTO(client.getAddress())
        );
    }

    public AdminOutputDTO convertAdminToAdminOutputDTO(Admin admin) {
        return new AdminOutputDTO(
                admin.getEntityId(),
                admin.getLogin(),
                admin.getFavouriteIceCream(),
                admin.isActive()
        );
    }

    public EmployeeOutputDTO convertEmployeeToEmployeeOutputDTO(Employee employee) {
        return new EmployeeOutputDTO(
                employee.getEntityId(),
                employee.getLogin(),
                employee.getDesk(),
                employee.isActive()
        );
    }
}
