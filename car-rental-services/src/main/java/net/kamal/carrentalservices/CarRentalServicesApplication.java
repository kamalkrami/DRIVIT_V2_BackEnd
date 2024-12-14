package net.kamal.carrentalservices;

import net.kamal.carrentalservices.client.CarsRestClient;
import net.kamal.carrentalservices.client.UsersRestClient;
import net.kamal.carrentalservices.entities.CarRental;
import net.kamal.carrentalservices.enums.Status_dipo;
import net.kamal.carrentalservices.enums.Status_rental;
import net.kamal.carrentalservices.enums.UserType;
import net.kamal.carrentalservices.model.Cars;
import net.kamal.carrentalservices.model.Users;
import net.kamal.carrentalservices.repositories.CarRentalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class CarRentalServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalServicesApplication.class, args);
    }

/*    @Bean
    CommandLineRunner commandLineRunner(CarRentalRepository carRentalRepository, CarsRestClient carsRestClient, UsersRestClient usersRestClient){
        return args -> {
            usersRestClient.getUsersByType(UserType.USER).forEach(users_ele -> {
                carsRestClient.getAllCarsByStatusDipo(Status_dipo.AVAILABLE).forEach(cars_ele -> {
                    List<CarRental> carRentalList = List.of(
                            CarRental.builder()
                                    .cars(cars_ele)
                                    .id_car(cars_ele.getId_car())
                                    .users(users_ele)
                                    .id_user(users_ele.getId_user())
                                    .rentalTime(LocalDate.now())
                                    .statusRental(Status_rental.PENDING)
                                    .build()
                    );
                    carRentalRepository.saveAll(carRentalList);
                });
            });
        };
    }*/

    @Bean
    CommandLineRunner commandLineRunner(CarRentalRepository carRentalRepository,
                                        CarsRestClient carsRestClient,
                                        UsersRestClient usersRestClient) {
        return args -> {
            List<Users> users = usersRestClient.getUsersByType(UserType.USER);
            List<Cars> cars = carsRestClient.getAllCarsByStatusDipo(Status_dipo.AVAILABLE);

            if (users.isEmpty() || cars.isEmpty()) {
                System.out.println("No users or cars found!");
                return;
            }

            users.forEach(user -> {
                cars.forEach(car -> {
                    CarRental carRental = CarRental.builder()
                            .cars(car)
                            .id_car(car.getId_car())
                            .users(user)
                            .id_user(user.getId_user())
                            .rentalTime(LocalDate.now())
                            .statusRental(Status_rental.PENDING)
                            .build();

                    try {
                        carRentalRepository.save(carRental);
                        System.out.println("Saved: " + carRental);
                    } catch (Exception e) {
                        System.err.println("Error saving rental: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            });
        };
    }

}
