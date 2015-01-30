package edu.csh.androiddrink;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MachineData {

    @SerializedName("1")
    private ArrayList<ItemInfo> littleItemInfo;
    @SerializedName("2")
    private ArrayList<ItemInfo> bigItemInfo;
    @SerializedName("3")
    private ArrayList<ItemInfo> snackItemInfo;

    public MachineData(ArrayList<ItemInfo> littleItemInfo, ArrayList<ItemInfo> bigItemInfo,
                   ArrayList<ItemInfo> snackItemInfo){
        this.littleItemInfo = littleItemInfo;
        this.bigItemInfo = bigItemInfo;
        this.snackItemInfo = snackItemInfo;
    }

    public ArrayList<ItemInfo> getLittleItemInfo(){
        return littleItemInfo;
    }
    public void setLittleItemInfo(ArrayList<ItemInfo> littleItemInfo){
        this.littleItemInfo = littleItemInfo;
    }

    public ArrayList<ItemInfo> getBigItemInfo(){
        return bigItemInfo;
    }
    public void setBigItemInfo(ArrayList<ItemInfo> bigItemInfo){
        this.bigItemInfo = bigItemInfo;
    }

    public ArrayList<ItemInfo> getSnacktemInfo(){
        return snackItemInfo;
    }
    public void setSnackItemInfo(ArrayList<ItemInfo> snackItemInfo){
        this.snackItemInfo = snackItemInfo;
    }



}
