package clients.adapters.mapper.user;

import clients.data.AddressEnt;
import clients.data.users.AdminEnt;
import clients.data.users.ClientEnt;
import clients.data.users.EmployeeEnt;
import pl.lodz.p.edu.model.Address;
import pl.lodz.p.edu.model.users.Admin;
import pl.lodz.p.edu.model.users.Client;
import pl.lodz.p.edu.model.users.Employee;

public class UserFromDataToDomainMapper {
    public Client convertClientToDomainModel(ClientEnt clientEnt) {
        return new Client(
                clientEnt.getLogin(),
                clientEnt.getPassword(),
                clientEnt.getFirstName(),
                clientEnt.getLastName(),
                convertAddressToDomainModel(clientEnt.getAddress())
        );
    }

    public Admin convertAdminToDomainModel(AdminEnt adminEnt) {
        return new Admin(
                adminEnt.getLogin(),
                adminEnt.getPassword(),
                adminEnt.getFavouriteIceCream()
        );
    }

    public Employee convertEmployeeToDomainModel(EmployeeEnt employeeEnt) {
        return new Employee(employeeEnt.getLogin(), employeeEnt.getPassword(), employeeEnt.getDesk());
    }

    private Address convertAddressToDomainModel(AddressEnt addressEnt) {
        return new Address(
                addressEnt.getCity(),
                addressEnt.getStreet(),
                addressEnt.getStreetNr()
        );
    }
}
