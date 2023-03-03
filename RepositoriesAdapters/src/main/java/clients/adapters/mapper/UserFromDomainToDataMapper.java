package clients.adapters.mapper;

import clients.data.AddressEnt;
import clients.data.users.AdminEnt;
import clients.data.users.ClientEnt;
import clients.data.users.EmployeeEnt;
import pl.lodz.p.edu.model.Address;
import pl.lodz.p.edu.model.users.Admin;
import pl.lodz.p.edu.model.users.Client;
import pl.lodz.p.edu.model.users.Employee;

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
}
