package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;
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

public class DropDrinkAsync extends AsyncTask<Void, Void, Void> {

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
    protected Void doInBackground(Void... params) {
        SecurePreferences prefs = new SecurePreferences(act,"UserData","key", true);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://webdrink.csh.rit.edu/api/index.php?request=drops/drop");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("machine_id", machine_id));
        pairs.add(new BasicNameValuePair("slot_num",slot_num));
        pairs.add(new BasicNameValuePair("delay",delay));
        pairs.add(new BasicNameValuePair("api_key",prefs.getString("userKey")));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(act,"Dropping",Toast.LENGTH_SHORT).show();
    }
}
