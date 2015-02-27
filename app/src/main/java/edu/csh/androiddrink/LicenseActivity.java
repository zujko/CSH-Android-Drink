package edu.csh.androiddrink;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


public class LicenseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPrefs.getString("theme_setting",null);
        if(theme.equals("light")){
            setTheme(R.style.Light);
        }
        setToolBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

    }

    private void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLicense);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

    }
}
