package edu.csh.androiddrink;


import com.google.gson.annotations.SerializedName;

public class Machine {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private MachineData machineData;

    public Machine(MachineData machineData, Boolean status, String message){
        this.machineData = machineData;
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus(){
        return status;
    }
    public void setStatus(Boolean status){
        this.status = status;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public MachineData getMachineData(){
        return machineData;
    }
    public void setMachineData(MachineData machineData){
        this.machineData = machineData;
    }

}
