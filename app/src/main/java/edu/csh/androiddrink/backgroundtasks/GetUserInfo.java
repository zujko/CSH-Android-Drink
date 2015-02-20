package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;
import com.securepreferences.SecurePreferences;

import java.io.Reader;

import edu.csh.androiddrink.MainActivity;
import edu.csh.androiddrink.jsonjavaobjects.UserData;
import edu.csh.androiddrink.jsonjavaobjects.UserDataResponse;
import edu.csh.androiddrink.interfaces.UserDataOnComplete;

public class GetUserInfo extends AsyncTask<Void, Void, UserData>{

    private String apiKey;
    SecurePreferences prefs;
    private UserDataOnComplete data;
    private MainActivity mainAct;
    private Activity act;
    private MenuItem item;


    public GetUserInfo(UserDataOnComplete data,MainActivity mainAct, Activity act,MenuItem item){
        this.data = data;
        this.mainAct = mainAct;
        this.item = item;
        prefs  = new SecurePreferences(act,"UserData","key", true);
        apiKey = prefs.getString("userKey");
    }

    @Override
    protected UserData doInBackground(Void... params) {
        Reader reader = API.getData("users/info&api_key="+apiKey);
        UserDataResponse response = new GsonBuilder().create().fromJson(reader, UserDataResponse.class);
        return response.getUserData();
    }

    @Override
    protected void onPostExecute(UserData info) {
        if(prefs.getString("credits") == null){
            prefs.put("credits",info.getCredits());
        }
        else{
            prefs.removeValue("credits");
            prefs.put("credits",info.getCredits());
        }
        if (data != null){
            data.onComplete(info);
        }
        if(mainAct != null){
            mainAct.refreshCredits();
        }
        MainActivity.credits = true;
        if(item != null) {
            item.collapseActionView();
            item.setActionView(null);
        }


    }

}
