package com.api.cars.cars_api.validator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationFields {

    private static final Set<String> VALID_KEYS = Set.of("brand", "version", "price");
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

    public List<String> validateUpdateData(Map<String, Object> dataToUpdate) {
        return dataToUpdate.entrySet().stream()
                .flatMap(entry -> validateEntry(entry.getKey(), entry.getValue()).stream())
                .collect(Collectors.toList());
    }

    private List<String> validateEntry(String key, Object value) {
        if (!VALID_KEYS.contains(key)) {
            return List.of("Invalid key: " + key);
        }

        if ("brand".equals(key) && (value instanceof String) && !validateCarBrand((String) value)) {
            return List.of("Invalid brand value: " + value);
        }

        if ("version".equals(key) && (value instanceof String) && !validateCarVersion((String) value)) {
            return List.of("Invalid version value: " + value);
        }

        if ("price".equals(key) && (value instanceof Number) && ((Number) value).floatValue() <= 0) {
            return List.of("Price must be a positive number: " + value);
        }

        return List.of(); // No errors
    }

}
