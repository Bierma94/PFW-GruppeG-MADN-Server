package pfw.gruppeG.MADN.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

/**
 * UserDto
 * Data Transfer Object for the User
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    private String msg;
    private Map<String, String> user;


}
