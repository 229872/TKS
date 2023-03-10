package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user;

import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.AdminEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.EmployeeEnt;
import pl.lodz.p.edu.core.domain.model.Address;
import pl.lodz.p.edu.core.domain.model.users.Admin;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.Employee;

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
