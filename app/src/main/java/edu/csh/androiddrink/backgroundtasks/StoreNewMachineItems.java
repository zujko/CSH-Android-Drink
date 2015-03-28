package edu.csh.androiddrink.backgroundtasks;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.List;

import edu.csh.androiddrink.TabPageAdapter;
import edu.csh.androiddrink.activities.MainActivity;
import edu.csh.androiddrink.jsonjavaobjects.ItemInfo;
import edu.csh.androiddrink.jsonjavaobjects.Machine;
import edu.csh.androiddrink.jsonjavaobjects.MachineData;

public class StoreNewMachineItems extends AsyncTask<Void, Void,Void> {

    private ViewPager pager;
    private ActionBarActivity act;

    public StoreNewMachineItems(ActionBarActivity act,ViewPager pager){
        ActiveAndroid.initialize(act);
        this.pager = pager;
        this.act = act;
    }

    @Override
    protected Void doInBackground(Void... params) {
        /* Get all items from each machine */
        Reader reader = API.getData("machines/stock/");
        Machine machine = new GsonBuilder().create().fromJson(reader, Machine.class);
        MachineData machines = machine.getMachineData();

        /* Store all items in the DB */
        ActiveAndroid.beginTransaction();
        try {
            List<ItemInfo> itemsList = new Select().from(ItemInfo.class).execute();
            for(ItemInfo item:itemsList) {
                item.delete();
            }
            for(ItemInfo item:machines.getBigItemInfo()) {
                item.save();
            }
            for(ItemInfo item:machines.getLittleItemInfo()) {
                item.save();
            }
            for(ItemInfo item:machines.getSnacktemInfo()) {
                item.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        return null;
 }


    @Override
    protected void onPostExecute(Void items) {
        if(MainActivity.menuItem != null && pager != null){
            try{
                pager.setAdapter(new TabPageAdapter(act.getSupportFragmentManager()));
            }catch (IllegalStateException e){
                //TODO: Handle this better
                Log.v("STORINGITEMS","ILLEGALSTATEEXCEPTION");
            }

            MainActivity.menuItem.collapseActionView();
            MainActivity.menuItem.setActionView(null);
        }

    }

}
