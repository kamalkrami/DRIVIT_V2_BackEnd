package net.kamal.carrentalservices.repositories;

import net.kamal.carrentalservices.entities.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRepository extends JpaRepository<CarRental,Long> {
}
