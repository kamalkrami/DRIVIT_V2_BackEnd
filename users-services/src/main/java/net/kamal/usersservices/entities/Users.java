package net.kamal.usersservices.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import net.kamal.usersservices.enums.UserType;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;

    @Column(unique = true, nullable = false)
    private String cin;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserType status;
}
