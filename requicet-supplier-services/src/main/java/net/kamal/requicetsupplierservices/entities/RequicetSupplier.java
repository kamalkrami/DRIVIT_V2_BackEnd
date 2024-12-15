package net.kamal.requicetsupplierservices.entities;

import jakarta.persistence.*;
import lombok.*;
import net.kamal.requicetsupplierservices.enums.Status;
import net.kamal.requicetsupplierservices.model.Users;

import java.time.LocalDate;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString @Builder
public class RequicetSupplier {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_requicet;

    @Transient
    private Users users;
    private Long id_users;

    private LocalDate requicetDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
