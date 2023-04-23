package pl.lodz.p.edu.adapter.repository.users.adapters.mapper.user;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.users.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.users.data.AdminEnt;
import pl.lodz.p.edu.adapter.repository.users.data.ClientEnt;
import pl.lodz.p.edu.adapter.repository.users.data.EmployeeEnt;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Address;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Admin;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Client;
import pl.lodz.p.edu.user.core.domain.usermodel.users.Employee;

@ApplicationScoped
public class UserFromDataToDomainMapper {
    public Client convertClientToDomainModel(ClientEnt clientEnt) {
        return new Client(
                clientEnt.getEntityId(),
                clientEnt.getLogin(),
                clientEnt.getPassword(),
                clientEnt.getFirstName(),
                clientEnt.getLastName(),
                clientEnt.isActive(),
                convertAddressToDomainModel(clientEnt.getAddress())
        );
    }

    public Admin convertAdminToDomainModel(AdminEnt adminEnt) {
        return new Admin(
                adminEnt.getEntityId(),
                adminEnt.getLogin(),
                adminEnt.getPassword(),
                adminEnt.isActive(),
                adminEnt.getFavouriteIceCream()
        );
    }

    public Employee convertEmployeeToDomainModel(EmployeeEnt employeeEnt) {
        return new Employee(
                employeeEnt.getEntityId(),
                employeeEnt.getLogin(),
                employeeEnt.getPassword(),
                employeeEnt.isActive(),
                employeeEnt.getDesk());
    }

    private Address convertAddressToDomainModel(AddressEnt addressEnt) {
        return new Address(
                addressEnt.getCity(),
                addressEnt.getStreet(),
                addressEnt.getStreetNr()
        );
    }
}
