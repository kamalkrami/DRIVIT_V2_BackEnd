package net.kamal.carrentalservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.carrentalservices.client.CarsRestClient;
import net.kamal.carrentalservices.client.UsersRestClient;
import net.kamal.carrentalservices.entities.CarRental;
import net.kamal.carrentalservices.model.Cars;
import net.kamal.carrentalservices.model.Users;
import net.kamal.carrentalservices.repositories.CarRentalRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CarRentalController {

    CarRentalRepository carRentalRepository;
    CarsRestClient carsRestClient;
    UsersRestClient usersRestClient;

    @GetMapping("/carrental")
    public List<CarRental> getAllCarRental(){
        List<CarRental> carRentalList = carRentalRepository.findAll();
        carRentalList.forEach(carRental -> {
            carRental.setCars(carsRestClient.getCarsById(carRental.getId_car()));
            carRental.setUsers(usersRestClient.findUserById(carRental.getId_user()));
        });
        return carRentalList;
    }

    @GetMapping("/carrental/{id_user}")
    public CarRental getCarRental(@PathVariable Long id_user){
        CarRental carRental = carRentalRepository.findById(id_user).get();
        Cars cars = carsRestClient.getCarsById(carRental.getId_car());
        Users users = usersRestClient.findUserById(carRental.getId_user());
        carRental.setUsers(users);
        carRental.setCars(cars);
        return carRental;
    }

}
