package com.api.cars.cars_api.controller;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.service.CarsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cars")
public class CarsController {
    @Autowired
    CarsServiceImpl carsService;

    /**
     * GET endpoint for returning all the cars
     * @return a List<Cars>
     */
    @GetMapping("/all")
    ResponseEntity<?> getAllCars(){
        return carsService.getAllCars();
    }

    /**
     * GET endpoint for returning a specific car based on the carId
     * @param carId
     * @return ResponseEntity<?> with the details of the car
     */
    @GetMapping("/{carId}")
    ResponseEntity<?> getASpecificCarInfo(@PathVariable String carId){
        return carsService.getSpecificCar(carId);
    }

    /**
     * GET endpoint for returning all Cars objects with the same Brand
     * @param carBrand
     * @return ResponseEntity<?> with the Cars objects
     */
    @GetMapping("brand/{carBrand}")
    ResponseEntity<?> getCarsByBrand(@PathVariable String carBrand){
        return carsService.getCarsByBrand(carBrand);
    }

    /**
     * GET endpoint for returning all Cars objects with the same version
     * @param carVersion
     * @return ResponseEntity<?> with the Cars objects
     */
    @GetMapping("version/{carVersion}")
    ResponseEntity<?> getCarsByVersion(@PathVariable String carVersion){
        return carsService.getCarsByVersion(carVersion);
    }
    /**
     * POST endpoint for creating a car object
     * @param carBrand
     * @param carVersion
     * @return ResponseEntity<?> with the car object created
     */
    @PostMapping("/createCar/{carBrand}/{carVersion}")
    ResponseEntity<?> createANewCar(@PathVariable String carBrand, @PathVariable String carVersion){
        return carsService.createNewCar(carBrand, carVersion);
    }

    /**
     * DELETE endpoint for deleting a specific car based on carId
     * @param carId
     * @return ResponseEntity<?> with a message of success or fail of the deletion
     */
    @DeleteMapping("/deleteCar/{carId}")
    ResponseEntity<?> deleteACar(@PathVariable String carId){
        return carsService.deleteSpecificCar(carId);
    }

    /**
     * DELETE endpoint for deleting all the cars
     * @return ResponseEntity<?> with a message of success or fail of the deletion
     */
    @DeleteMapping("/deleteAllCars")
    ResponseEntity<?> deleteAllCars(){
        return carsService.deleteAllCars();
    }

    /**
     * PUT endpoint for updating a specific car based on carId
     * @param carId
     * @param carToUpdate
     * @return ResponseEntity<?> with the car object updated, or a fail message
     */
    @PutMapping("/updateCar/{carId}")
    ResponseEntity<?> updateACar(@PathVariable String carId, @RequestBody Cars carToUpdate){
        return carsService.updateSpecificCar(carId, carToUpdate);
    }

    /**
     * PATCH endpoint for updating a specific detail of a car based on carId
     * @param carId
     * @param dataToUpdate
     * @return ResponseEntity<?> with the car object updated, or a fail message
     */
    @PatchMapping("/updateCarPart/{carId}")
    ResponseEntity<?> updateACarPartially(@PathVariable String carId, @RequestBody Map<String, Object> dataToUpdate){
        return carsService.updateASpecificCarDetail(carId, dataToUpdate);
    }
}
