package soap;

import org.junit.jupiter.api.Test;
import pl.soap.EquipmentSoapController;
import pl.soap.EquipmentSoapControllerService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoapExampleTest {

    @Test
    void sth() {
        EquipmentSoapControllerService soap = new EquipmentSoapControllerService();

        assertEquals(0, soap.getEquipmentSoapControllerPort().getAll().size());
    }
}
