package com.timsedam.buildingmanagement.util.mappers;


import com.timsedam.buildingmanagement.dto.BuildingDTO;
import com.timsedam.buildingmanagement.dto.CreateBuildingDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BuildingMapper {

    private ModelMapper modelMapper=new ModelMapper();

    public Building toModel(CreateBuildingDTO cb){
        return modelMapper.map(cb,Building.class);
    }

    public BuildingDTO toDto(Building b){
        BuildingDTO dto= new BuildingDTO(
                b.getId(),
                b.getCity(),b.getAddress(),b.getCountry(),b.getApartmentCount(),
                b.getDescription(),b.getPicture(),null,null);

        if(b.getManager()!=null){
            b.getManager().setComments(null);
            b.getManager().setPassword(null);
        }
        if(b.getResidences()!=null){
            dto.setResidences(new ArrayList<Residence>());
            for(Residence r:b.getResidences()){
                r.setBuilding(null);
                dto.getResidences().add(r);
            }
        }

        return dto;
    }

}
