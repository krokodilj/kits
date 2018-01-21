package com.timsedam.buildingmanagement.dto;


import javax.validation.constraints.NotNull;

public class CreateResidenceDTO {

    @NotNull
    private long building;
    @NotNull
    private int floorNumber;
    @NotNull
    private int apartmentNumber;

    public CreateResidenceDTO(){}

    public CreateResidenceDTO(long building, int floorNumber, int apartmentNumber) {
        this.building = building;
        this.floorNumber = floorNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public long getBuilding() {
        return building;
    }

    public void setBuilding(long building) {
        this.building = building;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        return "CreateResidenceDTO{" +
                ", building=" + building +
                ", floorNumber=" + floorNumber +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
