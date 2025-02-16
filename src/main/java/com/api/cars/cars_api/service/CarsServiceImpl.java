package com.api.cars.cars_api.service;

import com.api.cars.cars_api.enums.Brands;
import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.enums.Version;
import com.api.cars.cars_api.repository.CarsRepository;
import com.api.cars.cars_api.validator.ValidationFields;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
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
    public ResponseEntity<?> getSpecificCar(String carId) {
       Optional<Cars> carToBeFound = Optional.empty();
        if(validationFields.validateId(carId)){
            carToBeFound = carsRepository.findById(Integer.parseInt(carId));
            if(carToBeFound.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(carToBeFound);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car not found, please provide an existing car id");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car id invalid, please provide a valid one");
        }
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
    public ResponseEntity<?> deleteAllCars() {
        Map<String, String> result = new HashMap<>();
        List<Cars> cars = getAllCars();
        if(!cars.isEmpty()){
            cars.stream().map(Cars::getCarId).forEach(carsRepository::deleteById);
            result.put("message", "All cars deleted with success");
           return ResponseEntity.status(HttpStatus.OK).body(result);
        }else{
            result.put("message", "There aren't no cars in the database");
             return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }

    }

    @Override
    public ResponseEntity<?> updateSpecificCar(String carId, Cars carToUpdate) {
        Map<String, Cars> result = new HashMap<>();
        Map<String, String> fail = new HashMap<>();
        if(validationFields.validateId(carId)){
            Optional<?> carToBeFound = carsRepository.findById(Integer.parseInt(carId));
            if(carToBeFound.isPresent()){
                Cars newCar =  (Cars) carToBeFound.get();
                    newCar.setVersion(carToUpdate.getVersion());
                    newCar.setBrand(carToUpdate.getBrand());
                    newCar.setPrice(carToUpdate.getPrice());
                    carsRepository.save(newCar);
                    result.put("Car updated", newCar);
                    return ResponseEntity.status(HttpStatus.OK).body(result);

            }else{
                fail.put("message", "Car not found, please provide an existing carId");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fail);
            }
        }else{
            fail.put("message", "There isn't no cars for the input carId. Please provide a valid carId");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fail);
        }
    }

    @Override
    public ResponseEntity<?> updateASpecificCarDetail(String carId, Map<String, Object> dataToUpdate) {
        Map<String, Cars> result = new HashMap<>();
        Map<String, String> fail = new HashMap<>();

        if(validationFields.validateId(carId)){
            Optional<?> carToBeUpdated = carsRepository.findById(Integer.parseInt(carId));
            try {
                if(carToBeUpdated.isPresent()){
                    Cars newCar = (Cars) carToBeUpdated.get();
                    dataToUpdate.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Cars.class, key);
                        if(field != null){
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, newCar, value);
                        }
                    } );
                    result.put("Car updated with success", newCar);
                    return ResponseEntity.status(HttpStatus.OK).body(result);
                }else{
                    throw new EntityNotFoundException("Car not found for the id " + carId );
                }
            }catch (EntityNotFoundException e){
                fail.put("message:","Car not found, please provide a existing id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fail);
            }
        }else{
            fail.put("message:", "Car id invalid, please provide a valid one");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fail);
        }
    }



    private String defineVersion(String version) {
        Integer numVersion = Integer.parseInt(version);
        String versionFinal = "";
        switch (numVersion){
            case 1:
                versionFinal = Version.SEDAN.name();
                break;
            case 2:
                versionFinal = Version.HATCH.name();
                break;
            case 3:
                versionFinal = Version.SPORT.name();
                break;
            case 4:
                versionFinal = Version.SUV.name();
                break;
            case 5:
                versionFinal = Version.PICKUP.name();
                break;
        }

        return versionFinal;
    }

    private Brands defineBrand(String carBrand) {
        Integer num = Integer.parseInt(carBrand);
        Brands brandToReturn = null;
        switch (num){
            case 1:
                brandToReturn = Brands.VOLKSWAGEN;
                break;
            case 2:
                brandToReturn = Brands.MERCEDES;
                break;
            case 3:
                brandToReturn = Brands.BMW;
                break;
            case 4:
                brandToReturn = Brands.FIAT;
                break;
            case 5:
                brandToReturn = Brands.MG;
                break;
            case 6:
                brandToReturn = Brands.OPEL;
                break;
            case 7:
                brandToReturn = Brands.RENAULT;
                break;
        }

        return brandToReturn;
    }
}
