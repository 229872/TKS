package pl.lodz.p.edu.usersoap.adapter.mapper.user;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Address;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Admin;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Client;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Employee;
import pl.lodz.p.edu.usersoap.dto.AddressDTO;
import pl.lodz.p.edu.usersoap.dto.output.AdminOutputDTO;
import pl.lodz.p.edu.usersoap.dto.output.ClientOutputDTO;
import pl.lodz.p.edu.usersoap.dto.output.EmployeeOutputDTO;

@ApplicationScoped
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
