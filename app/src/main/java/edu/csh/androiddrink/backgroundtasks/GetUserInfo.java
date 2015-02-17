package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.securepreferences.SecurePreferences;

import java.io.Reader;

import edu.csh.androiddrink.UserData;
import edu.csh.androiddrink.UserDataResponse;
import edu.csh.androiddrink.interfaces.UserDataOnComplete;

public class GetUserInfo extends AsyncTask<Void, Void, UserData> {

    private String apiKey;
    SecurePreferences prefs;
    private UserDataOnComplete data;


    public GetUserInfo(UserDataOnComplete data,Activity act){
        this.data = data;
        prefs  = new SecurePreferences(act,"UserData","key", true);
        apiKey = prefs.getString("userKey");
    }

    @Override
    protected UserData doInBackground(Void... params) {
        Gson gson= new Gson();
        Reader reader = API.getData("users/info&api_key="+apiKey);
        UserDataResponse response = new GsonBuilder().create().fromJson(reader, UserDataResponse.class);
        return response.getUserData();
    }

    @Override
    protected void onPostExecute(UserData info) {
        if(prefs.getString("credits") == null){
            prefs.put("credits",info.getCredits());
            prefs.put("ibutton",info.getIbutton());
        }
        else{
            prefs.removeValue("credits");
            prefs.put("credits",info.getCredits());
        }
        if (data != null){
            data.onComplete(info);
        }

    }

}
