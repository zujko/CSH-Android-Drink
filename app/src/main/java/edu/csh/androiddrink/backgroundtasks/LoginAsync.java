package edu.csh.androiddrink.backgroundtasks;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.dd.processbutton.iml.ActionProcessButton;
import com.securepreferences.SecurePreferences;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import edu.csh.androiddrink.MainActivity;
import edu.csh.androiddrink.R;


public class LoginAsync extends AsyncTask<Object, String, String>  {

    SecurePreferences prefs;
    private Activity activity;
    private String apiKey;
    final ActionProcessButton btnSignIn;

    public LoginAsync(String key, Activity myActivity){
        apiKey = key;
        btnSignIn = (ActionProcessButton) myActivity.findViewById(R.id.btnSignIn);
        activity = myActivity;
        prefs = new SecurePreferences(activity, "UserData","key", true);
    }

    @Override
    protected String doInBackground(Object... params) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://webdrink.csh.rit.edu/api/index.php?request=test/api/&api_key="+apiKey);
        try{
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            JSONObject json = new JSONObject(builder.toString());
            final String valid = json.getString("message");
            final Boolean isValid;
            /* If the api key is valid, store it in secure preferences
             * and set isValid to true.
             */
            if(valid.contains("Greetings")){
                prefs.put("userKey", apiKey);
                isValid = true;
            }
            else{
                isValid = false;
            }
            /* If an api key was inserted into secure preferences, set the progress of the button
             * to 100 and launch MainActivity. Else, set the progress to -1 (error) and launch a toast
             */
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (isValid){
                        btnSignIn.setProgress(100);
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                    else{
                        btnSignIn.setProgress(-1);
                        Crouton.makeText(activity, valid, Style.ALERT);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}




