package net.kamal.carservices;

import net.kamal.carservices.client.UserRestClient;
import net.kamal.carservices.entities.Cars;
import net.kamal.carservices.enums.Status_add;
import net.kamal.carservices.enums.Status_dispo;
import net.kamal.carservices.enums.UserType;
import net.kamal.carservices.model.Users;
import net.kamal.carservices.repositories.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class CarServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServicesApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository, UserRestClient userRestClient){
        return args -> {
            List<Users> usersList = userRestClient.getUsersByType(UserType.SUPPLIER);

            if (usersList.isEmpty()) {
                System.out.println("No users found of type SUPPLIER.");
                return;
            }

            // Iterate through each user
            usersList.forEach(user -> {
                // Create the list of cars to save
                List<Cars> carsList = List.of(
                        Cars.builder()
                                .users(user)
                                .id_user(user.getId_user())
                                .carName("BMW")
                                .carModel("2024")
                                .carMatricul("I153247")
                                .carImage("https://imagecar.com")
                                .statusDipo(Status_dispo.AVAILABLE)
                                .statusAdd(Status_add.PENDING)
                                .build()
                );
                carRepository.saveAll(carsList);
            });
        };
    }
}
