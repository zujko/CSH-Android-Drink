package edu.csh.androiddrink;

import android.content.Context;
import android.os.AsyncTask;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.ArrayList;

public class SyncAsync extends AsyncTask<Void, Void, ArrayList<ItemInfo>> {

    Context myContext;
    MenuItem myItem;

    public SyncAsync(Context context, MenuItem item){
        myContext = context;
        myItem = item;
    }

    @Override
    protected ArrayList<ItemInfo> doInBackground(Void... params) {
        Gson gson= new Gson();
        Reader reader = API.getData();
        Machine machine = new GsonBuilder().create().fromJson(reader, Machine.class);
        MachineData machines = machine.getMachineData();
        ArrayList<ItemInfo> bigDrinkItems = machines.getBigItemInfo();
        ArrayList<ItemInfo> littleDrinkItems = machines.getLittleItemInfo();
        ArrayList<ItemInfo> snackItems = machines.getSnacktemInfo();
        return bigDrinkItems;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemInfo> items) {

        myItem.collapseActionView();
        myItem.setActionView(null);

    }

}
