package net.kamal.carservices.entities;

import jakarta.persistence.*;
import lombok.*;
import net.kamal.carservices.enums.Status_add;
import net.kamal.carservices.enums.Status_dispo;
import net.kamal.carservices.model.Users;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class Cars {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_car;

    @Transient
    private Users users;
    private Long id_user;

    private String carName;
    private String carModel;
    private String carMatricul;
    private String carImage;

    @Enumerated(EnumType.STRING)
    private Status_dispo statusDipo;

    @Enumerated(EnumType.STRING)
    private Status_add statusAdd;
}
