package pfw.gruppeG.MADN.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pfw.gruppeG.MADN.user.dto.UserDto;

import pfw.gruppeG.MADN.user.model.User;
import pfw.gruppeG.MADN.user.repository.UserRepository;

import java.util.Optional;

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
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        Link link = linkTo(methodOn(UserController.class).getUser(id)).withSelfRel();

        if (user.isPresent()) {
            UserDto userDto = new UserDto(user.get());
            userDto.setUser(user.get());
            userDto.add(link);
            userDto.add(linkTo(methodOn(UserController.class).getUser(user.get().getUsername())).withRel("user"));
            userDto.add(linkTo(methodOn(UserController.class)).withSelfRel());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.badRequest().body(new UserDto(null));
    }

    @GetMapping("/user/name/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Link link = linkTo(methodOn(UserController.class).getUser(username)).withSelfRel();
        if (user.isPresent()) {
            UserDto userDto = new UserDto(user.get());
            userDto.setUser(user.get());
            userDto.add(link);
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.badRequest().body(new UserDto(null));
    }

}
