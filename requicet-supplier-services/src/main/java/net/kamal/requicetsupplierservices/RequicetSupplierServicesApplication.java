package net.kamal.requicetsupplierservices;

import net.kamal.requicetsupplierservices.client.UserRestClient;
import net.kamal.requicetsupplierservices.entities.RequicetSupplier;
import net.kamal.requicetsupplierservices.enums.Status;
import net.kamal.requicetsupplierservices.enums.UserType;
import net.kamal.requicetsupplierservices.repositories.RequicetSupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class RequicetSupplierServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequicetSupplierServicesApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRestClient userRestClient, RequicetSupplierRepository requicetSupplierRepository){
        return args -> {
            userRestClient.getUsersByType(UserType.USER).forEach(user -> {
                List<RequicetSupplier> requicetSuppliers = List.of(
                        RequicetSupplier.builder()
                                .users(user)
                                .id_users(user.getId_user())
                                .requicetDate(LocalDate.now())
                                .status(Status.PENDING)
                                .build()
                );
                requicetSupplierRepository.saveAll(requicetSuppliers);
            });
        };
    }
}
