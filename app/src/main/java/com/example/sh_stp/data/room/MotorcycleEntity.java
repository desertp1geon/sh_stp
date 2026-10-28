
package com.example.sh_stp.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "motorcycles",
        indices = {
                @Index(value = {"brand", "model"})
        }
)
public class MotorcycleEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "engine_volume")
    private double engineVolume;

    public MotorcycleEntity() {
    }

    public MotorcycleEntity(
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

    @Ignore
    public MotorcycleEntity(
            String brand,
            String model,
            int year,
            double engineVolume
    ) {
        this(0, brand, model, year, engineVolume);
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
