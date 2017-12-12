package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidenceService {

    @Autowired
    private ResidenceRepository residenceRepository;

    public Residence createResidence(Residence residence){
        try {
            return residenceRepository.save(residence);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Residence findOneById(long id){
        try{
            return residenceRepository.findOne(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}