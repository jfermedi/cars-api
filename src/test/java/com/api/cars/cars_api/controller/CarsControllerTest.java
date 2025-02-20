package com.api.cars.cars_api.controller;

import com.api.cars.cars_api.model.Cars;
import com.api.cars.cars_api.repository.CarsRepository;
import com.api.cars.cars_api.service.CarsService;
import com.api.cars.cars_api.service.CarsServiceImpl;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class CarsControllerTest {



    @Mock
    CarsRepository carsRepository;
    @Mock
    CarsServiceImpl carsService;
    @InjectMocks
    CarsController carsController;
    @InjectMocks
    CarsServiceImpl carsServiceImpl;


    AutoCloseable autoCloseable;
    Cars car;
    Cars car2;
    Cars car3;
    Map<String, Object> dataToReturn;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        car = new Cars("OPEL", 20.0f, "SPORT");
        car2 = new Cars("MG", 30.0f, "SUV");
        car3 = new Cars("BMW", 50.0f, "SPORT");
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
        when(carsServiceImpl.getAllCarsList()).thenReturn(carsList);
        dataToReturn.put("cars", carsList);
        ResponseEntity<Map<String, Object>> response = ResponseEntity.status(HttpStatus.OK).body(dataToReturn);


        when(carsService.getAllCars()).thenReturn(response);

        ResponseEntity<Map<String, Object>> dataReturned = carsController.getAllCars();
        assertNotNull(dataReturned);
        assertEquals(HttpStatus.OK, dataReturned.getStatusCode());
        assertEquals(response.getBody(), dataReturned.getBody());

        List<Cars> carsFound = (List<Cars>) dataReturned.getBody().get("cars");
        assertNotNull(carsFound);

    }

    @Test
    void getASpecificCarInfo() {
    }

    @Test
    void getCarsByBrand() {
    }

    @Test
    void getCarsByVersion() {
    }

    @Test
    void createANewCar() {
    }

    @Test
    void deleteACar() {
    }

    @Test
    void deleteAllCars() {
    }

    @Test
    void updateACar() {
    }

    @Test
    void updateACarPartially() {
    }
}