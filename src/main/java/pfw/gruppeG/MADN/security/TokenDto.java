package pfw.gruppeG.MADN.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * TokenDto
 * Data Transfer Object for the Token
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 - 21.10.2024
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class TokenDto extends RepresentationModel<TokenDto> {
    private String token;
}
