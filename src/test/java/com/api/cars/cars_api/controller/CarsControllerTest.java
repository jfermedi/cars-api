package com.api.cars.cars_api.controller;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.repository.CarsRepository;
import com.api.cars.cars_api.service.CarsServiceImpl;
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


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
class CarsControllerTest {



    @Mock
    CarsRepository carsRepository;
    @Mock
    CarsServiceImpl carsService;
    @InjectMocks
    CarsController carsController;



    AutoCloseable autoCloseable;
    Cars car;
    Cars car2;
    Cars car3;
    Cars car4;
    Map<String, Object> dataToReturn;
    ResponseEntity<Map<String, Object>> dataReturned;
    ResponseEntity<Map<String, Object>> response;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        car = new Cars(1,"OPEL", 20.0f, "SPORT");
        car2 = new Cars(2,"MG", 30.0f, "SUV");
        car3 = new Cars(3,"BMW", 50.0f, "SPORT");
        car4 = new Cars(4,"BMW", 50.0f, "SEDAN");
        dataToReturn = new HashMap<String, Object>();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCars() throws Exception {
        List<Cars> carsList = List.of(car,car2,car3);
        when(carsRepository.findAll()).thenReturn(carsList);
        when(carsService.getAllCarsList()).thenReturn(carsList);
        dataToReturn.put("cars", carsList);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);


        when(carsService.getAllCars()).thenReturn(response);

        dataReturned = carsController.getAllCars();
        assertNotNull(dataReturned);
        assertEquals(HttpStatus.OK, dataReturned.getStatusCode());
        assertEquals(response.getBody(), dataReturned.getBody());

