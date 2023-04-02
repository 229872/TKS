import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class ExampleIT {

    @Inject
    private EquipmentRepository equipmentRepository;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "pl.lodz.p.edu")
                .addPackages(true, "org.postgresql")
                .addPackages(true, "org.hibernate")
                .addPackages(true, "net.bytebuddy")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"");
    }

    @Test
    void test() {
        List<EquipmentEnt> all = equipmentRepository.getAll();
        assertEquals(0, all.size());
    }
}
