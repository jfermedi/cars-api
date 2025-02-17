package com.api.cars.cars_api.validator;

public class ValidationFields {

    /**
     * Method to validate the input carId
     * @param carId
     * @return boolean
     */
    public boolean validateId(String carId){
        try {
            Integer newCarId = Integer.valueOf(carId);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Method to validate the input carBrand
     * @param carBrand
     * @return boolean
     */
    public boolean validateCarBrand(String carBrand) {
        if (carBrand == null || carBrand.isBlank()) {
            return false;
        }
        boolean isValid;
        try {
            int number = Integer.parseInt(carBrand.trim());
            isValid = number >= 1 && number <= 7;
        } catch (NumberFormatException e) {
            return false;
        }
        return isValid;
    }

    /**
     * Method to validate the input car version
     * @param version
     * @return boolean
     */
    public boolean validateCarVersion(String version) {
       if(version == null || version.isBlank()){
           return false;
       }
       boolean isValid;
       try {
           int number = Integer.parseInt(version.trim());
           isValid = number >=1 && number <=5;
       } catch (NumberFormatException e) {
           return false;
       }
       return isValid;
    }
}
