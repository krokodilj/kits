package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

public class UserLoginDTO {

    @NotNull(message = "{user.username.empty}")
    private String username;
    @NotNull(message = "{user.password.empty}")
    private String password;

    public UserLoginDTO(){}

    public UserLoginDTO(String username,String password){
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }


}
