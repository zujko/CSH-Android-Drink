package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.securepreferences.SecurePreferences;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class DropDrinkAsync extends AsyncTask<Void,Void,Void> {

    private String delay;
    private String slot_num;
    private String machineId;
    private String itemName;
    private boolean success;
    Activity act;
    SecurePreferences prefs;

    public DropDrinkAsync(String delay, Activity activity, String slot_num, String machineId, String itemName){
        this.delay = delay;
        prefs  = new SecurePreferences(activity,"UserData","key", true);
        this.slot_num = slot_num;
        this.machineId = machineId;
        this.itemName = itemName;
        this.act = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://webdrink.csh.rit.edu/api/?request=drops/drop");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("ibutton",prefs.getString("ibutton")));
        nameValuePairs.add(new BasicNameValuePair("machine_id",machineId));
        nameValuePairs.add(new BasicNameValuePair("slot_num",slot_num));
        nameValuePairs.add(new BasicNameValuePair("delay",delay));

        try{
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try{
            HttpResponse response = httpClient.execute(httpPost);
            Log.v("HTTPPOSTRESPONSE",response.toString());
            success = true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(success){
            Toast.makeText(act,"Dropping "+itemName,Toast.LENGTH_LONG).show();
        }
    }
}
