package net.kamal.carservices.entities;

import jakarta.persistence.*;
import lombok.*;
import net.kamal.carservices.enums.Status_add;
import net.kamal.carservices.enums.Status_dipo;
import net.kamal.carservices.model.Users;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class Cars {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_car;

    @Transient
    private Users users;
    private Long user_id;

    private String carName;
    private String carModel;
    private String carMatricul;
    private String carImage;

    @Enumerated(EnumType.ORDINAL)
    private Status_dipo statusDipo;

    @Enumerated(EnumType.ORDINAL)
    private Status_add statusAdd;
}
