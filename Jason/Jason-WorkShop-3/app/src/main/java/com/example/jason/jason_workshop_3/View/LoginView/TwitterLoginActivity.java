package com.example.jason.jason_workshop_3.View.LoginView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PTwitterLogin;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String PREF_NAME = "sample_twitter_pref";
    public static final String PREF_USER_NAME = "twitter_user_name";
    public static final int WEBVIEW_REQUEST_CODE = 100;
    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";

    TextView tvUsername = null;
    RelativeLayout login_btn = null, start_main_btn = null, logout_btn = null;
    LinearLayout twitter_welcome_layout = null, twitter_login_layout = null;

    private boolean doubleBackToExitPressedOnce = false;
    private String mConsumerKey = null;
    private String mConsumerSecret = null;
    private String mCallbackUrl = null;
    private String mAuthVerifier = null;
    private String mTwitterVerifier = null;
    private Twitter mTwitter = null;
    private RequestToken mRequestToken = null;
    private SharedPreferences mSharedPreferences = null;
    private PTwitterLogin twitterLogin;
    private boolean logout = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_twitter_login);
        tvUsername = (TextView) findViewById(R.id.textView_username);
        login_btn = (RelativeLayout) findViewById(R.id.login_layout);
        logout_btn = (RelativeLayout) findViewById(R.id.logout_layout);
        start_main_btn = (RelativeLayout) findViewById(R.id.start_main_layout);
        twitter_welcome_layout = (LinearLayout) findViewById(R.id.layout_twitter_welcome);
        twitter_login_layout = (LinearLayout) findViewById(R.id.layout_twitter_login);
        initSDK();
        login_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);
        start_main_btn.setOnClickListener(this);

        if (getIntent().getStringExtra("Intent").equals("Login")) logout = true;
    }

    @Override
    public void onClick(View v) {
        Utils utils = new Utils();
        switch (v.getId()){
            case R.id.login_layout:
                if (utils
                        .isNetworkConnected(TwitterLoginActivity.this) == false) {
                    showAlertBox();
                } else {
                    mSharedPreferences = getSharedPreferences(PREF_NAME, 0);
                    if (!isAuthenticated()) {
                        loginToTwitter();
                    }
                }
                break;
            case  R.id.logout_layout:
                showLogoutAlertDialog();
                break;
            case R.id.start_main_layout:
                startImproveHealth();
                break;

        }
    }

    private void logoutFromTwitter() {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.remove(PREF_KEY_OAUTH_TOKEN);
        e.remove(PREF_KEY_OAUTH_SECRET);
        e.remove(PREF_KEY_TWITTER_LOGIN);
        e.remove(PREF_USER_NAME);
        e.commit();

        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();


        twitter_welcome_layout.setVisibility(View.GONE);
        twitter_login_layout.setVisibility(View.VISIBLE);

    }


    ///TwitterAuthentication
    public void initSDK() {
        // this.mTwitterAuth = auth;
        mConsumerKey = getResources().getString(R.string.twitter_consumer_key);
        mConsumerSecret = getResources().getString(R.string.twitter_consumer_secret);
        mAuthVerifier = "oauth_verifier";

        if (TextUtils.isEmpty(mConsumerKey)
                || TextUtils.isEmpty(mConsumerSecret)) {
            return;
        }

        mSharedPreferences = getSharedPreferences(PREF_NAME, 0);
        if (isAuthenticated()) {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            //hide login button here and show tweet
            twitter_welcome_layout.setVisibility(View.VISIBLE);
            twitter_login_layout.setVisibility(View.GONE);
            mSharedPreferences.getString(PREF_USER_NAME, "");
            tvUsername.setText(mSharedPreferences.getString(PREF_USER_NAME, ""));
        } else {
            twitter_welcome_layout.setVisibility(View.GONE);
            twitter_login_layout.setVisibility(View.VISIBLE);
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(mCallbackUrl)) {
                String verifier = uri.getQueryParameter(mAuthVerifier);
                try {
                    AccessToken accessToken = mTwitter.getOAuthAccessToken(
                            mRequestToken, verifier);
                    saveTwitterInformation(accessToken);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    Log.d("Failed to login ",
                            e.getMessage());
                }
            }
        }
    }

    protected boolean isAuthenticated() {
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    private void saveTwitterInformation(AccessToken accessToken) {
        long userID = accessToken.getUserId();
        User user;
        try {
            user = mTwitter.showUser(userID);
            String username = user.getName();
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
            e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
            e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
            e.putString(PREF_USER_NAME, username);
            e.commit();

        } catch (TwitterException e1) {
            Log.d("Failed to Save", e1.getMessage());
        }
    }

    private void loginToTwitter() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(
                PREF_KEY_TWITTER_LOGIN, false);

        if (!isLoggedIn) {
            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(mConsumerKey);
            builder.setOAuthConsumerSecret(mConsumerSecret);

            final Configuration configuration = builder.build();
            final TwitterFactory factory = new TwitterFactory(configuration);
            mTwitter = factory.getInstance();
            try {
                mRequestToken = mTwitter.getOAuthRequestToken(mCallbackUrl);
                startWebAuthentication();
            } catch (TwitterException e) {
                e.printStackTrace();
                Log.d("FA", "FA");
            }
        }
    }

    protected void startWebAuthentication() {
        final Intent intent = new Intent(TwitterLoginActivity.this,
                TwitterAuthenticationActivity.class);
        intent.putExtra(TwitterAuthenticationActivity.EXTRA_URL,
                mRequestToken.getAuthenticationURL());
        startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null)
            mTwitterVerifier = data.getExtras().getString(mAuthVerifier);

        AccessToken accessToken;
        try {
            accessToken = mTwitter.getOAuthAccessToken(mRequestToken,
                    mTwitterVerifier);
            long userID = accessToken.getUserId();
            final User user = mTwitter.showUser(userID);
            String username = user.getName();
            tvUsername.setText(username);
            logout = false;
            twitter_welcome_layout.setVisibility(View.VISIBLE);
            twitter_login_layout.setVisibility(View.GONE);
            saveTwitterInformation(accessToken);
        } catch (Exception e) {
        }
    }

    private void showAlertBox() {

        AlertDialog malertDialog = null;
        AlertDialog.Builder mdialogBuilder = null;
        if (mdialogBuilder == null) {
            mdialogBuilder = new AlertDialog.Builder(TwitterLoginActivity.this);

            mdialogBuilder.setTitle("Alert");
            mdialogBuilder.setMessage("No Network");

            mdialogBuilder.setPositiveButton("Enable",
                    new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            // launch setting Activity
                            startActivityForResult(new Intent(
                                            android.provider.Settings.ACTION_SETTINGS),
                                    0);
                        }
                    });

            mdialogBuilder.setNegativeButton(android.R.string.no,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert);

            if (malertDialog == null) {
                malertDialog = mdialogBuilder.create();
                malertDialog.show();
            }

        }

    }

    public void startImproveHealth(){
        twitterLogin = new PTwitterLogin(TwitterLoginActivity.this, tvUsername.getText().toString());
        twitterLogin.checkTwitterUser();
    }

    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }

    public void startUserMain(){
        startActivities(UserMainActivity.class, "Twitter");
    }

    public void startFirstCheckBMI(){
        startActivities(MonthlyCheckBMIActivity.class, "1");
    }

    public void showLogoutAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

//      Fix Bug ID: JS_012 - (Title should be “Warning” and content should be “Do you want to log out?”)

        alertDialogBuilder.setTitle("Warning!");
        alertDialogBuilder
                .setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id) {
                        twitterLogin = new PTwitterLogin(TwitterLoginActivity.this, tvUsername.getText().toString());
                        twitterLogin.logoutTwitter();
                        logoutFromTwitter();
                        logout = true;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (!logout) {
            if (doubleBackToExitPressedOnce) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }else startActivities(LoginActivity.class, "none");
    }

}
