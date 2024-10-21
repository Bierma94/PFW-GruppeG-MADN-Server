package pfw.gruppeG.MADN.user.wrapper;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pfw.gruppeG.MADN.user.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * UserWrapper
 * Copyright (c) Jannes Bierma -All Rights Reserved.
 *
 * @author Jannes Bierma (jannes.bierma@stud.hs-emden-leer.de)
 * @version 1.0 - 17.10.2024
 */
@Component
@NoArgsConstructor
public class UserWrapper {

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
