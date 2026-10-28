
package com.example.sh_stp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.sh_stp.BR;

public class MotorcycleDetailsActivityMotorcycleModel
        extends BaseObservable {

    private String brand = "";
    private String model = "";
    private String year = "";
    private String engineVolume = "";

    @Bindable
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
        notifyPropertyChanged(BR.brand);
    }

    @Bindable
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
        notifyPropertyChanged(BR.model);
    }

    @Bindable
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    @Bindable
    public String getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(String engineVolume) {
        this.engineVolume = engineVolume;
        notifyPropertyChanged(BR.engineVolume);
    }

    public void fromMotorcycle(Motorcycle motorcycle) {
        setBrand(motorcycle.getBrand());
        setModel(motorcycle.getModel());
        setYear(String.valueOf(motorcycle.getYear()));
        setEngineVolume(
                String.valueOf(motorcycle.getEngineVolume())
        );
    }
}
