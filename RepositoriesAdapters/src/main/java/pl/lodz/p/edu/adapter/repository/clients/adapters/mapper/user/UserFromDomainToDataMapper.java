package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user;

import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.AdminEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.EmployeeEnt;
import pl.lodz.p.edu.core.domain.model.Address;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;

public class UserFromDomainToDataMapper {
    private AddressEnt convertAddressToDataModel(Address address) {
        return new AddressEnt(
                address.getCity(),
                address.getStreet(),
                address.getStreetNr()
        );
    }

    public ClientEnt convertClientToDataModel(Client client) {
        return new ClientEnt(
                client.getLogin(),
                client.getPassword(),
                client.getFirstName(),
                client.getLastName(),
                convertAddressToDataModel(client.getAddress())
        );
    }

    public AdminEnt convertAdminToDataModel(Admin admin) {
        return new AdminEnt(
                admin.getLogin(),
                admin.getPassword(),
                admin.getFavouriteIceCream()
        );
    }

    public EmployeeEnt convertEmployeeToDataModel(Employee employee) {
        return new EmployeeEnt(
                employee.getLogin(),
                employee.getPassword(),
                employee.getDesk()
        );
    }

    public ClientEnt convertPUTClientToDataModel(Client client) {
        return new ClientEnt(
                client.getEntityId(),
                client.getLogin(),
                client.getPassword(),
                client.getFirstName(),
                client.getLastName(),
                client.isActive(),
                convertAddressToDataModel(client.getAddress())
        );
    }

    public AdminEnt convertPUTAdminToDataModel(Admin admin) {
        return new AdminEnt(
                admin.getEntityId(),
                admin.getLogin(),
                admin.getPassword(),
                admin.isActive(),
                admin.getFavouriteIceCream()
        );
    }

    public EmployeeEnt convertPUTEmployeeToDataModel(Employee employee) {
        return new EmployeeEnt(
                employee.getEntityId(),
                employee.getLogin(),
                employee.getPassword(),
                employee.isActive(),
                employee.getDesk()
        );
    }
}
