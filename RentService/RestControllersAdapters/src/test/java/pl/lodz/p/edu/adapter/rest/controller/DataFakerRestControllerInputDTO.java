//package pl.lodz.p.edu.adapter.rest.controller;
//
//
//import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
//import pl.lodz.p.edu.adapter.rest.dto.input.ClientInputDTO;
//
//public class DataFakerRestControllerInputDTO {
//
//    public static AddressDTO getAddress() {
//        return new AddressDTO(randStr(7), randStr(10), randStr(4));
//    }
//
//    public static ClientInputDTO getClient(AddressDTO a) {
//        return new ClientInputDTO(randStr(7), "CLIENT123",// idType.values()[(int)(Math.random() * 2) % 2],
//                randStr(10), randStr(10), a);
//    }
//
//    public static ClientInputDTO getClient() {
//        AddressDTO a = getAddress();
//        return new ClientInputDTO(randStr(7), "CLIENT123",
//                randStr(10), randStr(10), a);
//    }
//
//    public static AdminInputDTO getAdmin() {
//        return new AdminInputDTO(randStr(10), "ADMIN123", randStr(20));
//    }
//
//    public static EmployeeInputDTO getEmployee() {
//        return new EmployeeInputDTO(randStr(10), "EMPLOYEE", randStr(20));
//    }
//
//
//    public static EquipmentInputDTO getEquipment() {
//        return new EquipmentInputDTO(randStr(10), Math.random() * 100, Math.random() * 200,
//                Math.random() * 1000, randStr(10));
//    }
//
//
//    // source: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
//    public static String randStr(int size) {
//        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//                + "0123456789"
//                + "abcdefghijklmnopqrstuvxyz";
//
//        StringBuilder sb = new StringBuilder(size);
//        for (int i = 0; i < size; i++) {
//            int index = (int) (AlphaNumericString.length() * Math.random());
//            sb.append(AlphaNumericString.charAt(index));
//        }
//        return sb.toString();
//    }
//}
//
