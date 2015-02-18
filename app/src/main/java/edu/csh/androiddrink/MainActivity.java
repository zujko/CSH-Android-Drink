package edu.csh.androiddrink;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.securepreferences.SecurePreferences;

import edu.csh.androiddrink.backgroundtasks.GetUserInfo;
import edu.csh.androiddrink.interfaces.UserDataOnComplete;


public class MainActivity extends ActionBarActivity implements UserDataOnComplete {

    private MenuItem menuItem;
    android.support.v7.app.ActionBar bar;
    public static boolean credits = false;
    SecurePreferences prefs;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(credits){
            refreshCredits();
            credits = false;
        }
    }

    public void refreshCredits(){
        prefs = new SecurePreferences(this,"UserData","key", true);
        bar = getSupportActionBar();
        bar.invalidateOptionsMenu();
        bar.setSubtitle("Credits: "+prefs.getString("credits"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetUserInfo userInfo = new GetUserInfo(this,this,this,null);
        userInfo.execute();
        bar = getSupportActionBar();
        bar.setTitle("CSH Drink");

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabs.setViewPager(pager);
        tabs.setIndicatorColor(Color.parseColor("#9D4799"));
        tabs.setIndicatorHeight(7);
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
                GetUserInfo info = new GetUserInfo(null,this,this,item);
                info.execute();
                ViewPager pager = (ViewPager) findViewById(R.id.pager);
                pager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));
                //TODO: Make sure refresh animation stops after fragments are created
                break;
            case R.id.action_settings:
                //TODO: Open settings activity
                break;
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
        prefs.removeValue("ibutton");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Successfully signed out", Toast.LENGTH_SHORT).show();
        this.finish();
    }



    @Override
    public void onComplete(UserData data) {
        bar = getSupportActionBar();
        bar.setSubtitle("Credits: "+data.getCredits());
    }
}
