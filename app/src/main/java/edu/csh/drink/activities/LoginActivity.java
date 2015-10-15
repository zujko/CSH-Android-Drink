package edu.csh.drink.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.drink.DrinkApplication;
import edu.csh.drink.models.Test;
import edu.csh.drink.network.DrinkService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_button) Button mLoginButton;
    @Bind(R.id.get_apikey_button) Button mGetKeyButton;
    @Bind(R.id.api_key_edittext) EditText mApiKeyEditText;
    private ProgressBar mProgressBar;
    private Dialog mLoginDialog;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(mPrefs.getString("key",null) != null) {
            finishLogin();
        }
        mProgressBar = new ProgressBar(this);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
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
        webView.setVisibility(View.INVISIBLE);
        LinearLayout wrapper = new LinearLayout(this);
        EditText keyboardHack = new EditText(this);
        keyboardHack.setVisibility(View.GONE);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.addView(webView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        wrapper.addView(keyboardHack, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        wrapper.addView(mProgressBar, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        wrapper.setGravity(Gravity.CENTER_VERTICAL);
        webDialog.setView(wrapper);
        webDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mLoginDialog = webDialog.create();
        createWebView(webView);
        mLoginDialog.show();
    }

    /**
     * Creates the webview
     * @param webView
     */
    private void createWebView(final WebView webView) {
        final String tag = "WEBVIEW";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(mProgressBar.getVisibility() == View.INVISIBLE && view.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(DrinkService.REDIRECT_URL)) {
                    String token = url.split(DrinkService.REDIRECT_URL)[1];
                    mApiKeyEditText.setText("");
                    mApiKeyEditText.append(token);
                    webView.stopLoading();
                    webView.destroy();
                    mLoginDialog.dismiss();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("https://webauth")) {
                    if(mProgressBar.getVisibility() == View.VISIBLE && view.getVisibility() == View.INVISIBLE) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        view.setVisibility(View.VISIBLE);
                    }
                    super.onPageFinished(view, url);
                } else if (url.startsWith("https://webdrink.csh.rit") && !url.startsWith(DrinkService.LOGIN_URL)) {
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
}
