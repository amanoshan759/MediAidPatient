package patient.medical.dt.mediaid;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;

import patient.medical.dt.mediaid.beans.PatientBean;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] value = new String[]{"Male", "Female"};
    String[] value1 = new String[]{"O-", "O+", "A", "A+", "B", "B+", "AB-", "AB+"};
    private EditText etName, etUsername, etPassword, etPhoneNumber, etEmail, etDOB, etConfirmPassword;
    private TextView txtGender, txtBloodGroup, txtDob;
    private Button btnSignup;
    private boolean fgender = false, fdob = false, fblood = false, sign_up_flag = false;
    private int day, month, year;
    private Calendar cal;
    private FrameLayout frame1;
    private DatePickerDialog.OnDateSetListener on = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            txtDob.setText(String.valueOf(year + "-" + (month + 1) + "-" + dayOfMonth));
        }
    };

    private void init() {
        frame1 = (FrameLayout) findViewById(R.id.frame1);
        txtGender = (TextView) findViewById(R.id.txtGender);
        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        txtBloodGroup = (TextView) findViewById(R.id.txtBloodGroup);
        txtDob = (TextView) findViewById(R.id.txtDob);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
        txtDob.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        txtBloodGroup.setOnClickListener(this);
        cal = Calendar.getInstance();
        day = (cal.get(Calendar.DAY_OF_MONTH));
        month = (cal.get(Calendar.MONTH));
        year = (cal.get(Calendar.YEAR));

        txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(SignUpActivity.this);
                alertdialogbuilder.setTitle(" select Gender");
                alertdialogbuilder.setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(value).get(which);
                        txtGender.setText(selectedText);
                    }
                });
                AlertDialog dialog = alertdialogbuilder.create();
                dialog.show();
                fgender = true;
            }
        });

        txtBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(SignUpActivity.this);
                alertdialogbuilder.setTitle(" select BloodGroup");
                alertdialogbuilder.setItems(value1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(value1).get(which);
                        txtBloodGroup.setText(selectedText);
                    }
                });
                AlertDialog dialog = alertdialogbuilder.create();
                dialog.show();
                fblood = true;
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.this.finish();
            }
        });
        init();
        SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
        sign_up_flag = sp.getBoolean(SpUtility.KEY_SIGN_UP_FLAG, SpUtility.DEFAULT_VALUE_BOOLEAN);
        if (sign_up_flag) {
            String name = sp.getString(SpUtility.KEY_SIGN_UP_NAME, SpUtility.DEFAULT_VALUE_STRING);
            String username = sp.getString(SpUtility.KEY_SIGN_UP_EMAIL, SpUtility.DEFAULT_VALUE_STRING);
            etName.setText(name);
            etEmail.setText(username);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignup: {
                Utility.hideKeyboard(SignUpActivity.this);
                if (etName.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_name, v);
                    etName.requestFocus();
                } else if (etUsername.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_username, v);
                    etUsername.requestFocus();
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_password, v);
                    etPassword.requestFocus();
                } else if (etPassword.getText().toString().trim().length() < 6) {
                    Utility.showInSnackbar(R.string.snackbar_passwordlimit, v);
                    etPassword.requestFocus();
                } else if (etConfirmPassword.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_confirm_password, v);
                    etConfirmPassword.requestFocus();
                } else if (etPhoneNumber.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_phone, v);
                    etPhoneNumber.requestFocus();
                } else if (etEmail.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_email, v);
                    etEmail.requestFocus();
                } else if (!etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
                    Utility.showInSnackbar(R.string.snackbar_password, v);
                    etPassword.requestFocus();
                } else if (!Utility.isValidEmail(etEmail.getText().toString().trim())) {
                    Utility.showInSnackbar(R.string.snackbar_valid_email, v);
                    etEmail.requestFocus();
                } else if (!fdob) {
                    Utility.showInSnackbar(R.string.snackbar_dob, v);
                    txtDob.requestFocus();
                } else if (!fblood) {
                    Utility.showInSnackbar(R.string.snackbar_blood_group, v);
                    txtBloodGroup.requestFocus();
                } else if (!fgender) {
                    Utility.showInSnackbar(R.string.snackbar_gender, v);
                    txtGender.requestFocus();
                } else {
                    Utility.hideKeyboard(SignUpActivity.this);
                    String msg = Utility.internetCheck(getApplicationContext());
                    if (msg.equals("no internet")) {
                        Utility.showInSnackbar(R.string.nointernet, v);
                    } else {
                        frame1.setVisibility(View.VISIBLE);
                        signup(btnSignup);
                    }
                }
                break;
            }
            case R.id.txtDob: {
                fdob = true;
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                DatePickerDialog dpd = new DatePickerDialog(SignUpActivity.this, on, year, month, day);
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpd.show();
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.feedback) {
            return true;
        } else if (id == R.id.AboutUs) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signup(final View v) {
        PatientBean patientBean = new PatientBean();
        patientBean.setPatient_name(etName.getText().toString().trim());
        patientBean.setUser_name(etUsername.getText().toString().trim());
        patientBean.setUser_password(etPassword.getText().toString().trim());
        patientBean.setPatient_dob((txtDob.getText().toString().trim()));
        if (txtGender.getText().toString().trim().equals("Male")) {
            patientBean.setPatient_gender(true);
        } else {
            patientBean.setPatient_gender(false);
        }
        patientBean.setUser_email(etEmail.getText().toString().trim());
        patientBean.setUser_contact(etPhoneNumber.getText().toString().trim());
        patientBean.setPatient_bloodgroup(txtBloodGroup.getText().toString().trim());
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<UserBean>> call = userApi.patientSignUp(patientBean);
        call.enqueue(new Callback<ResultModel<UserBean>>() {
            @Override
            public void onResponse(Call<ResultModel<UserBean>> call, Response<ResultModel<UserBean>> response) {
                ResultModel<UserBean> r = response.body();
                UserBean userBean = r.getResult_data();
                if (r.getResult_code() > 0) {
                    String id = (String.valueOf(r.getResult_code()));
                    SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());
                    SharedPreferences.Editor e = sp.edit();
                    e.putString(SpUtility.KEY_PATIENT_NAME, etName.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_APPOINTMENT_ID, String.valueOf(userBean.getPatient_id()));
                    e.putString(SpUtility.KEY_PATIENT_USERNAME, etUsername.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_EMAIL, etEmail.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_CONTACT, etPhoneNumber.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_DOB, txtDob.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_GENDER, txtGender.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_BLOODGROUP, txtBloodGroup.getText().toString().trim());
                    e.putString(SpUtility.KEY_PATIENT_PASSWORD, etPassword.getText().toString().trim());
                    e.putString(SpUtility.KEY_USER_ID, String.valueOf(userBean.getUser_id()));
                    if (sp.getBoolean(SpUtility.KEY_SIGN_UP_FLAG, SpUtility.DEFAULT_VALUE_BOOLEAN)) {
                        e.putBoolean(SpUtility.KEY_FLAG_SIGN_SET, true);
                    }
                    e.commit();
                    Utility.startIntent(SignUpActivity.this, MainActivity.class);
                    SignUpActivity.this.finish();
                } else {
                    Utility.showInSnackbar(R.string.snackbar_signupfail, v);
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

