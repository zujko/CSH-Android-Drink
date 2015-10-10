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

    public String getSlotNumber() {
        return slot_num;
    }

    public String getMachineId() {
        return machine_id;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getItemId() {
        return item_id;
    }

    public String getItemName() {
        return item_name;
    }

    public String getItemPrice() {
        return item_price;
    }

    public boolean isAvailable() {
        return available.equals("1");
    }
}
