package edu.csh.cshdrink.models;

public class Item {
    public String slot_num;

    public String machine_id;

    public String display_name;

    public String item_id;

    public String item_name;

    public String item_price;

    public String available;

    public String status;

    public boolean isAvailable() {
        return !available.equals("0");
    }
    public boolean isEnabled(){return status.equals("enabled");}
}
