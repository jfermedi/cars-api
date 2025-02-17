package com.api.cars.cars_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer carId;
    String brand;
    Float price;
    String version;

    /**
     * Constructor method with no args
     */
    public Cars(){}

    /**
     * Constructor method
     * @param brand
     * @param price
     * @param version
     */
    public Cars(String brand, Float price, String version) {
        this.brand = brand;
        this.price = price;
        this.version = version;
    }

    public Integer getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
