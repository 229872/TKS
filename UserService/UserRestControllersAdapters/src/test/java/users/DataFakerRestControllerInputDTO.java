package users;

import pl.lodz.p.edu.adapter.rest.users.dto.AddressDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.EmployeeInputDTO;

public class DataFakerRestControllerInputDTO {

  public static AddressDTO getAddress() {
    return new AddressDTO(randStr(7), randStr(10), randStr(4));
  }

  public static ClientInputDTO getClient(AddressDTO a) {
    return new ClientInputDTO(randStr(7), "CLIENT123",// idType.values()[(int)(Math.random() * 2) % 2],
            randStr(10), randStr(10), a);
  }

  public static ClientInputDTO getClient() {
    AddressDTO a = getAddress();
    return new ClientInputDTO(randStr(7), "CLIENT123",
            randStr(10), randStr(10), a);
  }

  public static AdminInputDTO getAdmin() {
    return new AdminInputDTO(randStr(10), "ADMIN123", randStr(20));
  }

  public static EmployeeInputDTO getEmployee() {
    return new EmployeeInputDTO(randStr(10), "EMPLOYEE", randStr(20));
  }


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
