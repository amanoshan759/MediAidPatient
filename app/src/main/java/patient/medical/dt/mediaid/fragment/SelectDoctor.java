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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import patient.medical.dt.mediaid.R;
import patient.medical.dt.mediaid.beans.AppointmentBean;
import patient.medical.dt.mediaid.beans.DoctorBean;
import patient.medical.dt.mediaid.beans.HospitalBean;
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

public class SelectDoctor extends Fragment implements View.OnClickListener {


    int day, month, year;
    Calendar cal;
    int iddoctor = 0, idhospital = 0;
    boolean flagdoctor = false, flaghospital = false, flagtime = false, flagdate = false;
    private TextView txtHospital;
    private TextView txtDoctor, txtTime, txtDate;
    private Button btnSubmit;
    private String[] time = new String[]{"10:00:00", "12:00:00", "02:00:00", "04:00:00", "06:00:00"};
    private FrameLayout frame1;
    private DatePickerDialog.OnDateSetListener on = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            if (dayOfMonth == Calendar.SUNDAY) {
                Utility.showInSnackbar(R.string.snackbar_datenotavailabe, datePicker);
                txtDate.setText("Choose another date");
            }
            txtDate.setText(String.valueOf(year + "-" + (month + 1) + "-" + dayOfMonth));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_doctor, container, false);
        init(view);
        return view;
    }

    void init(View v) {
        txtHospital = (TextView) v.findViewById(R.id.txtHospital);
        txtDoctor = (TextView) v.findViewById(R.id.txtDoctor);
        txtDate = (TextView) v.findViewById(R.id.txtDate);
        txtTime = (TextView) v.findViewById(R.id.txtTime);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        frame1 = (FrameLayout) v.findViewById(R.id.frame1);
        txtTime.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        cal = Calendar.getInstance();
        day = (cal.get(Calendar.DAY_OF_MONTH));
        month = (cal.get(Calendar.MONTH));
        year = (cal.get(Calendar.YEAR));
        String msg = Utility.internetCheck(getActivity());
        if (msg.equals("no internet")) {
            Utility.showInSnackbar(R.string.nointernet, v);
        } else {
            frame1.setVisibility(View.VISIBLE);
            getHospital(v);
        }
    }

    public void getHospital(final View v) {
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<ArrayList<HospitalBean>>> call = userApi.getHospital();
        call.enqueue(new Callback<ResultModel<ArrayList<HospitalBean>>>() {
            @Override
            public void onResponse(Call<ResultModel<ArrayList<HospitalBean>>> call, Response<ResultModel<ArrayList<HospitalBean>>> response) {
                ResultModel<ArrayList<HospitalBean>> r = response.body();
                ArrayList<HospitalBean> al = r.getResult_data();
                int size = al.size();
                final String[] hospital = new String[size];
                final int[] hospitalid = new int[size];
                for (int i = 0; i < al.size(); i++) {
                    HospitalBean appointmentBean1 = al.get(i);
                    appointmentBean1.getHospital_name();
                    hospital[i] = appointmentBean1.getHospital_name();
                    hospitalid[i] = appointmentBean1.getHospital_id();
                    txtHospital.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getActivity());
                            alertdialogbuilder.setTitle("Select Hospital");
                            alertdialogbuilder.setItems(hospital, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String selectedText = Arrays.asList(hospital).get(which);
                                    txtHospital.setText(selectedText);
                                    idhospital = hospitalid[which];
                                    String msg = Utility.internetCheck(getActivity());
                                    if (msg.equals("no internet")) {
                                        Utility.showInSnackbar(R.string.nointernet, v);
                                    } else {
                                        frame1.setVisibility(View.VISIBLE);
                                        getDoctor(view);
                                    }

                                    flaghospital = true;
                                    System.out.println(idhospital + "----------------hid----");
                                }
                            });
                            AlertDialog dialog = alertdialogbuilder.create();
                            dialog.show();
                        }
                    });
                    frame1.setVisibility(View.GONE);
                }

            }

            public void onFailure(Call<ResultModel<ArrayList<HospitalBean>>> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
    }

    public void getDoctor(View v) {
        DoctorBean doctorBean = new DoctorBean();
        System.out.println(idhospital + "+++++++++++++++++++++++++");
        doctorBean.setHospital_id(idhospital);
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<ArrayList<DoctorBean>>> call = userApi.getDoctors(doctorBean);
        call.enqueue(new Callback<ResultModel<ArrayList<DoctorBean>>>() {
            @Override
            public void onResponse(Call<ResultModel<ArrayList<DoctorBean>>> call, Response<ResultModel<ArrayList<DoctorBean>>> response) {
                ResultModel<ArrayList<DoctorBean>> r = response.body();
                ArrayList<DoctorBean> al = r.getResult_data();
                int size = al.size();
                final String[] doctor = new String[size];
                final int[] doctorid = new int[size];
                for (int i = 0; i < al.size(); i++) {
                    DoctorBean doctorBean = al.get(i);
                    doctorBean.getDoctor_name();
                    doctor[i] = doctorBean.getDoctor_name();
                    doctorid[i] = doctorBean.getDoctor_id();
                    txtDoctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getActivity());
                            alertdialogbuilder.setTitle("Select Doctor");
                            alertdialogbuilder.setItems(doctor, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String selectedText = Arrays.asList(doctor).get(which);
                                    txtDoctor.setText(selectedText);
                                    iddoctor = doctorid[which];
                                    SharedPreferences sp = SpUtility.getSharedPreference(getActivity());
                                    SharedPreferences.Editor e = sp.edit();
                                    e.putInt(SpUtility.KEY_DOCTOR_ID, iddoctor);
                                    e.commit();
                                    flagdoctor = true;
                                    System.out.println(iddoctor + "---------------idoc----------------");
                                }
                            });
                            AlertDialog dialog = alertdialogbuilder.create();
                            dialog.show();
                        }
                    });
                }
                frame1.setVisibility(View.GONE);

            }

            public void onFailure(Call<ResultModel<ArrayList<DoctorBean>>> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
    }

    //
    public void setAppointment(final View v) {
        AppointmentBean appointmentBean = new AppointmentBean();
        appointmentBean.setAppointment_time(txtTime.getText().toString().trim());
        appointmentBean.setAppointment_date(txtDate.getText().toString().trim());
        appointmentBean.setDoctor_id(iddoctor);
        appointmentBean.setHospital_id(idhospital);
        SharedPreferences sp = SpUtility.getSharedPreference(getActivity());
        int userid = Integer.parseInt(sp.getString(SpUtility.KEY_USER_ID, SpUtility.DEFAULT_VALUE_STRING));
        appointmentBean.setUser_id(userid);
        String patientid = sp.getString(SpUtility.KEY_PATIENT_APPOINTMENT_ID, SpUtility.DEFAULT_VALUE_STRING);
        appointmentBean.setPatient_id(Integer.parseInt(patientid));
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> call = userApi.setAppointment(appointmentBean);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel r = response.body();
                if (r.getResult_code() == 1 && r.getMsg().equals("success")) {
                    txtHospital.setText("");
                    txtDoctor.setText("");
                    txtTime.setText("");
                    txtDate.setText("");
                    Toast.makeText(getActivity(), "Appointment Set", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Appointment not Set", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit: {
                Utility.hideKeyboard(getActivity());
                if (!flaghospital) {
                    Utility.showInSnackbar(R.string.snackbar_selecthospital, v);
                } else if (!flagdoctor) {
                    Utility.showInSnackbar(R.string.snackbar_selectdoctor, v);
                } else if (!flagdate) {
                    Utility.showInSnackbar(R.string.snackbar_selectdate, v);
                } else if (!flagtime) {
                    Utility.showInSnackbar(R.string.snackbar_selecttime, v);
                } else {
                    String msg = Utility.internetCheck(getActivity());
                    if (msg.equals("no internet")) {
                        Utility.showInSnackbar(R.string.nointernet, v);
                    } else {
                        setAppointment(v);
                    }
                }
                break;
            }
            case R.id.txtTime: {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getActivity());
                alertdialogbuilder.setTitle("Select Time");
                alertdialogbuilder.setItems(time, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(time).get(which);
                        txtTime.setText(selectedText);
                    }
                });
                AlertDialog dialog = alertdialogbuilder.create();
                dialog.show();
                flagtime = true;
                break;
            }
            case R.id.txtDate: {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), on, year, month, day);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
                flagdate = true;
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
