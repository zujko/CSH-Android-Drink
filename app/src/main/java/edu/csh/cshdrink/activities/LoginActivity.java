package edu.csh.cshdrink.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.DrinkApplication;
import edu.csh.cshdrink.models.Test;
import edu.csh.cshdrink.models.UserData;
import edu.csh.cshdrink.network.DrinkService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_button) Button mLoginButton;
    @Bind(R.id.get_apikey_button) Button mGetKeyButton;
    @Bind(R.id.api_key_edittext) EditText mApiKeyEditText;
    private Dialog mLoginDialog;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(mPrefs.getString("key",null) != null) {
            finishLogin();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUpButtons();

    }

    /**
     * Sets up buttons by implementing an onClick listener.
     */
    private void setUpButtons() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String apiKey = mApiKeyEditText.getText().toString().trim();
                if (apiKey.equals("") || apiKey.equals(" ")) {
                    Toast.makeText(getApplicationContext(), "API key cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Test> testCall = DrinkApplication.API.getTest(apiKey);
                    testCall.enqueue(new Callback<Test>() {
                        @Override
                        public void onResponse(Response<Test> response, Retrofit retrofit) {
                            if (response.body().data) {
                                getUserInfo(apiKey);
                                mPrefs.edit().putString("key",apiKey).apply();
                                finishLogin();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid API key", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        mGetKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLoginDialog();
            }
        });
    }

    /**
     * Creates the dialog which shows a webview to get your api key
     */
    private void createLoginDialog() {
        AlertDialog.Builder webDialog = new AlertDialog.Builder(this);
        WebView webView = new WebView(this);
        LinearLayout wrapper = new LinearLayout(this);
        EditText keyboardHack = new EditText(this);
        keyboardHack.setVisibility(View.GONE);

        createWebView(webView);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        wrapper.addView(keyboardHack, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        webDialog.setView(wrapper);
        webDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mLoginDialog = webDialog.create();
        mLoginDialog.show();
    }

    /**
     * Creates the webview
     * @param webView
     */
    private void createWebView(final WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(DrinkService.REDIRECT_URL)) {
                    String token = url.split(DrinkService.REDIRECT_URL)[1];
                    mApiKeyEditText.setText("");
                    mApiKeyEditText.append(token);
                    webView.stopLoading();
                    webView.destroy();
                    mLoginDialog.dismiss();
                    return false;
                }
                return super.shouldOverrideUrlLoading(view,url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("https://webauth")) {
                    super.onPageFinished(view, url);
                } else if (url.startsWith("https://webdrink.csh.rit")) {
                    view.loadUrl(DrinkService.LOGIN_URL);
                }
            }
        });
        webView.loadUrl("https://webdrink.csh.rit.edu");
    }

    /**
     * Finishes the login process but starting the main activity
     * and calling finish on the login activity.
     */
    private void finishLogin() {
        startActivity(new Intent(this, MainActivity.class));
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
                if(userData.status.equals("true")) {
                    UserData.User user = userData.data;
                    String data = String.format("UID: %s CREDITS: %s ADMIN: %s",user.uid,user.credits,user.admin);
                    Log.d("USER DATA",data);
                    mPrefs.edit().putString("uid",user.uid).commit();
                    mPrefs.edit().putString("credits",user.credits).commit();
                    mPrefs.edit().putString("admin",user.admin).commit();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Could not get user data", Toast.LENGTH_SHORT).show();
                String errorMessage = String.format("Error: %s\nMessage: %s\nCause: %s",t.toString(),t.getMessage(),t.getCause().getMessage());
                Log.e("LOGIN", errorMessage);
            }
        });
    }
}
