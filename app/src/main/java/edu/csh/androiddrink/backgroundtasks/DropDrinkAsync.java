package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.securepreferences.SecurePreferences;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class DropDrinkAsync extends AsyncTask<Void, Void, String> {

    private String machine_id;
    private String slot_num;
    private String delay;
    private Activity act;

    public DropDrinkAsync(String machine_id, String slot_num,String delay, Activity act){
        this.machine_id = machine_id;
        this.slot_num = slot_num;
        this.delay = delay;
        this.act = act;
    }

    @Override
    protected String doInBackground(Void... params) {
        SecurePreferences prefs = new SecurePreferences(act,"UserData","key", true);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://webdrink.csh.rit.edu/api/index.php?request=drops/drop");
        List<NameValuePair> pairs = new ArrayList<>();
        act.runOnUiThread(new Runnable() {
            public void run() {
                if(delay.equals("") || delay.equals("1")){
                    Crouton.makeText(act,"Preparing to drop drink in 1 second",Style.INFO).show();
                }
                else{
                    Crouton.makeText(act,"Preparing to drop drink in "+delay+" seconds",Style.INFO).show();
                }
            }
        });
        pairs.add(new BasicNameValuePair("machine_id", machine_id));
        pairs.add(new BasicNameValuePair("slot_num",slot_num));
        pairs.add(new BasicNameValuePair("delay",delay));
        pairs.add(new BasicNameValuePair("api_key",prefs.getString("userKey")));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(responseBody);
            return obj.getString("status");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        if(status.equals("true")){
            Crouton.makeText(act,"Drink dropped!",Style.CONFIRM).show();
        }
        else{
            Crouton.makeText(act,"Error dropping drink",Style.ALERT).show();
        }
    }
}
