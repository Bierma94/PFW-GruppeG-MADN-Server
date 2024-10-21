package pfw.gruppeG.MADN.user.wrapper;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pfw.gruppeG.MADN.user.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * UserWrapper
 * Wraps a User object into a Map<String, String>
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@Component
@NoArgsConstructor
public class UserWrapper {

    /**
     * Maps a User object to a Map<String, String>
     * @param user the user to be mapped
     * @return Map<String, String> the mapped user
     */
    public  Map<String, String> map(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("email", user.getEmail());
        map.put("role", user.getRole().toString());
        return map;
    }
}
