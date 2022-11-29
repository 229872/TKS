package pl.lodz.p.edu.rest.repository;

import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.users.Admin;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.Employee;

import java.time.LocalDateTime;

public class DataFaker {

    public static Address getAddress() {
        return new Address(randStr(7), randStr(10), randStr(4));
    }

    public static Client getClient(Address a) {
        return new Client(randStr(7), // idType.values()[(int)(Math.random() * 2) % 2],
                randStr(10), randStr(10), a);
    }

    public static Client getClient() {
        Address a = getAddress();
        return new Client(randStr(7),
                randStr(10), randStr(10), a);
    }

    public static Admin getAdmin() {
        return new Admin(randStr(10), randStr(20));
    }

    public static Employee getEmployee() {
        return new Employee(randStr(10), randStr(20));
    }


    public static Equipment getEquipment() {
        return new Equipment(Math.random() * 100, Math.random() * 200,
                Math.random() * 1000, randStr(10));
    }


    // source: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    public static String randStr(int size)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
