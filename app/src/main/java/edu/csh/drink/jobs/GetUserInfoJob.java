package edu.csh.drink.jobs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;
import edu.csh.drink.DrinkApplication;
import edu.csh.drink.events.UserDataEvent;
import edu.csh.drink.models.UserData;
import retrofit.Call;

public class GetUserInfoJob extends Job {

    private String apiKey;
    private Context context;
    private SharedPreferences mPrefs;

    public GetUserInfoJob(String apiKey, Context context) {
        super(new Params(1).requireNetwork());
        this.apiKey = apiKey;
        this.context = context;
    }

    @Override
    public void onAdded() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void onRun() throws Throwable {
        Call<UserData> userDataCall = DrinkApplication.API.getUserInfo(apiKey);
        retrofit.Response<UserData> userDataResponse = userDataCall.execute();
        if(!userDataResponse.raw().isSuccessful()) {
            EventBus.getDefault().post(new UserDataEvent(false));
        } else {
            UserData userData = userDataResponse.body();
            if (userData.status.equals("true")) {
                UserData.User user = userData.data;
                mPrefs.edit().putString("uid", user.uid).commit();
                mPrefs.edit().putString("credits", user.credits).commit();
                mPrefs.edit().putString("admin", user.admin).commit();
                Log.d("USERINFO","Setting data in SF");
                EventBus.getDefault().post(new UserDataEvent(true));
            } else {
                EventBus.getDefault().post(new UserDataEvent(false));
            }
        }
    }

    @Override
    protected void onCancel() {}

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
