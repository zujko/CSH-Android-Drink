package edu.csh.androiddrink;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.securepreferences.SecurePreferences;


public class MainActivity extends FragmentActivity {

    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabs.setViewPager(pager);

        tabs.setTextColor(Color.parseColor("#FFFFFF"));
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
                //TODO: Refresh fragments
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
        SecurePreferences prefs = new SecurePreferences(this,"APIKey","key", true);
        prefs.removeValue("userKey");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Successfully signed out", Toast.LENGTH_SHORT).show();
        this.finish();
    }


}
