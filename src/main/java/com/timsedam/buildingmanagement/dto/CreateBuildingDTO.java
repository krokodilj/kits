package com.timsedam.buildingmanagement.dto;


import javax.validation.constraints.NotNull;

/**
 * Created by sirko on 12/7/17.
 */
public class CreateBuildingDTO {

    @NotNull
    private String city;
    @NotNull
    private String address;
    @NotNull
    private String country;
    @NotNull
    private int apartmentCount;

    @NotNull
    private String description;

    public CreateBuildingDTO(){}

    public CreateBuildingDTO(String city, String address, String country, int apartmentCount, String description){
        this.city=city;
        this.address=address;
        this.country=country;
        this.apartmentCount=apartmentCount;
        this.description=description;
    }


    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public int getApartmentCount() {
        return apartmentCount;
    }

    public String getDescription() {
        return description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setApartmentCount(int apartmentCount) {
        this.apartmentCount = apartmentCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CreateBuildingDTO{" +
                "city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", apartmentCount=" + apartmentCount +
                ", description='" + description + '\'' +
                '}';
    }

}
