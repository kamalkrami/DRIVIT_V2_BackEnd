package net.kamal.carrentalservices.repositories;

import feign.Param;
import net.kamal.carrentalservices.entities.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRentalRepository extends JpaRepository<CarRental,Long> {
    @Query("SELECT c FROM CarRental c WHERE c.id_user = :id_user")
    List<CarRental> findAllByIduser(@Param("id_user") Long id_user);
}
