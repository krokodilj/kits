package com.timsedam.buildingmanagement.dto.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateBuildingDTO {

    @NotNull(message = "{building.city.empty}")
    private String city;
    @NotNull(message = "{building.address.empty}")
    private String address;
    @NotNull(message = "{building.country.empty}")
    private String country;
    @Min(value = 0, message = "{building.apartmentCount.negative}")
    private int apartmentCount;
    @NotNull(message = "{description.empty}")
    private String description;

    public CreateBuildingDTO(){}

    public CreateBuildingDTO(String city, String address, String country, int apartmentCount, String description){
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
