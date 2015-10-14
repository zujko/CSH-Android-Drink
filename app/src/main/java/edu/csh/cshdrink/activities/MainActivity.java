package edu.csh.cshdrink.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.DrinkApplication;
import edu.csh.cshdrink.adapters.ViewPagerFragmentAdapter;
import edu.csh.cshdrink.models.UserData;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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
        getUserInfo(mPrefs.getString("key",""));
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
    /**
     * Gets user info (uid,credits,admin,name) and stores it in SharedPreferences.
     * @param apiKey
     */
    private void getUserInfo(String apiKey) {
        Call<UserData> userDataCall = DrinkApplication.API.getUserInfo(apiKey);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Response<UserData> response, Retrofit retrofit) {
                UserData userData = response.body();
                if (userData.status.equals("true")) {
                    UserData.User user = userData.data;
                    String data = String.format("UID: %s CREDITS: %s ADMIN: %s", user.uid, user.credits, user.admin);
                    Log.d("USER DATA", data);
                    mPrefs.edit().putString("uid", user.uid).commit();
                    mPrefs.edit().putString("credits", user.credits).commit();
                    mPrefs.edit().putString("admin", user.admin).commit();
                    getSupportActionBar().setTitle(mPrefs.getString("uid",""));
                    getSupportActionBar().setSubtitle("Credits: "+ mPrefs.getString("credits",""));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Could not get user data", Toast.LENGTH_SHORT).show();
                String errorMessage = String.format("Error: %s\nMessage: %s\nCause: %s", t.toString(), t.getMessage(), t.getCause().getMessage());
                Log.e("LOGIN", errorMessage);
            }
        });
    }
}
