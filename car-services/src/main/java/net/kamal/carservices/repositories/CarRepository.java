package net.kamal.carservices.repositories;

import feign.Param;
import net.kamal.carservices.entities.Cars;
import net.kamal.carservices.enums.Status_add;
import net.kamal.carservices.enums.Status_dispo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Cars,Long> {
    public List<Cars> getCarsByStatusDipoAndStatusAdd(Status_dispo statusDipo, Status_add statusAdd);

    @Query("SELECT cars FROM Cars cars WHERE cars.id_user = :id_supplier")
    public List<Cars> getCarsByID_Supplier(@Param("id_supplier") Long id_supplier);
}
