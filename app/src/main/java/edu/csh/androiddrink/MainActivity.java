package edu.csh.androiddrink;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.securepreferences.SecurePreferences;


public class MainActivity extends FragmentActivity {


    private MenuItem menuItem;
    ViewPager Tab;
    TabPageAdapter tabAdapter;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabAdapter = new TabPageAdapter(getSupportFragmentManager());
        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);                    }
                });
        Tab.setAdapter(tabAdapter);
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Tab.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }};
        actionBar.addTab(actionBar.newTab().setText("Info").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Big Drink").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Little Drink").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Snack").setTabListener(tabListener));

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
