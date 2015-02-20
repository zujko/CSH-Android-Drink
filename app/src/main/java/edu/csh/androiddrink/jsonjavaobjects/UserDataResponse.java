package edu.csh.androiddrink.jsonjavaobjects;

import com.google.gson.annotations.SerializedName;

public class UserDataResponse {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private UserData userData;

    public UserDataResponse(Boolean status, UserData userdata){
        this.status = status;
        this.userData = userdata;
    }

    public Boolean getStatus(){
        return status;
    }
    public void setStatus(Boolean status){
        this.status = status;
    }

    public UserData getUserData(){
        return userData;
    }
    public void setUserData(UserData userData){
        this.userData = userData;
    }
}
