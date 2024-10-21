package pfw.gruppeG.MADN.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfw.gruppeG.MADN.security.JsonWebToken;
import pfw.gruppeG.MADN.security.TokenDto;
import pfw.gruppeG.MADN.security.service.AuthenticationService;
import pfw.gruppeG.MADN.user.UserServiceAPI;

/**
 * LoginController
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = authenticationService.authenticate(loginDto.username(), loginDto.password());
            TokenDto tokenDto = TokenDto.builder()
                    .token(token)
                    .build();
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

record LoginDto(String username, String password) { }