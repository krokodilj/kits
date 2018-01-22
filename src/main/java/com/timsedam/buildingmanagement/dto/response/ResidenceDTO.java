package com.timsedam.buildingmanagement.dto.response;


import java.util.List;

public class ResidenceDTO {

    private long id;
    private Long building;
    private int floorNumber;
    private int apartmentNumber;
    private List<Long> residents;
    private Long appartmentOwner;

   public ResidenceDTO(){}

    public ResidenceDTO(long id, Long building, int floorNumber, int apartmentNumber, List<Long> residents, Long appartmentOwner) {
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

    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
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

    public List<Long> getResidents() {
        return residents;
    }

    public void setResidents(List<Long> residents) {
        this.residents = residents;
    }

    public Long getAppartmentOwner() {
        return appartmentOwner;
    }

    public void setAppartmentOwner(Long appartmentOwner) {
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
