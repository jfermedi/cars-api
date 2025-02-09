package com.api.cars.cars_api.repository;

import com.api.cars.cars_api.model.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Cars, Integer> {
}
