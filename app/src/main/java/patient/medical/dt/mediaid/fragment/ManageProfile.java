package patient.medical.dt.mediaid.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import patient.medical.dt.mediaid.MainActivity;
import patient.medical.dt.mediaid.R;
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

public class ManageProfile extends Fragment {

    final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            txtDob.setText(String.valueOf(year + "-" + (month + 1) + "-" + dayOfMonth));
        }
    };
    int day, month, year;
    String[] value = new String[]{"Male", "Female"};
    private EditText etName, etEmail, etPhone;
    private Button btnSave;
    private TextView txtGender, txtDob;
    private Calendar cal;
    private String msg = null;
    private ImageView imagePerson, imageCamera;
    private FrameLayout frame1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_profile, container, false);
        init(view);
        return view;
    }

    public void init(View v) {
        frame1 = (FrameLayout) v.findViewById(R.id.frame1);
        btnSave = (Button) v.findViewById(R.id.btnSave);
        etName = (EditText) v.findViewById(R.id.etName);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        txtDob = (TextView) v.findViewById(R.id.txtDob);
        txtGender = (TextView) v.findViewById(R.id.txtGender);
        imagePerson = (ImageView) v.findViewById(R.id.imagePerson);
        imageCamera = (ImageView) v.findViewById(R.id.imageCamera);
        cal = Calendar.getInstance();
        day = cal.get(java.util.Calendar.DAY_OF_MONTH);
        month = cal.get(java.util.Calendar.MONTH);
        year = cal.get(java.util.Calendar.YEAR);
        msg = Utility.internetCheck(getActivity());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msg.equals("no internet")) {
                    Utility.showInSnackbar(R.string.nointernet, v);
                } else {
                    frame1.setVisibility(View.VISIBLE);
                    updatePatientProfile(btnSave);
                }
            }
        });
        if (msg.equals("no internet")) {
            Utility.showInSnackbar(R.string.nointernet, v);
        } else {
            frame1.setVisibility(View.VISIBLE);
            getProfile();
        }

    }

    public void getProfile() {
        UserBean userBean = new UserBean();
        SharedPreferences sp = SpUtility.getSharedPreference(getActivity());
        String id = sp.getString(SpUtility.KEY_USER_ID, SpUtility.DEFAULT_VALUE_STRING);
        userBean.setUser_id(Integer.parseInt(id));
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<PatientBean>> call = userApi.getProfile(userBean);
        call.enqueue(new Callback<ResultModel<PatientBean>>() {
            @Override
            public void onResponse(Call<ResultModel<PatientBean>> call, Response<ResultModel<PatientBean>> response) {
                ResultModel<PatientBean> resultmodel = response.body();
                PatientBean patientBean = resultmodel.getResult_data();
                etName.setText(patientBean.getPatient_name());
                etEmail.setText(patientBean.getUser_email());
                etPhone.setText(patientBean.getUser_contact());
                txtDob.setText(patientBean.getPatient_dob());
                if (patientBean.isPatient_gender()) {
                    txtGender.setText("Male");
                } else {
                    txtGender.setText("Female");
                }
                txtGender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getActivity());
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
                    }
                });
                txtDob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(getActivity(), onDateSetListener, day, month, year);
                        dpd.getDatePicker().setMaxDate(new Date().getTime());
                        dpd.show();
                    }
                });
                frame1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultModel<PatientBean>> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
    }

    public void updatePatientProfile(final View v) {
        PatientBean patientBean = new PatientBean();
        SharedPreferences sp = SpUtility.getSharedPreference(getActivity());
        int id = Integer.parseInt(sp.getString(SpUtility.KEY_USER_ID, SpUtility.DEFAULT_VALUE_STRING));
        patientBean.setUser_id(id);
        patientBean.setPatient_name(etName.getText().toString().trim());
        patientBean.setUser_email(etEmail.getText().toString().trim());
        patientBean.setUser_contact(etPhone.getText().toString().trim());
        if (txtGender.getText().toString().trim().equalsIgnoreCase("Male")) {
            patientBean.setPatient_gender(true);
        } else {
            patientBean.setPatient_gender(false);
        }
        patientBean.setPatient_dob(txtDob.getText().toString().trim());
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> call = userApi.updatePatientProfile(patientBean);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel r = response.body();
                if (r.getResult_code() > 0) {
                    Utility.startIntent(getActivity(), MainActivity.class);
                    Utility.showInSnackbar(R.string.snackbar_profileupdated, v);
                } else {
                    Utility.showInSnackbar(R.string.snackbar_updateFail, v);
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