        List<Cars> carsFound = (List<Cars>) dataReturned.getBody().get("cars");
        assertNotNull(carsFound);
        assertEquals(carsFound.get(0).getBrand(), carsList.get(0).getBrand());
        assertEquals(carsFound.get(0).getVersion(), carsList.get(0).getVersion());
        assertEquals(carsFound.get(0).getPrice(), carsList.get(0).getPrice());
        assertEquals(carsFound.get(1).getBrand(), carsList.get(1).getBrand());
        assertEquals(carsFound.get(1).getVersion(), carsList.get(1).getVersion());
        assertEquals(carsFound.get(1).getPrice(), carsList.get(1).getPrice());
        assertEquals(carsFound.get(2).getBrand(), carsList.get(2).getBrand());
        assertEquals(carsFound.get(2).getVersion(), carsList.get(2).getVersion());
        assertEquals(carsFound.get(2).getPrice(), carsList.get(2).getPrice());

    }

    @Test
    void getASpecificCarInfo() {

        Optional<Cars> carToBeFound = Optional.of(car);
        dataToReturn.put("car", carToBeFound);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findById(1)).thenReturn(carToBeFound);
        when(carsService.getSpecificCar("1")).thenReturn(response);

         dataReturned = carsController.getASpecificCarInfo("1");
         assertNotNull(dataReturned);
         Optional<Cars> optionalCarFound = (Optional<Cars>) dataReturned.getBody().get("car");
         assertNotNull(optionalCarFound);

         Cars finalCarFound = optionalCarFound.get();
         assertNotNull(finalCarFound);
         assertEquals(finalCarFound.getCarId(), car.getCarId());
         assertEquals(finalCarFound.getBrand(), car.getBrand());
         assertEquals(finalCarFound.getVersion(), car.getVersion());
         assertEquals(finalCarFound.getPrice(), car.getPrice());
    }

    @Test
    void getCarsByBrand() {
        List<Cars> carsList = List.of(car3,car4);
        dataToReturn.put("cars", carsList);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findByBrand("BMW")).thenReturn(carsList);
        when(carsService.getCarsByBrand("BMW")).thenReturn(response);

        dataReturned = carsController.getCarsByBrand("BMW");
        List<Cars> carsFound = (List<Cars>) dataReturned.getBody().get("cars");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getBody().get("cars"), dataToReturn.get("cars"));
        assertNotNull(carsFound);
        assertEquals(carsFound.get(0).getCarId(), carsList.get(0).getCarId());
        assertEquals(carsFound.get(0).getBrand(), carsList.get(0).getBrand());
        assertEquals(carsFound.get(0).getVersion(), carsList.get(0).getVersion());
        assertEquals(carsFound.get(0).getPrice(), carsList.get(0).getPrice());
        assertEquals(carsFound.get(1).getCarId(), carsList.get(1).getCarId());
        assertEquals(carsFound.get(1).getBrand(), carsList.get(1).getBrand());
        assertEquals(carsFound.get(1).getVersion(), carsList.get(1).getVersion());
        assertEquals(carsFound.get(1).getPrice(), carsList.get(1).getPrice());
     }

    @Test
    void getCarsByVersion() {
        List<Cars> carsList = List.of(car, car3);
        dataToReturn.put("cars", carsList);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findByVersion("SPORT")).thenReturn(carsList);
        when(carsService.getCarsByVersion("SPORT")).thenReturn(response);

        dataReturned = carsController.getCarsByVersion("SPORT");
        List<Cars> carsFound = (List<Cars>) dataReturned.getBody().get("cars");

        assertNotNull(dataReturned);
        assertEquals(dataReturned.getBody().get("cars"), dataToReturn.get("cars"));
        assertNotNull(carsFound);
        assertEquals(2, carsFound.size());
        assertEquals(carsFound.get(0).getCarId(), carsList.get(0).getCarId());
        assertEquals(carsFound.get(0).getVersion(), carsList.get(0).getVersion());
        assertEquals(carsFound.get(0).getBrand(), carsList.get(0).getBrand());
        assertEquals(carsFound.get(0).getPrice(), carsList.get(0).getPrice());
        assertEquals(carsFound.get(1).getCarId(), carsList.get(1).getCarId());
        assertEquals(carsFound.get(1).getVersion(), carsList.get(1).getVersion());
        assertEquals(carsFound.get(1).getBrand(), carsList.get(1).getBrand());
        assertEquals(carsFound.get(1).getPrice(), carsList.get(1).getPrice());
    }

    @Test
    void createANewCar() {
        Cars newCar = new Cars(1, "OPEL", 25.0f, "HATCH");
        dataToReturn.put("car", newCar);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.save(newCar)).thenReturn(newCar);
        when(carsService.createNewCar("OPEL","HATCH")).thenReturn(response);

        dataReturned = carsController.createANewCar("OPEL","HATCH");
        Cars carCreated = (Cars) dataReturned.getBody().get("car");

        assertNotNull(dataToReturn);
        assertEquals(dataToReturn.get("car"), dataReturned.getBody().get("car"));
        assertNotNull(carCreated);
        assertEquals(carCreated.getCarId(), newCar.getCarId());
        assertEquals(carCreated.getBrand(), newCar.getBrand());
        assertEquals(carCreated.getVersion(), newCar.getVersion());
        assertEquals(carCreated.getPrice(), newCar.getPrice());
    }

    @Test
    void deleteACar() {
        Optional<Cars> carToBeFound = Optional.of(car);
        dataToReturn.put("message", "Car deleted with success");
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findById(1)).thenReturn(carToBeFound);
        when(carsService.deleteSpecificCar("1")).thenReturn(response);

        dataReturned = carsController.deleteACar("1");
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getBody().get("message"), dataToReturn.get("message"));
    }

    @Test
    void deleteAllCars() {
        List<Cars> carsList = List.of(car, car2, car3, car4);
        dataToReturn.put("message", "All cars deleted with success");
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.findAll()).thenReturn(carsList);
        when(carsService.deleteAllCars()).thenReturn(response);

        dataReturned = carsController.deleteAllCars();
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getBody().get("message"), dataToReturn.get("message"));
    }

    @Test
    void updateACar() {
        Cars carToUpdate = new Cars("MERCEDES", 65.0f, "SPORT");
        dataToReturn.put("car", carToUpdate);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);


        when(carsRepository.save(car)).thenReturn(car);
        when(carsService.updateSpecificCar("1", carToUpdate)).thenReturn(response);

        dataReturned = carsController.updateACar("1", carToUpdate);
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getBody().get("car"), dataToReturn.get("car"));
    }

    @Test
    void updateACarPartially() {
        Map<String, Object> dataToUpdate = new HashMap<String, Object>();
        dataToUpdate.put("brand", "FIAT");
        dataToReturn.put("car", car);
        response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);

        when(carsRepository.save(car)).thenReturn(car);
        when(carsService.updateASpecificCarDetail("1",dataToUpdate)).thenReturn(response);

        dataReturned = carsController.updateACarPartially("1", dataToUpdate);
        assertNotNull(dataReturned);
        assertEquals(dataReturned.getBody().get("car"), dataToReturn.get("car"));
    }
}