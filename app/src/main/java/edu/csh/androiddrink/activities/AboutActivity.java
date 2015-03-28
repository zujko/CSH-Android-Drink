package edu.csh.androiddrink.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import edu.csh.androiddrink.R;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPrefs.getString("theme_setting",null);
        if(theme.equals("light")){
            setTheme(R.style.Light);
        }

        setToolBar();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        TextView view = (TextView) findViewById(R.id.aboutAct);
        view.setText(Html.fromHtml(getString(R.string.aboutText)));
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.generalToolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

}
