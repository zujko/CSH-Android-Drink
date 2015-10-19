package edu.csh.drink.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import edu.csh.androiddrink.R;
import edu.csh.drink.DrinkApplication;
import edu.csh.drink.adapters.ViewPagerFragmentAdapter;
import edu.csh.drink.events.UserDataEvent;
import edu.csh.drink.jobs.GetUserInfoJob;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.view_pager) ViewPager mViewPager;
    @Bind(R.id.tab_layout) TabLayout mTabLayout;
    @Bind(R.id.toolbar) Toolbar mToolBar;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setSupportActionBar(mToolBar);
        DrinkApplication.JOB_MANAGER.addJobInBackground(new GetUserInfoJob(mPrefs.getString("key",""), getApplicationContext()));
        mViewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mPrefs.edit().clear().commit();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    public void onEventMainThread(UserDataEvent event) {
        if(event.isSuccessful) {
            getSupportActionBar().setTitle(mPrefs.getString("uid", ""));
            getSupportActionBar().setSubtitle("Credits: " + mPrefs.getString("credits", ""));
        } else {
            Toast.makeText(getApplicationContext(), "Could not get user data", Toast.LENGTH_SHORT).show();
        }
    }
}
