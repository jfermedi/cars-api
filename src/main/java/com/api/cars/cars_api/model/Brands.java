package com.api.cars.cars_api.model;

public enum Brands {
    VOLKSWAGEN("Taigo",20.000f), MERCEDEZ("C-Series",50.000f), BMW("X1", 45.000f), FIAT("Tipo",25.000f), MG("Avenger",32.000f), OPEL("Astra",18.000f), RENAULT("Megane",22.000f);

    public final String name;
    public final Float price;

    private Brands(String name,Float price){
        this.name = name;
        this.price = price;
    }
}
