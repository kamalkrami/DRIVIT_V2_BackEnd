package net.kamal.carservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.carservices.client.UserRestClient;
import net.kamal.carservices.entities.Cars;
import net.kamal.carservices.model.Users;
import net.kamal.carservices.repositories.CarRepository;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RefreshScope
public class CarController {
    private CarRepository carRepository;
    private UserRestClient userRestClient;

    @GetMapping("/cars")
    public List<Cars> getAllCars(){
        List<Cars> carsList = carRepository.findAll();
        carsList.forEach(cars -> {
            cars.setUsers(userRestClient.findUserById(cars.getUser_id()));
        });
        return carsList;
    }

    @GetMapping("/cars/{id_car}")
    public Cars getCarById(@PathVariable Long id_car){
        Cars cars = carRepository.findById(id_car).get();
        Users users = userRestClient.findUserById(cars.getUser_id());
        cars.setUsers(users);
        return carRepository.findById(id_car).get();
    }
}
