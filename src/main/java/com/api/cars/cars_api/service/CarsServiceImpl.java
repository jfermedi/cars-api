package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CarsServiceImpl implements CarsService{

 @Autowired
 CarsRepository carsRepository;
    @Override
    public List<Cars> getAllCars() {
        return carsRepository.findAll();
    }

    @Override
    public Cars getSpecificCar(String carId) {
        return null;
    }

    @Override
    public ResponseEntity<Cars> createNewCar(String carBrand, String version) {
        return null;
    }

    @Override
    public String deleteSpecificCar(String carId) {
        return "";
    }

    @Override
    public String deleteAllCars() {
        return "";
    }

    @Override
    public ResponseEntity<Cars> updateSpecificCar(String carId, Cars carToUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Cars> updateASpecificCarDetail(String carId, Map<String, Object> dataToUpdate) {
        return null;
    }
}
