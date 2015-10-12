package edu.csh.cshdrink.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BulkMachineData {
    public String status;

    public String message;

    public MachineData data;

    public class MachineData {
        @SerializedName("1")
        public List<Item> littleDrink;

        @SerializedName("2")
        public List<Item> bigDrink;

        @SerializedName("3")
        public List<Item> snack;

    }
}
