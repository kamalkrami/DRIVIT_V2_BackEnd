package net.kamal.requestsupplierservices.entities;

import jakarta.persistence.*;
import lombok.*;
import net.kamal.requestsupplierservices.enums.Status;
import net.kamal.requestsupplierservices.model.Users;

import java.time.LocalDate;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class RequestSupplier {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_request;

    @Transient
    private Users users;
    private Long id_user;

    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
