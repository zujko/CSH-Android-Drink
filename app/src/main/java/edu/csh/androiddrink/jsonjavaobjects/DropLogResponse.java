package edu.csh.androiddrink.jsonjavaobjects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DropLogResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private ArrayList<DropLogItem> items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<DropLogItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DropLogItem> items) {
        this.items = items;
    }
}
