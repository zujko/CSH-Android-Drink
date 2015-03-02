package edu.csh.androiddrink.jsonjavaobjects;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name ="DrinkItems")
public class ItemInfo extends Model {

    @SerializedName("machine_id")
    @Column(name = "machineId")
    private String machineId;

    @SerializedName("item_id")
    @Column(name = "itemId")
    private String itemId;

    @SerializedName("item_name")
    @Column(name = "itemName")
    private String itemName;

    @SerializedName("item_price")
    @Column(name = "itemPrice")
    private String itemPrice;

    @SerializedName("available")
    @Column(name = "available")
    private String available;

    @SerializedName("slot_num")
    @Column(name = "slotNum")
    private String slotNum;

    @SerializedName("status")
    @Column(name = "status")
    private String status;

    public ItemInfo(String machineId, String slotNum, String itemId, String itemName, String itemPrice, String available, String status){
        super();
        this.machineId = machineId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.available = available;
        this.slotNum= slotNum;
        this.status = status;
    }

    public ItemInfo(){
        super();
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

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
