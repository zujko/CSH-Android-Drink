package edu.csh.androiddrink.jsonjavaobjects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name = "Items")
public class DropLogItem extends Model {

    @SerializedName("drop_log_id")
    @Column(name = "logId", unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    private String logId;
    @SerializedName("display_name")
    @Column(name = "machineName")
    private String machineName;
    @SerializedName("item_name")
    @Column(name = "itemName")
    private String itemName;

    public DropLogItem(String logId,String machineName, String itemName){
        super();
        this.logId = logId;
        this.machineName = machineName;
        this.itemName = itemName;
    }

    public DropLogItem(){
        super();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMachinename() {
        return machineName;
    }

    public void setMachinename(String machinename) {
        this.machineName = machinename;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
