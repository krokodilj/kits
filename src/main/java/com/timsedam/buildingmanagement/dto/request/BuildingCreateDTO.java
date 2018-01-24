package com.timsedam.buildingmanagement.dto.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BuildingCreateDTO {

    @NotNull(message = "'city' not provided")
    private String city;
    
    @NotNull(message = "'address' not provided")
    private String address;
    
    @NotNull(message = "'country' not provided")
    private String country;
    
    @Min(value = 0, message = "'apartmentCount' cannot be a negative value")
    private int apartmentCount;
    
    @NotNull(message = "'description' not provided")
    private String description;

    public BuildingCreateDTO(){}

    public BuildingCreateDTO(String city, String address, String country, int apartmentCount, String description){
        this.city = city;
        this.address = address;
        this.country = country;
        this.apartmentCount = apartmentCount;
        this.description = description;
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
