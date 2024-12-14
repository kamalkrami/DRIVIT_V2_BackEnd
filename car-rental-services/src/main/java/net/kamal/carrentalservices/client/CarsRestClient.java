package net.kamal.carrentalservices.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.kamal.carrentalservices.enums.Status_dipo;
import net.kamal.carrentalservices.model.Cars;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CAR-SERVICES")
public interface CarsRestClient {

    @GetMapping("/cars")
    @CircuitBreaker(name="CarServices",fallbackMethod = "defaultgetAllCars")
    List<Cars> getAllCars();

    @GetMapping("/cars/{id_car}")
    @CircuitBreaker(name="CarServices",fallbackMethod = "defaultgetCarsById")
    Cars getCarsById(@PathVariable Long id_car);

    @GetMapping("/cars/dipo/{car_dispo_status}")
    @CircuitBreaker(name="CarServices",fallbackMethod = "defaultgetAllCars")
    List<Cars> getAllCarsByStatusDipo(@PathVariable Status_dipo car_dispo_status);

    default List<Cars> defaultgetAllCars(Exception exception){
        return List.of();
    }

    default Cars defaultgetCarsById(Long id_car,Exception exception){
        Cars cars = new Cars();
        cars.setId_car(id_car);
        cars.setId_user(null);
        cars.setUsers(null);
        cars.setCarImage("Not Available");
        cars.setCarModel("Not Available");
        cars.setCarMatricul("Not Available");
        cars.setCarName("Not Available");
        cars.setStatusAdd(null);
        cars.setStatusDipo(null);
        return cars;
    }

}
