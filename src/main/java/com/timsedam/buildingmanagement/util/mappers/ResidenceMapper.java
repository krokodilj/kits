package com.timsedam.buildingmanagement.util.mappers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.CreateResidenceDTO;
import com.timsedam.buildingmanagement.dto.ResidenceDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.Resident;

@Component
public class ResidenceMapper {

    public Residence toModel(CreateResidenceDTO r,Building b){
        Residence residence=new Residence(
                null,r.getFloorNumber(),
                r.getApartmentNumber(),null);

        b.setResidences(null);
        if(b.getManager()!=null) {
            b.getManager().setPassword(null);
            b.getManager().setComments(null);
        }
        residence.setBuilding(b);
        return residence;
    }

    public ResidenceDTO toDto(Residence r){
        ResidenceDTO residenceDTO= new ResidenceDTO(
                r.getId(),null,r.getFloorNumber(),r.getApartmentNumber(),
                null,null
        );

        //building
        r.getBuilding().setResidences(null);
        r.getBuilding().setManager(null);
        residenceDTO.setBuilding(r.getBuilding());
        
        //residents
        if(r.getResidents()!=null){
            residenceDTO.setResidents(new ArrayList<Resident>());
            for(Resident re:r.getResidents()){
                re.setOwnedApartments(null);
                re.setResidences(null);
                re.setComments(null);
                re.setPassword(null);
                residenceDTO.getResidents().add(re);
            }
        }
        residenceDTO.setResidents(r.getResidents());
        
        //apartment owner
        if(r.getApartmentOwner()!=null){
            r.getApartmentOwner().setPassword(null);
            r.getApartmentOwner().setComments(null);
            r.getApartmentOwner().setOwnedApartments(null);
            r.getApartmentOwner().setResidences(null);
        }


        return residenceDTO;
    }

}