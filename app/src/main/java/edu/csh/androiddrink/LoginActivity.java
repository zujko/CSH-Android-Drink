package edu.csh.androiddrink;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.securepreferences.SecurePreferences;


public class LoginActivity extends Activity {

    SharedPreferences prefs;
    Activity thisAct = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                prefs = new SecurePreferences(thisAct);
                prefs.edit().putString("key",editPassword.getText().toString()).commit();
                String api = prefs.getString("key","null");
                Toast.makeText(thisAct, "SharedPref "+api, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
