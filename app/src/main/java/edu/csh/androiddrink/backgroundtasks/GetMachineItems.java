package edu.csh.androiddrink.backgroundtasks;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.ArrayList;

import edu.csh.androiddrink.jsonjavaobjects.ItemInfo;
import edu.csh.androiddrink.jsonjavaobjects.Machine;
import edu.csh.androiddrink.jsonjavaobjects.MachineData;
import edu.csh.androiddrink.interfaces.MachineDataOnComplete;

public class GetMachineItems extends AsyncTask<Void, Void, ArrayList<ItemInfo>> {

    private int machineInt;
    private MachineDataOnComplete data;


    public GetMachineItems(MachineDataOnComplete data, int machine){
        this.data = data;
        this.machineInt = machine;
    }

    @Override
    protected ArrayList<ItemInfo> doInBackground(Void... params) {
        Reader reader = API.getData("machines/stock/");
        Machine machine = new GsonBuilder().create().fromJson(reader, Machine.class);
        MachineData machines = machine.getMachineData();
        switch(machineInt){
            case 1:
                return machines.getBigItemInfo();
            case 2:
                return machines.getLittleItemInfo();
            case 3:
                return machines.getSnacktemInfo();
            default:
                return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ItemInfo> items) {
        if (data != null){
            data.onComplete(items);
        }

    }

}
