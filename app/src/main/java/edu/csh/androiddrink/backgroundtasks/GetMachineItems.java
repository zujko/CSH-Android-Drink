package edu.csh.androiddrink.backgroundtasks;


import android.app.Activity;
import android.os.AsyncTask;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import edu.csh.androiddrink.interfaces.MachineDataOnComplete;
import edu.csh.androiddrink.jsonjavaobjects.ItemInfo;

public class GetMachineItems extends AsyncTask<Void,Void,List<ItemInfo>> {

    private String machineId;
    private MachineDataOnComplete dataOnComplete;

    public GetMachineItems(MachineDataOnComplete onComp,Activity act,String machineId){
        this.dataOnComplete = onComp;
        this.machineId = machineId;
        ActiveAndroid.initialize(act);
    }

    @Override
    protected List<ItemInfo> doInBackground(Void... params) {
        return new Select().from(ItemInfo.class).where("machineId = ?",machineId).execute();
    }

    @Override
    protected void onPostExecute(List<ItemInfo> items) {
        if(dataOnComplete != null){
            dataOnComplete.onComplete(new ArrayList<>(items));
        }

    }
}
