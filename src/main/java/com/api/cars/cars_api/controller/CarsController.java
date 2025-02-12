package com.api.cars.cars_api.controller;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.service.CarsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cars")
public class CarsController {
    @Autowired
    CarsServiceImpl carsService;

    @GetMapping("/all")
    List<Cars> getAllCars(){
        return carsService.getAllCars();
    }

    @GetMapping("/{carId}")
    Cars getASpecificCarInfo(@PathVariable String carId){
        return carsService.getSpecificCar(carId);
    }

    @PostMapping("/createCar/{carBrand}/{carVersion}")
    ResponseEntity<?> createANewCar(@PathVariable String carBrand, @PathVariable String carVersion){
        return carsService.createNewCar(carBrand, carVersion);
    }

    @DeleteMapping("/deleteCar/{carId}")
    String deleteACar(@PathVariable String carId){
        return carsService.deleteSpecificCar(carId);
    }

    @DeleteMapping("/deleteAllCars")
    String deleteAllCars(){
        return carsService.deleteAllCars();
    }

    @PutMapping("/updateCar/{carId}")
    ResponseEntity<Cars> updateACar(@PathVariable String carId, @RequestBody Cars carToUpdate){
        return carsService.updateSpecificCar(carId, carToUpdate);
    }

    @PatchMapping("/updateCarPart/{carId}")
    ResponseEntity<Cars> updateACarPartially(@PathVariable String carId, @RequestBody Map<String, Object> dataToUpdate){
        return carsService.updateASpecificCarDetail(carId, dataToUpdate);
    }
}
