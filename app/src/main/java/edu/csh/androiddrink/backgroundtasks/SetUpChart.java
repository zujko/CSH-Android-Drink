package edu.csh.androiddrink.backgroundtasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.securepreferences.SecurePreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.csh.androiddrink.jsonjavaobjects.DropLogItem;


public class SetUpChart extends AsyncTask<Void,Void,HashMap<String,Integer>> {

    SecurePreferences prefs;
    private PieChart chart;

    public SetUpChart(Activity act, PieChart chart){
        prefs  = new SecurePreferences(act,"UserData","key", true);
        this.chart = chart;
        ActiveAndroid.initialize(act);
    }

    @Override
    protected HashMap<String,Integer> doInBackground(Void... params) {
        List<DropLogItem> listItems = new Select().from(DropLogItem.class).execute();
        HashMap<String,Integer> map = new HashMap<>();
        for(DropLogItem item: listItems){
            if(map.get(item.getItemName()) != null){
                map.put(item.getItemName(), map.get(item.getItemName())+1);
            }else{
                map.put(item.getItemName(),1);
            }
        }

        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String,Integer> dropLogItems) {
        ArrayList<String> drinks = new ArrayList<>();
        ArrayList<Entry> drinkData = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        chart.setHoleColorTransparent(true);
        chart.setUsePercentValues(true);
        chart.setHoleRadius(50f);
        chart.setDescription("");
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        int counter = 0;
        for(String key: dropLogItems.keySet()){
            drinks.add(key);
            Float fVal = (float) dropLogItems.get(key);
            drinkData.add(new Entry(fVal,counter));
            counter++;
        }
        PieDataSet dataSet = new PieDataSet(drinkData,"Drink Stats");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3f);
        PieData data = new PieData(drinks,dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        chart.setData(data);
        chart.highlightValues(null);
        chart.animateXY(1500, 1500);
        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }
}
