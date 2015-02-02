package edu.csh.androiddrink;


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

    public ItemInfo(String slotNum, String itemId, String itemName, String itemPrice, String available,
                    String status){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.available = available;
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

}
