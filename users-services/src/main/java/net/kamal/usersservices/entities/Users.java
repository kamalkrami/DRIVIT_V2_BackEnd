package net.kamal.usersservices.entities;

import jakarta.persistence.*;
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
    private String cin;
    private String email;
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    private UserType status;
}
