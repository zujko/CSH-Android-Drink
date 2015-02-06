package edu.csh.androiddrink.backgroundtasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.ArrayList;

import edu.csh.androiddrink.DataOnComplete;
import edu.csh.androiddrink.ItemInfo;
import edu.csh.androiddrink.Machine;
import edu.csh.androiddrink.MachineData;

public class GetMachineItems extends AsyncTask<Void, Void, ArrayList<ItemInfo>> {

    private int machineInt;
    private DataOnComplete data;
    Context myContext;


    public GetMachineItems(DataOnComplete data, int machine){
        this.data = data;
        this.machineInt = machine;
    }

    @Override
    protected ArrayList<ItemInfo> doInBackground(Void... params) {
        Gson gson= new Gson();
        Reader reader = API.getData();
        Machine machine = new GsonBuilder().create().fromJson(reader, Machine.class);
        MachineData machines = machine.getMachineData();
        switch(machineInt){
            case 1:
                System.out.println("RETURNING BIG DRINK");
                return machines.getBigItemInfo();
            case 2:
                System.out.println("RETURNING LITTLE DRINK");
                return machines.getLittleItemInfo();
            case 3:
                System.out.println("RETURNING SNACK");
                return machines.getSnacktemInfo();
            default:
                System.out.println("RETURNING NULL");
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
