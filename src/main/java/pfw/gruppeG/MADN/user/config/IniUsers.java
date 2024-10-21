package pfw.gruppeG.MADN.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pfw.gruppeG.MADN.user.UserServiceAPI;

/**
 * IniUsers
 *
 * @author Jannes Bierma Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@Component
@RequiredArgsConstructor
public class IniUsers implements CommandLineRunner {

    private final UserServiceAPI userService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i <10 ; i++) {
           userService.create("user"+i,"password"+i,"user,email"+i,"USER");

        }
    }
}
