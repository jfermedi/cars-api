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

    public boolean validateCarBrand(String carBrand) {
       boolean result;
        try {
            Integer number = Integer.parseInt(carBrand);
                result = true;
            }
        catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    public boolean validateCarVersion(String version) {
        boolean result;
        try {
            Integer number = Integer.parseInt(version);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }

        return result;
    }
}
