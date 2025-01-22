package net.kamal.requestsupplierservices.repositories;

import feign.Param;
import net.kamal.requestsupplierservices.entities.RequestSupplier;
import net.kamal.requestsupplierservices.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestSupplierRepository extends JpaRepository<RequestSupplier,Long> {
    @Query("SELECT c FROM RequestSupplier c WHERE c.id_user = :id_user")
    RequestSupplier findByIduser(@Param("id_user") Long id_user);

    List<RequestSupplier> findAllByStatus(Status status);
}
