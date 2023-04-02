package pl.lodz.p.edu.adapter.repository.clients;

import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.AdminEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.EmployeeEnt;

public class DataFaker {

    public static AddressEnt getAddress() {
        return new AddressEnt(randStr(7), randStr(10), randStr(4));
    }

    public static ClientEnt getClient(AddressEnt a) {
        return new ClientEnt(randStr(7), "CLIENT",// idType.values()[(int)(Math.random() * 2) % 2],
                randStr(10), randStr(10), a);
    }

    public static ClientEnt getClient() {
        AddressEnt a = getAddress();
        return new ClientEnt(randStr(7), "CLIENT",
                randStr(10), randStr(10), a);
    }

    public static AdminEnt getAdmin() {
        return new AdminEnt(randStr(10), "ADMIN", randStr(20));
    }

    public static EmployeeEnt getEmployee() {
        return new EmployeeEnt(randStr(10), "EMPLOYEE", randStr(20));
    }


//    public static EquipmentEnt getEquipment() {
//        return new EquipmentEnt(Math.random() * 100, Math.random() * 200,
//                Math.random() * 1000, randStr(10));
//    }


    // source: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    public static String randStr(int size) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}

