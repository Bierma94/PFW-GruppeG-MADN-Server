package pfw.gruppeG.MADN.user.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * User
 * Represents a User
 *
 * @author Jannes Bierma, Dalila Rustemovic
 * @version 1.0 20.10.2024
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    /** The id of the user */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    /** The username of the user */
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    /** Password of the user */
    @Column(name = "password", nullable = false)
    private String password;
    /** Email of the user */
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    /** Role of the user */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
