package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Cars;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CarsService {

    List<Cars>getAllCars();

    Cars getSpecificCar(String carId);

    ResponseEntity<?> createNewCar(String carBrand, String version);

    ResponseEntity<?> deleteSpecificCar(String carId);

    String deleteAllCars();

    ResponseEntity<Cars> updateSpecificCar(String carId, Cars carToUpdate);

    ResponseEntity<Cars> updateASpecificCarDetail(String carId, Map<String, Object> dataToUpdate);


}
