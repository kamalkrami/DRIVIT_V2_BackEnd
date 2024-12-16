package net.kamal.carservices.repositories;

import net.kamal.carservices.entities.Cars;
import net.kamal.carservices.enums.Status_dispo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Cars,Long> {
    public List<Cars> getCarsByStatusDipo(Status_dispo statusDipo);
}
