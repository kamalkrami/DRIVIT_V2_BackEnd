package net.kamal.carrentalservices.entities;

import jakarta.persistence.*;
import jakarta.ws.rs.FormParam;
import lombok.*;
import net.kamal.carrentalservices.enums.Status_rental;
import net.kamal.carrentalservices.model.Cars;
import net.kamal.carrentalservices.model.Users;

import java.time.LocalDate;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class CarRental {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_carRental;

    @Transient
    private Cars cars;
    private Long id_car;

    @Transient
    private Users users;
    private Long id_user;

    private LocalDate rentalTime;

    @Enumerated(EnumType.STRING)
    private Status_rental statusRental;
}
