package net.kamal.carrentalservices.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import net.kamal.carrentalservices.enums.Status_add;
import net.kamal.carrentalservices.enums.Status_dipo;

@Getter @Setter @ToString
public class Cars {
    private Long id_car;
    private Users users;
    private Long id_user;
    private String carName;
    private String carModel;
    private String carMatricul;
    private String carImage;
    private Long carPrix;

    @Enumerated(EnumType.STRING)
    private Status_dipo statusDipo;
    @Enumerated(EnumType.STRING)
    private Status_add statusAdd;
}
