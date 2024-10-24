package pfw.gruppeG.MADN.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pfw.gruppeG.MADN.security.TokenDto;
import pfw.gruppeG.MADN.security.service.AuthenticationService;


/**
 * LoginController
 * REST-Controller for the login
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    /** The authentication service */
    private final AuthenticationService authenticationService;

    /**
     * User login with username and password
     * @param loginDto the login data transfer object (username, password)
     * @return the response entity with the token
     */
    @Operation(summary = "Login a user")
    @ApiResponse(responseCode = "200", description = "Get JWT token")
    @ApiResponse(responseCode = "400", description = "Invalid Credentials")
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

/**
 * LoginDto data transfer object for the login
 * @param username username
 * @param password password
 */
record LoginDto(String username, String password) { }