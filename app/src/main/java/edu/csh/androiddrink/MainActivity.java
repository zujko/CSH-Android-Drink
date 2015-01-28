package edu.csh.androiddrink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.securepreferences.SecurePreferences;


public class MainActivity extends Activity {

    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_refresh:
                //TODO: Refresh items
                menuItem = item;
                menuItem.setActionView(R.layout.action_progress);
                menuItem.expandActionView();
                break;
            case R.id.action_settings:
                //TODO: Open settings activity
                break;
            default:
                break;
        }
        return true;
    }

    public void onClickLogOut(MenuItem item){
        SecurePreferences prefs = new SecurePreferences(this,"APIKey","key", true);
        prefs.removeValue("userKey");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Successfully signed out", Toast.LENGTH_SHORT).show();
        this.finish();
    }


}
