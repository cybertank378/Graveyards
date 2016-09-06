package ubay.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    private ProgressBar mPbar;
    private FirebaseAuth mAuth;
    private Snackbar mSnackBar;
    private Activity mActivity;
    private Intent mIntent;
    private CoordinatorLayout mCoordinatorLayout;
    private TextInputLayout floatEmailText, floatPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        initFirebase();
        mActivity = this;
        setContentView (R.layout.activity_login);
        initView();
        initClickListener();
    }

    private void initClickListener() {

        mLogin.setOnClickListener (mLoginClickListener());
    }

    private View.OnClickListener mLoginClickListener() {
        return  new View.OnClickListener ( ) {

            @Override
            public void onClick(View view) {
                final String mEmail = emailInput.getText ().toString ();
                final String mPassword = passwordInput.getText ().toString ();
                if (TextUtils.isEmpty (mEmail) && TextUtils.isEmpty (mPassword)){
                    mSnackBar = Snackbar.make (mCoordinatorLayout, "Please Check your login credentials is correct !!", Snackbar.LENGTH_LONG);
                    mSnackBar.show ();
                }

                mPbar.setVisibility (View.VISIBLE);
                mAuth.signInWithEmailAndPassword (mEmail, mPassword).addOnCompleteListener (mActivity, new OnCompleteListener<AuthResult> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mPbar.setVisibility (View.GONE);
                        if (!task.isSuccessful ()){
                            if (mPassword.length () < 6 && mEmail !=null ){
                                emailInput.getText().clear();
                                emailInput.setError(getString(R.string.not_valid));
                                passwordInput.getText().clear();
                                passwordInput.setError (getString (R.string.minimum_password));
                            } else {
                                mSnackBar = Snackbar.make (mCoordinatorLayout, getString(R.string.auth_failed), Snackbar.LENGTH_LONG);
                                mSnackBar.show ();
                            }
                        } else {
                            mIntent = new Intent (mActivity, MainActivity.class);
                            startActivity (mIntent);
                            finish ();
                        }
                    }
                });

            }
        };
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        if(FirebaseUtil.getCurrentUserId () != null){
            startActivity (new Intent (this, MainActivity.class));
            finish ();
        }
    }

    private void initView() {
        emailInput            = (TextInputEditText) findViewById (R.id.emailEdt);
        passwordInput         = (TextInputEditText) findViewById (R.id.passwordEdt);
        floatEmailText      = (TextInputLayout) findViewById (R.id.floatEmailText);
        floatPasswordText   = (TextInputLayout) findViewById (R.id.floatPasswordText);
        mLogin              = (Button) findViewById (R.id.btn_login);
        mCoordinatorLayout  = (CoordinatorLayout) findViewById (R.id.coordinatorLayout);
        mPbar               = (ProgressBar) findViewById (R.id.progressBar);
        setupFloatingLabelError();

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


}
