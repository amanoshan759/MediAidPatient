package patient.medical.dt.mediaid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import patient.medical.dt.mediaid.beans.PasswordBean;
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

public class ChangePassowrd extends AppCompatActivity implements Button.OnClickListener {

    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private Button btnSave, btnCancel;
    private FrameLayout frame1;
    private TextView txtCurPass;
    private boolean change_password_flag = false;

    private void init() {
        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtCurPass = (TextView) findViewById(R.id.txtCurPass);
        frame1 = (FrameLayout) findViewById(R.id.frame1);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passowrd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
        SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
        change_password_flag = sp.getBoolean(SpUtility.KEY_FLAG_CHANGE_PASSWORD, SpUtility.DEFAULT_VALUE_BOOLEAN);
        if (!change_password_flag) {
            etCurrentPassword.setVisibility(View.GONE);
            txtCurPass.setVisibility(View.GONE);
        } else {
            etCurrentPassword.setVisibility(View.VISIBLE);
            txtCurPass.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave: {
                Utility.hideKeyboard(ChangePassowrd.this);
                if (etNewPassword.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_new_password, v);
                    etNewPassword.requestFocus();
                } else if (etNewPassword.getText().toString().trim().length() < 6) {
                    Utility.showInSnackbar(R.string.snackbar_passwordlimit, v);
                    etNewPassword.requestFocus();
                } else if (etConfirmPassword.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_confirm_password, v);
                    etConfirmPassword.requestFocus();
                } else if (!etNewPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
                    Utility.showInSnackbar(R.string.snackbar_passwordnotmatch, v);
                } else {
                    String msg = Utility.internetCheck(getApplicationContext());
                    if (msg.equals("no internet")) {
                        Utility.showInSnackbar(R.string.nointernet, v);
                    } else {
                        frame1.setVisibility(View.VISIBLE);
                        if (change_password_flag) {
                            changePassword(btnSave);
                        } else {
                            resetPassword(btnSave);
                        }
                    }
                }
                break;
            }
            case R.id.btnCancel: {
                Utility.startIntent(ChangePassowrd.this, LoginActivity.class);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_changepassword, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.feedback) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void changePassword(final View v) {
        PasswordBean passwordBean = new PasswordBean();
        passwordBean.setCurrent_password(etCurrentPassword.getText().toString().trim());
        passwordBean.setNew_password(etNewPassword.getText().toString().trim());
        SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
        String id = sp.getString(SpUtility.KEY_USER_ID, SpUtility.DEFAULT_VALUE_STRING);
        passwordBean.setUser_id(Integer.parseInt(id));
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> call = userApi.changePassword(passwordBean);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel r = response.body();
                if (r.getMsg().equals("success") && r.getResult_code() == 1) {
                    Utility.startIntent(ChangePassowrd.this, MainActivity.class);
                    ChangePassowrd.this.finish();
                } else {
                    Utility.showInSnackbar(R.string.snackbar_tryAgain, v);
                }
                frame1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
    }

    public void resetPassword(final View v) {
        UserBean userBean = new UserBean();
        userBean.setUser_password(etNewPassword.getText().toString().trim());
        SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
        String id = sp.getString(SpUtility.KEY_USER_ID_FORGOT, SpUtility.DEFAULT_VALUE_STRING);
        userBean.setUser_id(Integer.parseInt(id));
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> call = userApi.resetPasssword(userBean);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel r = response.body();
                if (r.getMsg().equals("success") && r.getResult_code() == 1) {
                    Utility.startIntent(ChangePassowrd.this, LoginActivity.class);
                    ChangePassowrd.this.finish();
                } else {
                    Utility.showInSnackbar(R.string.snackbar_tryAgain, v);
                }
                frame1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
    }
}
