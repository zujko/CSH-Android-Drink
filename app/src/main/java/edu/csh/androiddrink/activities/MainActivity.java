package edu.csh.androiddrink.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.securepreferences.SecurePreferences;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import edu.csh.androiddrink.R;
import edu.csh.androiddrink.TabPageAdapter;
import edu.csh.androiddrink.backgroundtasks.DropLogToDB;
import edu.csh.androiddrink.backgroundtasks.GetUserInfo;
import edu.csh.androiddrink.backgroundtasks.StoreNewMachineItems;
import edu.csh.androiddrink.interfaces.UserDataOnComplete;
import edu.csh.androiddrink.jsonjavaobjects.UserData;


public class MainActivity extends ActionBarActivity implements UserDataOnComplete {

    public static MenuItem menuItem = null;
    public static android.support.v7.app.ActionBar bar = null;
    public static boolean credits = false;
    public static boolean  isConnectedToNetwork;
    private String theme;
    PagerSlidingTabStrip tabs = null;
    ViewPager pager;
    SecurePreferences prefs;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String getTheme = sharedPrefs.getString("theme_setting",null);
        if(!getTheme.equals(theme)){
            theme = getTheme;
            if(theme.equals("light")){
                recreate();
                setTheme(R.style.Light);
            }else{
                recreate();
                setTheme(R.style.Dark);
            }

        }
        isConnectedToNetwork = isConnectedToNetwork();
        if(isConnectedToNetwork){
            StoreNewMachineItems item = new StoreNewMachineItems(this,pager);
            item.execute();
            if(credits){
                refreshCredits();
                credits = false;
            }
        }else{
            Crouton.makeText(this,"Not connected to a network", Style.ALERT).show();
            Crouton.makeText(this,"Please connect to a network and refresh",Style.ALERT).show();
        }
    }

    public void refreshCredits(){
        prefs = new SecurePreferences(this,"UserData","key", true);
        bar = getSupportActionBar();
        bar.invalidateOptionsMenu();
        bar.setSubtitle("Credits: "+prefs.getString("credits"));
    }

    private boolean isConnectedToNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPrefs.getString("theme_setting",null);
        if(theme != null && theme.equals("light")){
            setTheme(R.style.Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnectedToNetwork = isConnectedToNetwork();
        if(isConnectedToNetwork){
            pager = (ViewPager) findViewById(R.id.pager);
            final StoreNewMachineItems items = new StoreNewMachineItems(this,pager);
            items.execute();
            final GetUserInfo userInfo = new GetUserInfo(this,this,this);
            userInfo.execute();
            DropLogToDB dataDB = new DropLogToDB(this);
            dataDB.execute();

            bar = getSupportActionBar();
            bar.setTitle("CSH Drink");


            pager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));

            tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

            tabs.setViewPager(pager);
            tabs.setIndicatorColor(Color.parseColor("#E11C52"));
            tabs.setIndicatorHeight(7);

        }else{
            Crouton.makeText(this,"Not connected to a network", Style.ALERT).show();
            bar = getSupportActionBar();
            bar.setTitle("CSH Drink");

            Crouton.makeText(this,"Please connect to a network and refresh",Style.ALERT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handles items clicked on the action bar */
        switch(item.getItemId()){
            case R.id.action_refresh:
                menuItem = item;
                menuItem.setActionView(R.layout.action_progress);
                menuItem.expandActionView();
                if(isConnectedToNetwork()){
                    GetUserInfo info = new GetUserInfo(null,this,this);
                    info.execute();
                    StoreNewMachineItems items = new StoreNewMachineItems(this,pager);
                    items.execute();
                    if(tabs == null) {
                        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

                        if(pager != null){
                            tabs.setViewPager(pager);
                        }
                        tabs.setIndicatorColor(Color.parseColor("#E11C52"));
                        tabs.setIndicatorHeight(7);
                    }
                }else{
                    Crouton.makeText(this,"Not connected to a network",Style.ALERT).show();
                    menuItem.collapseActionView();
                    menuItem.setActionView(null);
                }
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_info:
                Intent statsIntent = new Intent(this,StatisticsActivity.class);
                startActivity(statsIntent);
            default:
                break;
        }
        return true;
    }

    /**
     * Signs the user out by removing the API key from secure preferences and
     * launching LoginActivity.
     * @param item logout MenuItem
     */
    public void onClickLogOut(MenuItem item){
        SecurePreferences prefs = new SecurePreferences(this,"UserData","key", true);
        prefs.removeValue("userKey");
        prefs.removeValue("credits");
        prefs.removeValue("admin");
        prefs.removeValue("uid");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Successfully signed out", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }

    @Override
    public void onComplete(UserData data) {
        bar = getSupportActionBar();
        bar.setSubtitle("Credits: "+data.getCredits());
    }
}
