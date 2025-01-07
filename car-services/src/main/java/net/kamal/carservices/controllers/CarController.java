package net.kamal.carservices.controllers;

import lombok.AllArgsConstructor;
import net.kamal.carservices.client.UserRestClient;
import net.kamal.carservices.entities.Cars;
import net.kamal.carservices.enums.Status_add;
import net.kamal.carservices.enums.Status_dispo;
import net.kamal.carservices.model.Users;
import net.kamal.carservices.repositories.CarRepository;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            cars.setUsers(userRestClient.findUserById(cars.getId_user()));
        });
        return carsList;
    }

    @GetMapping("/cars/{id_car}")
    public Cars getCarById(@PathVariable Long id_car){
        Cars cars = carRepository.findById(id_car).get();
        Users users = userRestClient.findUserById(cars.getId_user());
        cars.setUsers(users);
        return carRepository.findById(id_car).get();
    }

    @GetMapping("/cars/dispo/{car_dispo_status}/{car_add_status}")
    public List<Cars> getCarsByStatusDipoAndStatusAdd(@PathVariable Status_dispo car_dispo_status, @PathVariable Status_add car_add_status){
        List<Cars> carsList = carRepository.getCarsByStatusDipoAndStatusAdd(car_dispo_status,car_add_status);
        carsList.forEach(cars -> {
            cars.setUsers(userRestClient.findUserById(cars.getId_user()));
        });
        return carsList;
    }

    @PostMapping("/cars/addCar")
    public ResponseEntity<Map<String, Object>> addCar(@RequestBody Cars car){
        if (car == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "msg", "Car Cannot Be Null",
                "status", 400
        ));
        carRepository.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "Car Added Successfully",
                "status", 201
        ));
    }

    @DeleteMapping("cars/deletecar/{carId}")
    public ResponseEntity<Map<String, Object>> deleteCar(@PathVariable Long carId){
        if (carId == null) {
            //to check later
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "Car ID cannot be null",
                    "status", 400
            ));
        }
        Optional<Cars> car = carRepository.findById(carId);
        if (car.isPresent()){
            carRepository.deleteById(carId);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "msg", "Car has been deleted successfully",
                    "status", 200
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "Car not found",
                    "status", 404
            ));
        }
    }

    @PutMapping("cars/updatecar")
    public ResponseEntity<Map<String, Object>> updateCar(@RequestBody Cars car){
        //to check later
        if (car ==  null || car.getId_car() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "msg", "Car or Car ID cannot be null",
                    "status", 404
            ));
        }
        Cars updatedCar = carRepository.findById(car.getId_car()).orElse(null);
        if(updatedCar == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "msg", "Car not found",
                    "status", 404
            ));
        }
        updatedCar.setCarName(car.getCarName());
        updatedCar.setCarImage(car.getCarImage());
        updatedCar.setCarMatricul(car.getCarMatricul());
        updatedCar.setCarModel(car.getCarModel());
        updatedCar.setStatusAdd(car.getStatusAdd());
        updatedCar.setStatusDipo(car.getStatusDipo());
        updatedCar.setUsers(car.getUsers());
        updatedCar.setId_user(car.getId_user());


        carRepository.save(updatedCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "msg", "Car has been updated successfully",
                "status", 201
        ));
    }
}
