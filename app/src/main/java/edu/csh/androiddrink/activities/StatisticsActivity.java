package edu.csh.androiddrink.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;

import edu.csh.androiddrink.R;
import edu.csh.androiddrink.backgroundtasks.SetUpChart;


public class StatisticsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPrefs.getString("theme_setting",null);
        if(theme.equals("light")){
            setTheme(R.style.Light);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.generalToolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        PieChart pieChart = (PieChart) findViewById(R.id.chart1);
        SetUpChart dropItem = new SetUpChart(this, pieChart);
        dropItem.execute();
    }

}
