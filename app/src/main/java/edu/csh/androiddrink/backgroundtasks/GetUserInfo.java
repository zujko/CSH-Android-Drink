package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.GsonBuilder;
import com.securepreferences.SecurePreferences;

import java.io.Reader;
import java.lang.reflect.Modifier;

import edu.csh.androiddrink.activities.MainActivity;
import edu.csh.androiddrink.interfaces.UserDataOnComplete;
import edu.csh.androiddrink.jsonjavaobjects.UserData;
import edu.csh.androiddrink.jsonjavaobjects.UserDataResponse;

public class GetUserInfo extends AsyncTask<Void, Void, UserData>{

    private String apiKey;
    SecurePreferences prefs;
    private UserDataOnComplete data;
    private MainActivity mainAct;
    private Activity act;


    public GetUserInfo(UserDataOnComplete data,MainActivity mainAct, Activity act){
        this.data = data;
        this.mainAct = mainAct;
        prefs  = new SecurePreferences(act,"UserData","key", true);
        apiKey = prefs.getString("userKey");
    }

    @Override
    protected UserData doInBackground(Void... params) {
        Reader reader = API.getData("users/info&api_key="+apiKey);
        UserDataResponse response = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create().fromJson(reader, UserDataResponse.class);
        return response.getUserData();
    }

    @Override
    protected void onPostExecute(UserData info) {

        /* Refresh credit amount */
        if(prefs.getString("credits") == null){
            prefs.put("credits",info.getCredits());
        }
        else{
            prefs.removeValue("credits");
            prefs.put("credits",info.getCredits());
        }

        if(prefs.getString("admin") == null){
            prefs.put("admin",info.getAdmin());
        }
        if(prefs.getString("uid") == null){
            prefs.put("uid",info.getUid());
        }
        if (data != null){
            data.onComplete(info);
        }
        if(mainAct != null){
            mainAct.refreshCredits();
        }
        MainActivity.credits = true;
        if(MainActivity.bar != null){
            MainActivity.bar.invalidateOptionsMenu();
            MainActivity.bar.setSubtitle("Credits: "+prefs.getString("credits"));
        }

    }

}
