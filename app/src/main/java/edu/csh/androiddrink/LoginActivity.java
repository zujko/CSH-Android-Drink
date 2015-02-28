package edu.csh.androiddrink;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.securepreferences.SecurePreferences;

import edu.csh.androiddrink.backgroundtasks.LoginAsync;


public class LoginActivity extends Activity {

    Activity thisAct = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String theme = sharedPrefs.getString("theme_setting",null);
        if(theme != null && theme.equals("light")){
            setTheme(R.style.Light);
        }else{
            sharedPrefs.edit().putString("theme_setting","dark").apply();
        }
        super.onCreate(savedInstanceState);
        SecurePreferences prefs = new SecurePreferences(this,"UserData","key", true);
        /*
         * If there is already an API key stored, launch main activity
         * else, create the login activity
         */
        if(prefs.getString("userKey") != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        else{
            /* Make activity fullscreen */
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_login);

            final EditText editPassword = (EditText) findViewById(R.id.editPassword);
            final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSignIn.setProgress(1);
                    LoginAsync loginAsync = new LoginAsync(editPassword.getText().toString(),thisAct);
                    loginAsync.execute();
                }
            });
        }

    }
}
