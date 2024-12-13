package net.kamal.carrentalservices.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import net.kamal.carrentalservices.enums.Status_add;
import net.kamal.carrentalservices.enums.Status_dipo;

public class Cars {
    private Long id_car;
    private Users users;
    private String carName;
    private String carModel;
    private String carMatricul;
    private String carImage;

    @Enumerated(EnumType.ORDINAL)
    private Status_dipo statusDipo;
    @Enumerated(EnumType.ORDINAL)
    private Status_add statusAdd;
}
