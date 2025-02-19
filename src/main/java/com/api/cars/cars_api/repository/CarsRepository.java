package com.api.cars.cars_api.repository;

import com.api.cars.cars_api.model.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarsRepository extends JpaRepository<Cars, Integer> {

    /**
     * Abstract method to find the Cars with the same brand
     * @param brand
     * @return List<Cars> with the same brand
     */
    List<Cars> findByBrand(String brand);

    /**
     * Abstract method to find the Cars with the same version
     * @param version
     * @return List<Cars> with the same version
     */
    List<Cars> findByVersion(String version);
}
