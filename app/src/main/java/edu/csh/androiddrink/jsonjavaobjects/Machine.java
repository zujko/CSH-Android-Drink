package edu.csh.androiddrink.jsonjavaobjects;


import com.google.gson.annotations.SerializedName;

public class Machine {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("data")
    private MachineData machineData;

    public Machine(MachineData machineData, Boolean status){
        this.machineData = machineData;
        this.status = status;
    }

    public Boolean getStatus(){
        return status;
    }
    public void setStatus(Boolean status){
        this.status = status;
    }

    public MachineData getMachineData(){
        return machineData;
    }
    public void setMachineData(MachineData machineData){
        this.machineData = machineData;
    }

}
