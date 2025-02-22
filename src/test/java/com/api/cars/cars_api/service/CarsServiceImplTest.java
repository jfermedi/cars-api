package com.api.cars.cars_api.service;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.repository.CarsRepository;
import org.apache.coyote.Response;
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
    }

    @Test
    void getCarsByVersion() {
    }

    @Test
    void createNewCar() {
    }

    @Test
    void deleteSpecificCar() {
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