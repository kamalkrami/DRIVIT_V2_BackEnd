package net.kamal.usersservices.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AuthRequest {
    private String email;
    private String passWord;
}
