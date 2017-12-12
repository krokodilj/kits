package com.timsedam.buildingmanagement.dto;


import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Resident;

import java.util.List;

public class ResidenceDTO {

    private long id;
    private Building building;
    private int floorNumber;
    private int apartmentNumber;
    private List<Resident> residents;
    private Resident appartmentOwner;

   public ResidenceDTO(){}

    public ResidenceDTO(long id, Building building, int floorNumber, int apartmentNumber, List<Resident> residents, Resident appartmentOwner) {
        this.id = id;
        this.building = building;
        this.floorNumber = floorNumber;
        this.apartmentNumber = apartmentNumber;
        this.residents = residents;
        this.appartmentOwner = appartmentOwner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
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

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public Resident getAppartmentOwner() {
        return appartmentOwner;
    }

    public void setAppartmentOwner(Resident appartmentOwner) {
        this.appartmentOwner = appartmentOwner;
    }

    @Override
    public String toString() {
        return "ResidenceDTO{" +
                "id=" + id +
                ", building=" + building +
                ", floorNumber=" + floorNumber +
                ", apartmentNumber=" + apartmentNumber +
                ", residents=" + residents +
                ", appartmentOwner=" + appartmentOwner +
                '}';
    }
}
