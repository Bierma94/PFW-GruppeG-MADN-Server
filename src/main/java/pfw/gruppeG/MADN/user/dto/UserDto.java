package pfw.gruppeG.MADN.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pfw.gruppeG.MADN.user.model.User;

/**
 * UserDto
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 20.10.2024
 */
@Getter
@Setter
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {

    private User user;

}
