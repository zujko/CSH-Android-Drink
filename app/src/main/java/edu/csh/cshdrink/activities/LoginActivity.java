package edu.csh.cshdrink.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.network.DrinkService;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_button) Button mLoginButton;
    @Bind(R.id.get_apikey_button) Button mGetKeyButton;
    @Bind(R.id.api_key_edittext) EditText mApiKeyEditText;
    private Dialog mLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                //TODO Implement login logic
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
                    Log.d("LOGIN", "TOKEN: " + token);
                    mApiKeyEditText.setText(token);
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
}
