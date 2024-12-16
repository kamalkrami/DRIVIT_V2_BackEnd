package net.kamal.requestsupplierservices;

import net.kamal.requestsupplierservices.client.UserRestClient;
import net.kamal.requestsupplierservices.entities.RequestSupplier;
import net.kamal.requestsupplierservices.enums.Status;
import net.kamal.requestsupplierservices.enums.UserType;
import net.kamal.requestsupplierservices.repositories.RequestSupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class RequestSupplierServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestSupplierServicesApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRestClient userRestClient, RequestSupplierRepository requicetSupplierRepository){
        return args -> {
            userRestClient.getUsersByType(UserType.USER).forEach(user -> {
                List<RequestSupplier> requicetSuppliers = List.of(
                        RequestSupplier.builder()
                                .users(user)
                                .id_users(user.getId_user())
                                .requestDate(LocalDate.now())
                                .status(Status.PENDING)
                                .build()
                );
                requicetSupplierRepository.saveAll(requicetSuppliers);
            });
        };
    }
}
