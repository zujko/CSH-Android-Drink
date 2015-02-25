package edu.csh.androiddrink.backgroundtasks;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class API {

    private static Reader reader =null;

    public static Reader getData(String params){
        try{
            final String SERVER_URL = "https://webdrink.csh.rit.edu/api/?request="+params;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(SERVER_URL);
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                reader = new InputStreamReader(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
