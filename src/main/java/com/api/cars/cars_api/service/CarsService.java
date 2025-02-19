package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Cars;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CarsService {

    /**
     * Abstract method to return all the Cars objects from the
     * database
     * @return ResponseEntity<?> with the all the Cars objects
     */
    ResponseEntity<?> getAllCars();

    /**
     * Abstract method to return a specific Car object from
     * the database, based on the carId
     * @param carId
     * @return ResponseEntity<?> with the Car object
     */
    ResponseEntity<?> getSpecificCar(String carId);

    /**
     * Abstract method to return all Cars with the same brand
     * @param brand
     * @return ResponseEntity<?> with all the Cars objects
     */
    ResponseEntity<?> getCarsByBrand(String brand);

    /**
     * Abstract method to return all Cars with the same version
     * @param version
     * @return ResponseEntity<?> with all the Cars objects
     */
    ResponseEntity<?> getCarsByVersion(String version);
    /**
     * Abstract method to create a new Car object and persist it on the
     * database
     * @param carBrand
     * @param version
     * @return ResponseEntity<?> with the Car object created
     */
    ResponseEntity<?> createNewCar(String carBrand, String version);

    /**
     * Abstract method to delete a Car object from the database,
     * based on the CarId
     * @param carId
     * @return ResponseEntity<?> with the success or fail of the deletion
     */
    ResponseEntity<?> deleteSpecificCar(String carId);

    /**
     * Abstract method to delete all Car objects from the database,
     * with success or fail of the deletion
     * @return ResponseEntity<?> with a message of success or fail
     */
    ResponseEntity<?> deleteAllCars();

    /**
     * Abstract method to update the Car object and persist it on
     * the database, based on carId
     * @param carId
     * @param carToUpdate
     * @return ResponseEntity<?> with the Car object updated or a fail message
     */
    ResponseEntity<?> updateSpecificCar(String carId, Cars carToUpdate);

    /**
     * Abstract method to update partially the Car object and persist it
     * on the database
     * @param carId
     * @param dataToUpdate
     * @return ResponseEntity<?> with the Car object updated or a fail message
     */
    ResponseEntity<?> updateASpecificCarDetail(String carId, Map<String, Object> dataToUpdate);


}
