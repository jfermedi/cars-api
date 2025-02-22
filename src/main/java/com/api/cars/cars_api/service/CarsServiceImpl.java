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
import java.util.*;


@Service
public class CarsServiceImpl implements CarsService{

@Autowired
 CarsRepository carsRepository;
 ValidationFields validationFields = new ValidationFields();
 Map<String, Object> response;

    /**
     * Method implementation to return all Cars from the database
     *
     * @return ResponseEntity<Map<String, Object>> with the cars found
     */
    @Override
    public ResponseEntity<Map<String, Object>> getAllCars() {
        response = new HashMap<String, Object>();
        List<Cars> carsList = getAllCarsList();
        if(carsList.isEmpty()){
            response.put("message", "No cars exist on the database");
        }else{
         response.put("cars", carsList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Method implementation for returning all the Cars from the
     * database
     * @return List<Cars> with all the Cars objects
     */
     public List<Cars> getAllCarsList() {
        return carsRepository.findAll();
    }

    /**
     * Method implementation for returning a specific Car object from the
     * database, based on carId
     *
     * @param carId
     * @return ResponseEntity<Map<String, Object>> with the Car object
     */
    @Override
    public ResponseEntity<Map<String, Object>> getSpecificCar(String carId) {
        response = new HashMap<String, Object>();
       Optional<Cars> carToBeFound = Optional.empty();
        if(validationFields.validateId(carId)){
            carToBeFound = carsRepository.findById(Integer.parseInt(carId));
            if(carToBeFound.isPresent()) {
                response.put("car", carToBeFound);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                response.put("message","Car not found, please provide an existing car id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }else{
            response.put("message", "Car id invalid, please provide a valid one");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Method implementation to return all Cars objects with the same brand
     *
     * @param brand
     * @return ResponseEntity<Map<String, Object>> with all the Cars objects
     */
    @Override
    public ResponseEntity<Map<String, Object>> getCarsByBrand(String brand) {
        Map<String, Object> response = new HashMap<>();
        List<Cars> carsFound ;
        if(validationFields.validateCarBrand(brand)){
            String finalBrand = defineBrand(brand);
            carsFound = getAllCarsByBrand(finalBrand);
            Object o = carsFound.isEmpty() ? response.put("message", "No cars found for the brand " + finalBrand) :
                    response.put("cars", carsFound);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("message" , "Car brand invalid, please provide a car brand from 1-7");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    /**
     * Auxiliary method to return all Cars from the database with the same
     * brand
     * @param brand
     * @return List<Cars> with all the cars
     */
    private List<Cars> getAllCarsByBrand(String brand){
        return carsRepository.findByBrand(brand);
    }

    /**
     * Method implementation to return all the Cars objects with the same
     * version
     *
     * @param version
     * @return ResponseEntity<Map<String, Object>> with all the Cars
     */
    @Override
    public ResponseEntity<Map<String, Object>> getCarsByVersion(String version) {
        Map<String, Object> response = new HashMap<>();
        List<Cars> carsFound ;
        if(validationFields.validateCarVersion(version)){
            String finalVersion = defineVersion(version);
            carsFound = getAllCarsByVersion(finalVersion);
            Object o = carsFound.isEmpty() ? response.put("message", "No cars found for the version " + finalVersion) :
                    response.put("cars", carsFound);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("message" , "Car version invalid, please provide a car version from 1-5");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    /**
     * Auxiliary method to return all the Cars objects from the database
     * with the same version
     * @param version
     * @return List<Cars> with all the Cars objects
     */
    private List<Cars> getAllCarsByVersion(String version){
        return carsRepository.findByVersion(version);
    }

    /**
     * Method implementation for creating a new Cars object and persist it
     * on the database
     * @param carBrand
     * @param version
     * @return ResponseEntity<Map<String, Object>> with the new Cars object created
     */
    @Override
    public ResponseEntity<Map<String, Object>> createNewCar(String carBrand, String version) {
       response = new HashMap<String, Object>();
        Cars carToBeCreated;
        if((validationFields.validateCarBrand(carBrand)) && (validationFields.validateCarVersion(version))) {
          String carBrandFinal = defineBrand(carBrand);
              Float price = definePrice(carBrand);
              String carVersionFinal = defineVersion(version);
              carToBeCreated = new Cars(carBrandFinal,price ,carVersionFinal);
              carsRepository.save(carToBeCreated);
              response.put("car", carToBeCreated);
              return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
            response.put("message", "Invalid car brand or version ! Please provide a car brand from 1-7, and version from 1-5");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Method implementation to delete a Car object from the database,
     * based on the carId
     *
     * @param carId
     * @return ResponseEntity<Map<String, Object>> with the success or fail of the deletion
     */
    @Override
    public ResponseEntity<Map<String, Object>> deleteSpecificCar(String carId) {
        response = new HashMap<>();
        if(validationFields.validateId(carId)){
            Integer carIdToSearch = Integer.parseInt(carId);
            Optional<?> carToBeFound = carsRepository.findById(carIdToSearch);
           if(carToBeFound.isPresent()){
               carsRepository.deleteById(carIdToSearch);
               response.put("message", "Car deleted with success");
              return ResponseEntity.status(HttpStatus.OK).body(response);
           }else{
               response.put("message", "Car not found, please provide a existing carId");
             return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
           }
        }else{
           response.put("message", "Invalid carId, please provide a valid one");
        return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Method implementation to delete all Cars objects from the database
     *
     * @return ResponseEntity<Map<String, Object>> a message with the success or fail of the deletion
     */
    @Override
    public ResponseEntity<Map<String, Object>> deleteAllCars() {
         response = new HashMap<>();
        List<Cars> cars = getAllCarsList();
        if(!cars.isEmpty()){
            cars.stream().map(Cars::getCarId).forEach(carsRepository::deleteById);
            response.put("message", "All cars deleted with success");
           return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("message", "There aren't no cars in the database");
             return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

    }

    /**
     * Method implementation to update a Cars object and persist it on the
     * database, based on the carId
     *
     * @param carId
     * @param carToUpdate
     * @return ResponseEntity<Map<String, Object>> with the Cars object updated
     */
    @Override
    public ResponseEntity<Map<String, Object>> updateSpecificCar(String carId, Cars carToUpdate) {
         response = new HashMap<>();

        if(validationFields.validateId(carId)){
            Optional<?> carToBeFound = carsRepository.findById(Integer.parseInt(carId));
            if(carToBeFound.isPresent()){
                Cars newCar =  (Cars) carToBeFound.get();
                    newCar.setVersion(carToUpdate.getVersion());
                    newCar.setBrand(carToUpdate.getBrand());
                    newCar.setPrice(carToUpdate.getPrice());
                    carsRepository.save(newCar);
                    response.put("car", newCar);
                    return ResponseEntity.status(HttpStatus.OK).body(response);

            }else{
                response.put("message", "Car not found, please provide an existing carId");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }else{
            response.put("message", "There isn't no cars for the input carId. Please provide a valid carId");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Method implementation to update partially a Cars object and persist it
     * on the database, based on the carId
     *
     * @param carId
     * @param dataToUpdate
     * @return ResponseEntity<Map<String, Object>> with the Cars object updated
     */
    @Override
    public ResponseEntity<Map<String, Object>> updateASpecificCarDetail(String carId, Map<String, Object> dataToUpdate) {
         response = new HashMap<>();


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
                    response.put("car", newCar);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }else{
                    throw new EntityNotFoundException("Car not found for the id " + carId );
                }
            }catch (EntityNotFoundException e){
                response.put("message:","Car not found, please provide a existing id");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }else{
            response.put("message:", "Car id invalid, please provide a valid one");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Auxiliary method to define the version from the Enum of versions
     * @param version
     * @return a String version
     */
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

    /**
     * Auxiliary method to define the brand from the Enum of brands
     * @param carBrand
     * @return String brand
     */
    private String defineBrand(String carBrand) {
        Integer num = Integer.parseInt(carBrand);
        String brandToReturn = "";
        switch (num){
            case 1:
                brandToReturn = Brands.VOLKSWAGEN.name();
                break;
            case 2:
                brandToReturn = Brands.MERCEDES.name();
                break;
            case 3:
                brandToReturn = Brands.BMW.name();
                break;
            case 4:
                brandToReturn = Brands.FIAT.name();
                break;
            case 5:
                brandToReturn = Brands.MG.name();
                break;
            case 6:
                brandToReturn = Brands.OPEL.name();
                break;
            case 7:
                brandToReturn = Brands.RENAULT.name();
                break;
        }

        return brandToReturn;
    }

    /**
     * Auxiliary method to define the price from the Enum of brands
     * @param carBrand
     * @return Float price
     */
    private Float definePrice(String carBrand) {
        Integer num = Integer.parseInt(carBrand);
        Float priceToReturn = 0.0f;
        switch (num){
            case 1:
                priceToReturn = Brands.VOLKSWAGEN.price;
                break;
            case 2:
                priceToReturn = Brands.MERCEDES.price;
                break;
            case 3:
                priceToReturn = Brands.BMW.price;
                break;
            case 4:
                priceToReturn = Brands.FIAT.price;
                break;
            case 5:
                priceToReturn = Brands.MG.price;
                break;
            case 6:
                priceToReturn = Brands.OPEL.price;
                break;
            case 7:
                priceToReturn = Brands.RENAULT.price;
                break;
        }

        return priceToReturn;
    }

}
