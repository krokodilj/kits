package com.timsedam.buildingmanagement.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by sirko on 12/7/17.
 */
public class UserLoginDTO {

    @NotNull
    private String username;
    @NotNull
    private String password;

    public UserLoginDTO(){}

    public UserLoginDTO(String username,String password){
        this.username=username;
        this.password=password;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return this.password;
    }


}
