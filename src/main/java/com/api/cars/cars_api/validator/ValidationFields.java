package com.api.cars.cars_api.validator;

public class ValidationFields {

    public boolean validateId(String carId){
        try {
            Integer newCarId = Integer.valueOf(carId);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
