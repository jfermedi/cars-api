package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Cars;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CarsService {

    /**
     * Abstract method to return all the Cars objects from the
     * database
     *
     * @return ResponseEntity<Map<String, Object>> with the all the Cars objects
     */
    ResponseEntity<Map<String, Object>> getAllCars();

    /**
     * Abstract method to return a specific Car object from
     * the database, based on the carId
     *
     * @param carId
     * @return ResponseEntity<Map<String, Object>> with the Car object
     */
    ResponseEntity<Map<String,Object>> getSpecificCar(String carId);

    /**
     * Abstract method to return all Cars with the same brand
     *
     * @param brand
     * @return ResponseEntity<Map<String, Object>> with all the Cars objects
     */
    ResponseEntity<Map<String, Object>> getCarsByBrand(String brand);

    /**
     * Abstract method to return all Cars with the same version
     *
     * @param version
     * @return ResponseEntity<Map<String, Object>> with all the Cars objects
     */
    ResponseEntity<Map<String, Object>> getCarsByVersion(String version);
    /**
     * Abstract method to create a new Car object and persist it on the
     * database
     * @param carBrand
     * @param version
     * @return ResponseEntity<Map<String, Object>> with the Car object created
     */
    ResponseEntity<Map<String, Object>> createNewCar(String carBrand, String version);

    /**
     * Abstract method to delete a Car object from the database,
     * based on the CarId
     *
     * @param carId
     * @return ResponseEntity<Map<String, Object>> with the success or fail of the deletion
     */
    ResponseEntity<Map<String, Object>> deleteSpecificCar(String carId);

    /**
     * Abstract method to delete all Car objects from the database,
     * with success or fail of the deletion
     *
     * @return ResponseEntity<Map<String, Object>> with a message of success or fail
     */
    ResponseEntity<Map<String, Object>> deleteAllCars();

    /**
     * Abstract method to update the Car object and persist it on
     * the database, based on carId
     *
     * @param carId
     * @param carToUpdate
     * @return ResponseEntity<Map<String, Object>> with the Car object updated or a fail message
     */
    ResponseEntity<Map<String, Object>> updateSpecificCar(String carId, Cars carToUpdate);

    /**
     * Abstract method to update partially the Car object and persist it
     * on the database
     *
     * @param carId
     * @param dataToUpdate
     * @return ResponseEntity<Map<String, Object>> with the Car object updated or a fail message
     */
    ResponseEntity<Map<String, Object>> updateASpecificCarDetail(String carId, Map<String, Object> dataToUpdate);


}
