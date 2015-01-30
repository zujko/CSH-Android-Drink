package edu.csh.androiddrink;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.ArrayList;

public class SyncAsync extends AsyncTask<Void, Void, String> {

    public SyncAsync(){

    }

    @Override
    protected String doInBackground(Void... params) {
        Reader reader = API.getData();
        Machine machine = new GsonBuilder().create().fromJson(reader, Machine.class);
        MachineData machines = machine.getMachineData();
        ArrayList<ItemInfo> bigDrinkItems = machines.getBigItemInfo();
        ArrayList<ItemInfo> littleDrinkItems = machines.getLittleItemInfo();
        ArrayList<ItemInfo> snackItems = machines.getSnacktemInfo();

        return null;
    }

    @Override
    protected void onPostExecute(String strings) {

    }

}
