package patient.medical.dt.mediaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import patient.medical.dt.mediaid.beans.UserBean;
import patient.medical.dt.mediaid.util.ALLURL;
import patient.medical.dt.mediaid.util.RestClient;
import patient.medical.dt.mediaid.util.ResultModel;
import patient.medical.dt.mediaid.util.SpUtility;
import patient.medical.dt.mediaid.util.UserApi;
import patient.medical.dt.mediaid.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    private EditText etUsername, etPassword;
    private Button btnLogin, btnGoogle;
    private TextView txtCreateAccount, txtForgotPassword;
    private LinearLayout btnGuestUser;
    private GoogleApiClient mGoogleApiClient;
    private FrameLayout frame1;

    private void init() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGuestUser = (LinearLayout) findViewById(R.id.btnGuestUser);
        btnGoogle = (Button) findViewById(R.id.btnGoogle);
        txtCreateAccount = (TextView) findViewById(R.id.txtCreateAccount);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        frame1 = (FrameLayout) findViewById(R.id.frame1);
        btnLogin.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnGuestUser.setOnClickListener(this);
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean(SpUtility.KEY_SIGN_UP_FLAG, false);
                e.commit();
                Utility.startIntent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.finish();
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean(SpUtility.KEY_FLAG_CHANGE_PASSWORD, false);
                e.commit();
                Utility.startIntent(LoginActivity.this, ForgotPassword.class);
            }
        });

        if ((ActivityCompat.checkSelfPermission(LoginActivity.this, "android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(LoginActivity.this, "android.permission.ACCESS_NETWORK_STATE") != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"}, 1);
            return;
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                Utility.hideKeyboard(LoginActivity.this);
                if (etUsername.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_username, v);
                    etUsername.requestFocus();
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_password, v);
                    etPassword.requestFocus();
                } else {
                    String msg = Utility.internetCheck(getApplicationContext());
                    if (msg.equals("no internet")) {
                        Utility.showInSnackbar(R.string.nointernet, v);
                    } else {
                        frame1.setVisibility(View.VISIBLE);
                        login(btnLogin);
                    }
                }
                break;
            }
            case R.id.btnGoogle: {
                String msg = Utility.internetCheck(getApplicationContext());
                if (msg.equals("no internet")) {
                    Utility.showInSnackbar(R.string.nointernet, v);
                } else {
                    SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
                    SharedPreferences.Editor e = sp.edit();
                    e.putBoolean(SpUtility.KEY_SIGN_UP_FLAG, true);
                    e.commit();
                    signIn();
                }
                break;
            }
            case (R.id.btnGuestUser): {
                Utility.startIntent(LoginActivity.this, GuestExpertAdvice.class);
                break;
            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Utility.startIntent(LoginActivity.this, SignUpActivity.class);
            SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
            SharedPreferences.Editor e = sp.edit();
            e.putString("sign_up_name", acct.getDisplayName());
            e.putString("sign_up_email", acct.getEmail());
            e.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void login(final View v) {
        UserBean userBean = new UserBean();
        userBean.setUser_name(etUsername.getText().toString().trim());
        userBean.setUser_password(etPassword.getText().toString().trim());
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<UserBean>> call = userApi.authenticatePatient(userBean);
        call.enqueue(new Callback<ResultModel<UserBean>>() {
            @Override
            public void onResponse(Call<ResultModel<UserBean>> call, Response<ResultModel<UserBean>> response) {
                ResultModel<UserBean> objbean = response.body();
                if (objbean.getResult_code() == 1) {
                    UserBean userBean1 = objbean.getResult_data();
                    SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
                    SharedPreferences.Editor e = sp.edit();
                    e.putString(SpUtility.KEY_USER_ID, String.valueOf(userBean1.getUser_id()));
                    e.putString(SpUtility.KEY_PATIENT_APPOINTMENT_ID, String.valueOf(userBean1.getPatient_id()));
                    e.putString(SpUtility.KEY_PATIENT_USERNAME, etUsername.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_NAME, userBean1.getUser_name());
                    e.putString(SpUtility.KEY_PATIENT_EMAIL, userBean1.getUser_email());
                    e.putString(SpUtility.KEY_PATIENT_CONTACT, userBean1.getUser_contact());
                    e.putString(SpUtility.KEY_PATIENT_PASSWORD, userBean1.getUser_password());
                    e.putString(SpUtility.KEY_PATIENT_PASSWORD, etPassword.getText().toString().trim());
                    e.commit();
                    Utility.startIntent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.finish();
                } else {
                    Utility.showInSnackbar(R.string.snackbar_loginfailed, v);
                }
                frame1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultModel<UserBean>> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
    }
}
