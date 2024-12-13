package net.kamal.carservices;

import net.kamal.carservices.client.UserRestClient;
import net.kamal.carservices.entities.Cars;
import net.kamal.carservices.enums.Status_add;
import net.kamal.carservices.enums.Status_dipo;
import net.kamal.carservices.enums.UserType;
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
            userRestClient.findUsersByStatus(UserType.SUPPLIER).forEach(users -> {
                List<Cars> carsList = List.of(
                  Cars.builder()
                          .users(users)
                          .carName("BMW")
                          .carModel("2024")
                          .carMatricul("I153247")
                          .carImage("https://imagecar.com")
                          .statusDipo(Status_dipo.AVAILABLE)
                          .statusAdd(Status_add.PENDING)
                          .build()
                );
                carRepository.saveAll(carsList);
            });
        };
    }
}
