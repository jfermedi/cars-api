package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.repository.CarsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CarsServiceImplTest {

    @Mock
    CarsRepository carsRepository;
    @InjectMocks
    CarsServiceImpl carsService;

    AutoCloseable autoCloseable;
    Cars car;
    Cars car2;
    Cars car3;
    Cars car4;
    Map<String, Object> dataToReturn;
    Map<String, Object> dataToReturn2;
    ResponseEntity<Map<String, Object>> dataReturned;
    ResponseEntity<Map<String, Object>> dataReturned2;
    ResponseEntity<Map<String, Object>> response;
    ResponseEntity<Map<String, Object>> response2;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        car = new Cars(1,"OPEL", 20.0f, "SPORT");
        car2 = new Cars(2,"MG", 30.0f, "SUV");
        car3 = new Cars(3,"BMW", 50.0f, "SPORT");
        car4 = new Cars(4,"BMW", 50.0f, "SEDAN");
        dataToReturn = new HashMap<String, Object>();
        dataToReturn2 = new HashMap<String, Object>();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCarsList(){
        List<Cars> carsList = List.of(car, car2, car3, car4);

        when(carsRepository.findAll()).thenReturn(carsList);

        List<Cars>carListToBeReturned = carsService.getAllCarsList();
        assertNotNull(carListToBeReturned);
        assertEquals(carListToBeReturned.size(), carsList.size());
    }
    @Test
    void getAllCars() {
        List<Cars> carsList = List.of(car, car2, car3);
        dataToReturn.put("cars", carsList);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findAll()).thenReturn(carsList);
        dataReturned = carsService.getAllCars();
        List<Cars> carsListFound = (List<Cars>) dataReturned.getBody().get("cars");
        assertNotNull(dataReturned);
        assertNotNull(carsListFound);
        assertEquals(carsListFound.size(), carsList.size());
    }

    @Test
    void getAllCarsEmptyList(){
        List<Cars> carsList = new ArrayList<>();
        dataToReturn.put("message", "No cars exist on the database");
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findAll()).thenReturn(carsList);
        dataReturned = carsService.getAllCars();
        assertEquals(dataReturned.getBody().get("message"), dataToReturn.get("message"));
    }

    @Test
    void getSpecificCar() {
        Optional<Cars> carToBeFound = Optional.of(car);
        dataToReturn.put("car", car);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findById(1)).thenReturn(carToBeFound);

        dataReturned = carsService.getSpecificCar("1");
        Optional<Cars> optionalCarFound = (Optional<Cars>) dataReturned.getBody().get("car");
        Cars carFound = optionalCarFound.get();
        assertNotNull(dataReturned);
        assertEquals(car, carFound);
    }

    @Test
    void getSpecificCarNotExistingCarId(){
        dataToReturn.put("message", "Car not found, please provide an existing car id");
        dataToReturn2.put("message", "Car id invalid, please provide a valid one");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);
        response2 = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn2);
        dataReturned = carsService.getSpecificCar("9");
        dataReturned2 = carsService.getSpecificCar("A");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
        assertNotNull(dataReturned2);
        assertEquals(dataReturned2.getStatusCode(), response2.getStatusCode());
        assertEquals(dataReturned2.getBody().get("message"), response2.getBody().get("message"));
    }

    @Test
    void getCarsByBrand() {
        List<Cars> carsList = List.of(car3, car4);
        dataToReturn.put("cars", carsList);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findByBrand("BMW")).thenReturn(carsList);
        dataReturned = carsService.getCarsByBrand("3");
        List<Cars> carsListFound = (List<Cars>) dataReturned.getBody().get("cars");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(carsListFound.size(), carsList.size());
        assertEquals(carsListFound.get(0).getCarId(), carsList.get(0).getCarId());
        assertEquals(carsListFound.get(0).getBrand(), carsList.get(0).getBrand());
        assertEquals(carsListFound.get(0).getVersion(), carsList.get(0).getVersion());
        assertEquals(carsListFound.get(0).getPrice(), carsList.get(0).getPrice());
        assertEquals(carsListFound.get(1).getCarId(), carsList.get(1).getCarId());
        assertEquals(carsListFound.get(1).getBrand(), carsList.get(1).getBrand());
        assertEquals(carsListFound.get(1).getVersion(), carsList.get(1).getVersion());
        assertEquals(carsListFound.get(1).getPrice(), carsList.get(1).getPrice());
    }
    @Test
    void getCarsByBrandInvalidBrand(){
        dataToReturn.put("message", "Car brand invalid, please provide a car brand from 1-7");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);

        dataReturned = carsService.getCarsByBrand("A");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }
    @Test
    void getCarsByBrandEmptyCarsListFoundByBrand(){
        List<Cars> carsList = new ArrayList<>();
        String finalBrand = "OPEL";
        dataToReturn.put("message", "No cars found for the brand " + finalBrand);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findByBrand("A")).thenReturn(carsList);

        dataReturned = carsService.getCarsByBrand("6");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }
    @Test
    void getCarsByVersion() {
        List<Cars> carsList = List.of(car, car3);
        dataToReturn.put("cars", carsList);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findByVersion("SPORT")).thenReturn(carsList);

        dataReturned = carsService.getCarsByVersion("3");
        List<Cars> carsListFound = (List<Cars>) dataReturned.getBody().get("cars");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("cars"), response.getBody().get("cars"));
        assertNotNull(carsListFound);
        assertEquals(carsListFound.size(), carsList.size());
        assertEquals(carsListFound.get(0).getCarId(), carsList.get(0).getCarId());
        assertEquals(carsListFound.get(0).getVersion(), carsList.get(0).getVersion());
        assertEquals(carsListFound.get(0).getBrand(), carsList.get(0).getBrand());
        assertEquals(carsListFound.get(0).getPrice(), carsList.get(0).getPrice());
        assertEquals(carsListFound.get(1).getCarId(), carsList.get(1).getCarId());
        assertEquals(carsListFound.get(1).getVersion(), carsList.get(1).getVersion());
        assertEquals(carsListFound.get(1).getBrand(), carsList.get(1).getBrand());
        assertEquals(carsListFound.get(1).getPrice(), carsList.get(1).getPrice());
    }

    @Test
    void getCarsByVersionInvalidCarVersion(){
        dataToReturn.put("message", "Car version invalid, please provide a car version from 1-5");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);

        dataReturned = carsService.getCarsByVersion(null);
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }

    @Test
    void getCarsByVersionEmptyListCarVersion(){
        List<Cars> carsList = new ArrayList<>();
        String finalVersion = "SPORT";
        dataToReturn.put("message", "No cars found for the version " + finalVersion);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findByBrand("A")).thenReturn(carsList);

        dataReturned = carsService.getCarsByVersion("3");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }
    @Test
    void createNewCar() {
        Cars carToBeCreated = new Cars("FIAT", 25.000f, "HATCH");
        dataToReturn.put("car", carToBeCreated);
        response = ResponseEntity.status(HttpStatus.CREATED).body(dataToReturn);

        when(carsRepository.save(carToBeCreated)).thenReturn(carToBeCreated);

        dataReturned = carsService.createNewCar("4","2");
        Cars carCreated = (Cars) dataReturned.getBody().get("car");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
       assertNotNull(carCreated);
       assertEquals(carCreated.getCarId(), carToBeCreated.getCarId());
       assertEquals(carCreated.getBrand(), carToBeCreated.getBrand());
       assertEquals(carCreated.getPrice(), carToBeCreated.getPrice());
       assertEquals(carCreated.getVersion(), carToBeCreated.getVersion());
    }

    @Test
    void createNewCarInvalidCarBrandAndValidCarVersion(){
        dataToReturn.put("message", "Invalid car brand or version ! Please provide a car brand from 1-7, and version from 1-5");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);

        dataReturned = carsService.createNewCar("A","5");

        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }

    @Test
    void createNewCarValidCarBrandAndInvalidCarVersion(){
        dataToReturn.put("message", "Invalid car brand or version ! Please provide a car brand from 1-7, and version from 1-5");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);

        dataReturned = carsService.createNewCar("1","B");

        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }
    @Test
    void deleteSpecificCar() {
        Optional<Cars> carsOptional = Optional.of(car);
        dataToReturn.put("message", "Car deleted with success");
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findById(1)).thenReturn(carsOptional);

        dataReturned = carsService.deleteSpecificCar("1");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }

    @Test
    void deleteSpecificCarCarToBeDeletedNotFound(){
        Optional<Cars> optionalCars = Optional.empty();
        dataToReturn.put("message", "Car not found, please provide a existing carId");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);

        when(carsRepository.findById(0)).thenReturn(optionalCars);

        dataReturned = carsService.deleteSpecificCar("0");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }

    @Test
    void deleteSpecificCarInvalidCarId(){
        dataToReturn.put("message", "Invalid carId, please provide a valid one");
        response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dataToReturn);

        dataReturned = carsService.deleteSpecificCar("A");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getStatusCode(), response.getStatusCode());
        assertEquals(dataReturned.getBody().get("message"), response.getBody().get("message"));
    }

    @Test
    void deleteAllCars() {
    }

    @Test
    void updateSpecificCar() {
    }

    @Test
    void updateASpecificCarDetail() {
    }
}