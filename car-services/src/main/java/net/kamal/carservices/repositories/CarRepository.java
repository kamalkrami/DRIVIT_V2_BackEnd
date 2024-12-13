package net.kamal.carservices.repositories;

import net.kamal.carservices.entities.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Cars,Long> {}
