package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.gson.GsonBuilder;
import com.securepreferences.SecurePreferences;

import java.io.Reader;
import java.lang.reflect.Modifier;

import edu.csh.androiddrink.jsonjavaobjects.DropLogItem;
import edu.csh.androiddrink.jsonjavaobjects.DropLogResponse;

public class DropLogToDB extends AsyncTask<Void,Void,Void> {

    private Activity act;

    public DropLogToDB(Activity act){
        this.act = act;
        ActiveAndroid.initialize(act);
    }

    @Override
    protected Void doInBackground(Void... params) {
        SecurePreferences prefs = new SecurePreferences(act,"UserData","key", true);

        Reader reader = API.getData("users/drops&uid="+prefs.getString("uid")+"&api_key="+prefs.getString("userKey"));
        DropLogResponse response = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create().fromJson(reader,DropLogResponse.class);

        for(DropLogItem item: response.getItems()){
            DropLogItem dropItem = new Select().from(DropLogItem.class).where("logId = ?", item.getLogId()).executeSingle();
            if(dropItem != null){
                break;
            } else{
                item.save();
            }
        }
        return null;
    }

}
