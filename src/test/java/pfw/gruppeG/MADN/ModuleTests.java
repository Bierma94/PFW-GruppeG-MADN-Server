package pfw.gruppeG.MADN;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * ModuleTests
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@SpringBootTest
public class ModuleTests {

    ApplicationModules modules = ApplicationModules.of(MadnApplication.class);

    @Test
    void chekModules() {
        modules.stream().forEach(System.out::println);
        modules.verify();
        new Documenter(modules)
                .writeDocumentation()
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}
