package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Brands;
import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.model.Version;
import com.api.cars.cars_api.repository.CarsRepository;
import com.api.cars.cars_api.validator.ValidationFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CarsServiceImpl implements CarsService{

 @Autowired
 CarsRepository carsRepository;
 ValidationFields validationFields = new ValidationFields();
    @Override
    public List<Cars> getAllCars() {
        return carsRepository.findAll();
    }

    @Override
    public Cars getSpecificCar(String carId) {
       Optional<Cars> carToBeFound = null;
        if(validationFields.validateId(carId)){
            carToBeFound = carsRepository.findById(Integer.parseInt(carId));
            return carToBeFound.get();
        }
        return carToBeFound.get();
    }

    @Override
    public ResponseEntity<?> createNewCar(String carBrand, String version) {
        Cars carToBeCreated = null;
        if(validationFields.validateCarBrand(carBrand) && validationFields.validateCarVersion(version)) {
          Brands carBrandFinal = defineBrand(carBrand);
          Float price = carBrandFinal.price;
          String carVersionFinal = defineVersion(version);
             carToBeCreated = new Cars(carBrandFinal.name(),price ,carVersionFinal);
            carsRepository.save(carToBeCreated);
            return ResponseEntity.status(HttpStatus.CREATED).body(carToBeCreated);
        }else{
            Map<String, String> errorResponse = new HashMap<String, String>();
            errorResponse.put("message", "Invalid car brand or version ! Please provide a car brand from 1-7, and version from 1-5");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @Override
    public ResponseEntity<?> deleteSpecificCar(String carId) {
        Map<String, String> result = new HashMap<>();
        if(validationFields.validateId(carId)){
            Integer carIdToSearch = Integer.parseInt(carId);
            Optional<?> carToBeFound = carsRepository.findById(carIdToSearch);
           if(carToBeFound.isPresent()){
               carsRepository.deleteById(carIdToSearch);
               result.put("message", "Car deleted with success");
              return ResponseEntity.status(HttpStatus.OK).body(result);
           }else{
               result.put("message", "Car not found, please provide a existing carId");
             return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
           }
        }else{
           result.put("message", "Invalid carId, please provide a valid one");
        return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
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



    private String defineVersion(String version) {
        Integer numVersion = Integer.parseInt(version);
        String versionFinal = "";
        switch (numVersion){
            case 1:
                versionFinal = Version.SEDAN.name();
            case 2:
                versionFinal = Version.HATCH.name();
            case 3:
                versionFinal = Version.SPORT.name();
            case 4:
                versionFinal = Version.SUV.name();
            case 5:
                versionFinal = Version.PICKUP.name();
        }

        return versionFinal;
    }

    private Brands defineBrand(String carBrand) {
        Integer num = Integer.parseInt(carBrand);
        Brands brandToReturn = null;
        switch (num){
            case 1:
                brandToReturn = Brands.VOLKSWAGEN;
            case 2:
                brandToReturn = Brands.MERCEDEZ;
            case 3:
                brandToReturn = Brands.BMW;
            case 4:
                brandToReturn = Brands.FIAT;
            case 5:
                brandToReturn = Brands.MG;
            case 6:
                brandToReturn = Brands.OPEL;
            case 7:
                brandToReturn = Brands.RENAULT;
        }

        return brandToReturn;
    }
}
