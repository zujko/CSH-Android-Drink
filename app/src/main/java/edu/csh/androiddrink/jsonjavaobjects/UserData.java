package edu.csh.androiddrink.jsonjavaobjects;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("uid")
    private String uid;
    @SerializedName("credits")
    private String credits;
    @SerializedName("admin")
    private String admin;

    public UserData(String uid,String credits,String admin){
        this.admin = admin;
        this.credits = credits;
        this.uid = uid;
    }

    public String getUid(){
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public String getCredits(){
        return credits;
    }
    public void setCredits(String credits){
        this.credits = credits;
    }

    public String getAdmin(){
        return admin;
    }
    public void setAdmin(String admin){
        this.admin = admin;
    }

}
