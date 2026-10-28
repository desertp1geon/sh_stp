
package com.example.sh_stp.data;

import java.io.Serializable;

public class Motorcycle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String brand;
    private String model;
    private int year;
    private double engineVolume;

    public Motorcycle() {
    }

    public Motorcycle(
            int id,
            String brand,
            String model,
            int year,
            double engineVolume
    ) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engineVolume = engineVolume;
    }

    public Motorcycle(
            String brand,
            String model,
            int year,
            double engineVolume
    ) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engineVolume = engineVolume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }
}
