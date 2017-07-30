package patient.medical.dt.mediaid.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import patient.medical.dt.mediaid.Appointments_Adapter;
import patient.medical.dt.mediaid.R;
import patient.medical.dt.mediaid.beans.AppointmentBean;
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


public class Appointments extends Fragment {

    public static ArrayList al;
    private RecyclerView recycleView;
    private Appointments_Adapter ca;
    private String id;
    private FrameLayout frame1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        init(view);
        return view;
    }

    void init(View v) {
        recycleView = (RecyclerView) v.findViewById(R.id.recycleView);
        frame1 = (FrameLayout) v.findViewById(R.id.frame1);
        Utility.hideKeyboard(getActivity());
        String msg = Utility.internetCheck(getActivity());
        if (msg.equals("no internet")) {
            Toast.makeText(getActivity(), "No Interent", Toast.LENGTH_SHORT).show();
        } else {
            getAppointments();
            frame1.setVisibility(View.VISIBLE);
        }
    }

    public void getAppointments() {
        AppointmentBean appointmentBean = new AppointmentBean();
        SharedPreferences s = SpUtility.getSharedPreference(getActivity());
        String id = s.getString(SpUtility.KEY_USER_ID, SpUtility.DEFAULT_VALUE_STRING);
        System.out.println(id + "------------------");
        appointmentBean.setUser_id((Integer.parseInt(id)));
        appointmentBean.setUser_type("patient");
        SharedPreferences sp = SpUtility.getSharedPreference(getActivity());
        int p = sp.getInt(SpUtility.KEY_DOCTOR_ID, SpUtility.DEFAULT_VALUE_INT);
        System.out.println(p + "+++++++++++++++++++++++++++++++++++++++==");
        appointmentBean.setDoctor_id(sp.getInt(SpUtility.KEY_DOCTOR_ID, SpUtility.DEFAULT_VALUE_INT));
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<ArrayList<AppointmentBean>>> call = userApi.getAppointment(appointmentBean);
        call.enqueue(new Callback<ResultModel<ArrayList<AppointmentBean>>>() {
            @Override
            public void onResponse(Call<ResultModel<ArrayList<AppointmentBean>>> call, Response<ResultModel<ArrayList<AppointmentBean>>> response) {
                ResultModel<ArrayList<AppointmentBean>> r = response.body();
                System.out.println("=========================================+" + r.getResult_data());
                ArrayList<AppointmentBean> al = r.getResult_data();
                int id = 0;
                for (int i = 0; i < al.size(); i++) {
                    AppointmentBean appointmentBean1 = al.get(i);
                    id = appointmentBean1.getPatient_id();
                    System.out.println(appointmentBean1.getPatient_id() + "patient");
                }
//                SharedPreferences sp=SpUtility.getSharedPreference(getActivity());
//                SharedPreferences.Editor e=sp.edit();
//                e.putString(SpUtility.KEY_PATIENT_APPOINTMENT_ID,(String.valueOf()id);
//                e.commit();
                System.out.println(al.size() + "++++++++++++++++");
                ca = new Appointments_Adapter(al);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
                recycleView.setLayoutManager(lm);
                recycleView.setAdapter(ca);
                frame1.setVisibility(View.GONE);
            }

            public void onFailure(Call<ResultModel<ArrayList<AppointmentBean>>> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });

    }
}