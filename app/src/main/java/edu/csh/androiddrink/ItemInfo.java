package edu.csh.androiddrink;


import com.google.gson.annotations.SerializedName;

public class ItemInfo {

    @SerializedName("slot_num")
    private String slotNum;
    @SerializedName("item_id")
    private String itemId;
    @SerializedName("item_name")
    private String itemName;
    @SerializedName("item_price")
    private String itemPrice;
    @SerializedName("available")
    private String available;
    @SerializedName("status")
    private String status;

    public ItemInfo(String slotNum, String itemId, String itemName, String itemPrice, String available,
                    String status){
        this.slotNum = slotNum;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.available = available;
        this.status = status;
    }

    public String getSlotNum(){
        return slotNum;
    }
    public void setSlotNum(String slotNum){
        this.slotNum = slotNum;
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

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
