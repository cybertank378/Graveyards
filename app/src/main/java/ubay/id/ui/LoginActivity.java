package ubay.id.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ubay.id.R;
import ubay.id.app.AppConfig;
import ubay.id.app.AppController;
import ubay.id.helper.SQLiteHandler;
import ubay.id.helper.SessionManager;
import ubay.id.model.UserData;
import ubay.id.service.ConnectionDetector;
import ubay.id.service.WebService;

/**
 * Created              : Rahman on 9/6/2016.
 * Date Created         : 9/6/2016 / 12:26 PM.
 * ===================================================
 * Package              : ubay.id.
 * Project Name         : Graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private Button mLogin;
    private ProgressDialog pDialog;
    private Snackbar mSnackBar;
    private Activity mActivity;
    private Intent mIntent;
    private CoordinatorLayout mCoordinatorLayout;
    private TextInputLayout floatEmailText, floatPasswordText;
    private SessionManager mSession;
    private SQLiteHandler mDb;
    private UserData mUserData;
    private String dialogTitle, dialogMsg;
    private String mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        mActivity = this;
        setContentView (R.layout.activity_login);
        initView();
        initClickListener();
        setDataDummy();
        mDb = new SQLiteHandler(getApplicationContext());

        // Session manager
        mSession = new SessionManager(getApplicationContext());
        if (mSession.isLoggedIn()){
            mIntent =  new Intent(this, DashboardActivity.class);
            startActivity(mIntent);
            finish();
        }
    }

    private void initView() {
        emailInput              = (TextInputEditText) findViewById (R.id.emailEdt);
        passwordInput           = (TextInputEditText) findViewById (R.id.passwordEdt);
        floatEmailText          = (TextInputLayout) findViewById (R.id.floatEmailText);
        floatPasswordText       = (TextInputLayout) findViewById (R.id.floatPasswordText);
        mLogin                  = (Button) findViewById (R.id.btn_login);
        mCoordinatorLayout      = (CoordinatorLayout) findViewById (R.id.coordinatorLayout);

        setupFloatingLabelError();

    }


    private void initClickListener() {

        mLogin.setOnClickListener (mLoginClickListener());
    }

    private View.OnClickListener mLoginClickListener() {
        return  new View.OnClickListener ( ) {

            @Override
            public void onClick(View view) {
                mEmail = emailInput.getText ().toString ();
                Log.d("DEBUG", "onClick: " + mEmail);
                mPassword = passwordInput.getText ().toString ();
                Log.d("DEBUG", "onClick: " + mPassword);
                if (!mEmail.isEmpty() && !mPassword.isEmpty()){
                    sendRequest();
                }else{
                    mSnackBar = Snackbar.make (mCoordinatorLayout, "Please Check your login credentials is correct!!", Snackbar.LENGTH_LONG);
                    mSnackBar.show ();

                }

            }
        };
    }




    private void checkLogin(final String mEmail, final String mPassword) {
        String tag_string_req = "req_login";
        showDialog();
        final StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                closeDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean err = jObj.getBoolean("error");
                    if (!err) {
                        mSession.setLogin(true);
                        JSONObject uObj = jObj.getJSONObject("data");
                        mDb = new SQLiteHandler(getApplicationContext());

                        mUserData = mDb.getUserData();
                        if (mUserData == null) {
                            mUserData = new UserData();
                            mUserData.setUid(uObj.getInt("user_id"));
                            mUserData.setUname(uObj.getString("user_login"));
                            mUserData.setEmail(uObj.getString("user_email"));
                            mDb.addUser(mUserData);
                            mDb.close();

                            Intent mIntent = new Intent(mActivity, DashboardActivity.class);
                            startActivity(mIntent);
                            finish();
                        } else {
                            String errMsg = jObj.getString("error_msg");
                            Snackbar snackbar = Snackbar
                                    .make(mCoordinatorLayout, errMsg, Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errMsg = "Upss,.. \nMaaf pastikan alamat email dan password anda benar!";
                Snackbar snackbar = Snackbar
                        .make(mCoordinatorLayout, errMsg, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", mEmail);
                params.put("pass", mPassword);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void sendRequest() {
        if (ConnectionDetector.isConnectionAvailable(mActivity)) {
            checkLogin(mEmail, mPassword);
        } else {

            Log.d("DEBUG", "Kirim data");

        }
    }


    private void setupFloatingLabelError() {
        floatEmailText.getEditText ().addTextChangedListener (new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length () > 0 && charSequence.length () == 0 ){
                    floatEmailText.setError (getString (R.string.username_required));
                    floatEmailText.setErrorEnabled (true);
                } else {
                    floatEmailText.setErrorEnabled (false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        floatPasswordText.getEditText ().addTextChangedListener (new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length () > 0 && charSequence.length () <= 6 ){
                    floatPasswordText.setError (getString (R.string.password_required));
                    floatPasswordText.setErrorEnabled (true);
                } else {
                    floatPasswordText.setErrorEnabled (false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }




    public void showDialog(){
        String strMsg = "Please wait, checking user data..";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(strMsg);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void closeDialog(){
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    private void setDataDummy(){
        emailInput.setText("cybertank378@gmail.com");
        passwordInput.setText("admin");
    }


}
