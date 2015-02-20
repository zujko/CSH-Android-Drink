package edu.csh.androiddrink.jsonjavaobjects;


import com.google.gson.annotations.SerializedName;

public class ItemInfo {

    @SerializedName("item_id")
    private String itemId;
    @SerializedName("item_name")
    private String itemName;
    @SerializedName("item_price")
    private String itemPrice;
    @SerializedName("available")
    private String available;
    @SerializedName("slot_num")
    private String slotNum;

    public ItemInfo(String slotNum, String itemId, String itemName, String itemPrice, String available){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.available = available;
        this.slotNum= slotNum;
    }

    public String getItemId(){
        return itemId;
    }
    public void setItemId(String itemId){
        this.itemId = itemId;
    }

    public String getItemName(){return itemName;}
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public String getItemPrice(){
        return itemPrice;
    }
    public void setItemPrice(String itemPrice){
        this.itemPrice = itemPrice;
    }

    public String getAvailable(){
        return available;
    }
    public void setAvailable(String available){
        this.available = available;
    }

    public String getSlotNum(){
        return slotNum;
    }
    public void setSlotNum(String slotNum){
        this.slotNum=slotNum;
    }

}
