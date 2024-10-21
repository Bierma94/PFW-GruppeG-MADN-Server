package pfw.gruppeG.MADN.user.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pfw.gruppeG.MADN.user.UserServiceAPI;
import pfw.gruppeG.MADN.user.dto.UserDto;

import pfw.gruppeG.MADN.user.exception.UserException;
import pfw.gruppeG.MADN.user.model.UserRole;


import java.util.Map;
import java.util.Objects;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * UserController
 * Copyright (c) Jannes Bierma -All Rights Reserved.
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserServiceAPI userService;

  @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(
            @RequestBody @Valid RegisterDto registerUserDto,
            BindingResult bindingResult) {
      UserDto userDto = null;
      if(bindingResult.hasErrors()) {
          return ResponseEntity
                  .status(HttpStatus.BAD_REQUEST)
                  .body(UserDto.builder()
                          .msg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()).build());
      }
      try {
          Map<String,String> user = userService.create(
                  registerUserDto.username(),
                  registerUserDto.password(),
                  registerUserDto.email(),
                  UserRole.USER.toString());
          user.remove("password");
          userDto = UserDto.builder()
                  .msg("User successfully registered")
                  .user(user)
                  .build();
          userDto.add(linkTo(methodOn(UserController.class).getUser()).withRel("user"));
          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(userDto);
      } catch (UserException e) {
          log.debug(e.getMessage());
          return ResponseEntity
                  .status(HttpStatus.BAD_REQUEST)
                  .body(UserDto.builder().msg(e.getMessage()).build());
      }
  }
    @GetMapping
    public ResponseEntity<UserDto> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            Map<String, String> user = userService.getUser(authentication.getName());
            UserDto userDto = UserDto.builder()
                    .user(user)
                    .build();
            return ResponseEntity.ok(userDto);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(UserDto.builder().msg(e.getMessage()).build());
        }
    }

}

record RegisterDto (
        @NotNull @Length(min = 4, max = 20,
                message = "Username must be between 4 and 20 characters")
        String username,
        @NotNull @Length(min = 8, max = 30,
                message = "Password must be between 8 and 30 characters")
        String password,
        @Email(message = "Email should be valid")
        String email
){}

