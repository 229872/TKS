package soap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.soap.EquipmentInputSoapDTO;
import pl.soap.EquipmentSoapControllerService;
import pl.soap.ObjectNotFoundSoapException_Exception;
import pl.soap.SoapConflictException_Exception;
import pl.soap.SoapIllegalModificationException_Exception;


import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@Testcontainers
public class SoapExampleIT extends AppDeploymentTestConfig {


    EquipmentInputSoapDTO eq = new EquipmentInputSoapDTO();
    URL url;

    SoapExampleIT() throws MalformedURLException {
        String serviceUrl = String.format("http://localhost:%d/soap/EquipmentSoapControllerService",
                payara.getMappedPort(8080));
        url = new URL(serviceUrl);
    }

    @BeforeEach
    void beforeEach() {
        eq.setName(randStr(10));
        eq.setBail(2);
        eq.setFirstDayCost(1);
        eq.setNextDaysCost(3);
//        eq.setDescription("seven");
    }

    @Test
    void createEmployee_correct()  {
        EquipmentSoapControllerService soap = new EquipmentSoapControllerService(url);

        int initSize = soap.getEquipmentSoapControllerPort().getAll().size();
        soap.getEquipmentSoapControllerPort().add(eq);
        assertEquals(initSize + 1, soap.getEquipmentSoapControllerPort().getAll().size());
    }

    @Test
    void removeEmployee_correct() {
        EquipmentSoapControllerService soap = new EquipmentSoapControllerService(url);

        int initSize = soap.getEquipmentSoapControllerPort().getAll().size();
        var eq1 = soap.getEquipmentSoapControllerPort().add(eq);
        assertEquals(initSize + 1, soap.getEquipmentSoapControllerPort().getAll().size());

        try {
            soap.getEquipmentSoapControllerPort().remove(eq1.getEntityId());
        } catch(ObjectNotFoundSoapException_Exception | SoapConflictException_Exception e) {
            fail();
        }
    }

    @Test
    void updateEmployee_correct() throws ObjectNotFoundSoapException_Exception {
        EquipmentSoapControllerService soap = new EquipmentSoapControllerService(url);

        int initSize = soap.getEquipmentSoapControllerPort().getAll().size();
        var eq1 = soap.getEquipmentSoapControllerPort().add(eq);
        assertEquals(initSize + 1, soap.getEquipmentSoapControllerPort().getAll().size());

        String newName = "__1__";
        eq.setName(newName);
        try {
            soap.getEquipmentSoapControllerPort().update(eq1.getEntityId(), eq);
        } catch(ObjectNotFoundSoapException_Exception | SoapIllegalModificationException_Exception e) {
            fail();
        }
        var eq2 = soap.getEquipmentSoapControllerPort().get(eq1.getEntityId());
        assertEquals(newName, eq2.getName());
    }


    private static String randStr(int size) {
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
