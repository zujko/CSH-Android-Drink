package edu.csh.cshdrink.activities;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.adapters.ViewPagerFragmentAdapter;

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
        getSupportActionBar().setTitle(mPrefs.getString("uid",""));
        getSupportActionBar().setSubtitle("Credits: "+ mPrefs.getString("credits",""));

        mViewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
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
        startActivity(new Intent(this,LoginActivity.class));
        this.finish();
    }
}
