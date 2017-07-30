package patient.medical.dt.mediaid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import patient.medical.dt.mediaid.beans.GuestLabReportBean;
import patient.medical.dt.mediaid.util.ALLURL;
import patient.medical.dt.mediaid.util.RestClient;
import patient.medical.dt.mediaid.util.ResultModel;
import patient.medical.dt.mediaid.util.UserApi;
import patient.medical.dt.mediaid.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GuestExpertAdvice extends AppCompatActivity {

    EditText txt1_2, txt2_2, txt3_2, txt4_2, txt5_2, txt6_2, txt7_2, txt8_2, txt9_2, txt10_2, txt11_2, txt12_2, txt13_2, txt14_2, txt15_2, txt16_2, txt17_2, txt18_2, txt19_2, txt20_2, txt21_2, txt22_2, txt23_2, txt24_2, txt25_2, txt26_2;
    private GuestLabReportBean guestLabReportBean;
    private FrameLayout frame1;
    private String disease = null;
    private Button btnSeeExpertAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_expert_advice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        guestLabReportBean = new GuestLabReportBean();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {
        frame1 = (FrameLayout) findViewById(R.id.frame1);
        txt1_2 = (EditText) findViewById(R.id.txt1_2);
        txt2_2 = (EditText) findViewById(R.id.txt2_2);
        txt3_2 = (EditText) findViewById(R.id.txt3_2);
        txt4_2 = (EditText) findViewById(R.id.txt4_2);
        txt5_2 = (EditText) findViewById(R.id.txt5_2);
        txt6_2 = (EditText) findViewById(R.id.txt6_2);
        txt7_2 = (EditText) findViewById(R.id.txt7_2);
        txt8_2 = (EditText) findViewById(R.id.txt8_2);
        txt9_2 = (EditText) findViewById(R.id.txt9_2);
        txt10_2 = (EditText) findViewById(R.id.txt10_2);
        txt11_2 = (EditText) findViewById(R.id.txt11_2);
        txt12_2 = (EditText) findViewById(R.id.txt12_2);
        txt13_2 = (EditText) findViewById(R.id.txt13_2);
        txt14_2 = (EditText) findViewById(R.id.txt14_2);
        txt15_2 = (EditText) findViewById(R.id.txt15_2);
        txt16_2 = (EditText) findViewById(R.id.txt16_2);
        txt17_2 = (EditText) findViewById(R.id.txt17_2);
        txt18_2 = (EditText) findViewById(R.id.txt18_2);
        txt19_2 = (EditText) findViewById(R.id.txt19_2);
        txt20_2 = (EditText) findViewById(R.id.txt20_2);
        txt21_2 = (EditText) findViewById(R.id.txt21_2);
        txt22_2 = (EditText) findViewById(R.id.txt22_2);
        txt23_2 = (EditText) findViewById(R.id.txt23_2);
        btnSeeExpertAdvice = (Button) findViewById(R.id.btnSeeExpertAdvice);
        btnSeeExpertAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = Utility.internetCheck(getApplicationContext());
                if (txt1_2.getText().toString().trim().isEmpty() | txt2_2.getText().toString().trim().isEmpty() | txt3_2.getText().toString().trim().isEmpty() | txt4_2.getText().toString().trim().isEmpty() | txt5_2.getText().toString().trim().isEmpty() | txt6_2.getText().toString().trim().isEmpty() | txt7_2.getText().toString().trim().isEmpty() | txt8_2.getText().toString().trim().isEmpty() | txt9_2.getText().toString().trim().isEmpty() | txt10_2.getText().toString().trim().isEmpty() | txt11_2.getText().toString().trim().isEmpty() | txt12_2.getText().toString().trim().isEmpty() | txt13_2.getText().toString().trim().isEmpty() | txt14_2.getText().toString().trim().isEmpty() | txt15_2.getText().toString().trim().isEmpty() | txt16_2.getText().toString().trim().isEmpty() | txt17_2.getText().toString().trim().isEmpty() | txt18_2.getText().toString().trim().isEmpty() | txt19_2.getText().toString().trim().isEmpty() | txt20_2.getText().toString().trim().isEmpty() | txt21_2.getText().toString().trim().isEmpty() | txt22_2.getText().toString().trim().isEmpty() | txt23_2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
                } else if (msg.equals("no internet")) {
                    Utility.showInSnackbar(R.string.nointernet, v);
                } else {
                    if (getGuestExpertAdvice() != null) {
                        frame1.setVisibility(View.VISIBLE);
                        DialogInterface.OnClickListener dialogclicklistener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE: {
                                        break;
                                    }
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(GuestExpertAdvice.this);
                        builder.setMessage("Disease : " + disease);
                        builder.setPositiveButton("Go Back", dialogclicklistener);
                        builder.show();
                    } else {
                        DialogInterface.OnClickListener dialogclicklistener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE: {
                                        break;
                                    }
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(GuestExpertAdvice.this);
                        builder.setMessage("Disease : " + "Normal");
                        builder.setPositiveButton("Go Back", dialogclicklistener);
                        builder.show();
                        frame1.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public String getGuestExpertAdvice() {
        guestLabReportBean.setRbc(Double.parseDouble(txt1_2.getText().toString()));
        guestLabReportBean.setHaemoglobin(Double.parseDouble(txt2_2.getText().toString()));
        guestLabReportBean.setMcv(Double.parseDouble(txt3_2.getText().toString()));
        guestLabReportBean.setWbv(Double.parseDouble(txt4_2.getText().toString()));
        guestLabReportBean.setNeutrophils(Double.parseDouble(txt5_2.getText().toString()));
        guestLabReportBean.setLymphocytes(Double.parseDouble(txt6_2.getText().toString()));
        guestLabReportBean.setMonocytes(Double.parseDouble(txt7_2.getText().toString()));
        guestLabReportBean.setEsoinophils(Double.parseDouble(txt8_2.getText().toString()));
        guestLabReportBean.setBasophils(Double.parseDouble(txt9_2.getText().toString()));
        guestLabReportBean.setPlatelets(Double.parseDouble(txt10_2.getText().toString()));
        guestLabReportBean.setBlood_urea(Double.parseDouble(txt11_2.getText().toString()));
        guestLabReportBean.setSerum_uric_acid(Double.parseDouble(txt12_2.getText().toString()));
        guestLabReportBean.setSerum_bilirubin(Double.parseDouble(txt13_2.getText().toString()));
        guestLabReportBean.setAlkaline_phosphate(Double.parseDouble(txt14_2.getText().toString()));
        guestLabReportBean.setSodium(Double.parseDouble(txt15_2.getText().toString()));
        guestLabReportBean.setPotassium(Double.parseDouble(txt16_2.getText().toString()));
        guestLabReportBean.setChlorides(Double.parseDouble(txt17_2.getText().toString()));
        guestLabReportBean.setCalcium(Double.parseDouble(txt18_2.getText().toString()));
        guestLabReportBean.setPhosphorous(Double.parseDouble(txt19_2.getText().toString()));
        guestLabReportBean.setAcid_phosphates(Double.parseDouble(txt20_2.getText().toString()));
        guestLabReportBean.setProteins(Double.parseDouble(txt21_2.getText().toString()));
        guestLabReportBean.setAlbumin(Double.parseDouble(txt22_2.getText().toString()));
        guestLabReportBean.setFasting_blood_sugar(Double.parseDouble(txt23_2.getText().toString()));
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel> call = userApi.getGuestExpertAdvice(guestLabReportBean);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel resultModel = response.body();
                disease = resultModel.getMsg();
                System.out.println(disease);
                frame1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);
            }
        });
        frame1.setVisibility(View.GONE);

        return disease;
    }

}
