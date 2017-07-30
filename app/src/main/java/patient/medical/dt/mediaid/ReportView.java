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

import patient.medical.dt.mediaid.beans.ExpertReportBean;
import patient.medical.dt.mediaid.beans.LabReportBean;
import patient.medical.dt.mediaid.util.ALLURL;
import patient.medical.dt.mediaid.util.RestClient;
import patient.medical.dt.mediaid.util.ResultModel;
import patient.medical.dt.mediaid.util.UserApi;
import patient.medical.dt.mediaid.util.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportView extends AppCompatActivity {
    Button btnSeeExpertAdvice;
    String disease = null;
    EditText txt1_2, txt2_2, txt3_2, txt4_2, txt5_2, txt6_2, txt7_2, txt8_2, txt9_2, txt10_2, txt11_2, txt12_2, txt13_2, txt14_2, txt15_2, txt16_2, txt17_2, txt18_2, txt19_2, txt20_2, txt21_2, txt22_2, txt23_2, txt24_2, txt25_2, txt26_2;
    private LabReportBean objbean;
    private FrameLayout frame1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        Utility.hideKeyboard(ReportView.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            objbean = (LabReportBean) b.getSerializable("bean");
            txt1_2.setText(String.valueOf(objbean.getRbc()));
            txt2_2.setText(String.valueOf(objbean.getHaemoglobin()));
            txt3_2.setText(String.valueOf(objbean.getMcv()));
            txt4_2.setText(String.valueOf(objbean.getWbv()));
            txt5_2.setText(String.valueOf(objbean.getNeutrophils()));
            txt6_2.setText(String.valueOf(objbean.getLymphocytes()));
            txt7_2.setText(String.valueOf(objbean.getMonocytes()));
            txt8_2.setText(String.valueOf(objbean.getEsoinophils()));
            txt9_2.setText(String.valueOf(objbean.getBasophils()));
            txt10_2.setText(String.valueOf(objbean.getPlatelets()));
            txt11_2.setText(String.valueOf(objbean.getBlood_urea()));
            txt12_2.setText(String.valueOf(objbean.getSerum_uric_acid()));
            txt13_2.setText(String.valueOf(objbean.getSerum_bilirubin()));
            txt14_2.setText(String.valueOf(objbean.getAlkaline_phosphate()));
            txt15_2.setText(String.valueOf(objbean.getSodium()));
            txt16_2.setText(String.valueOf(objbean.getPotassium()));
            txt17_2.setText(String.valueOf(objbean.getChlorides()));
            txt18_2.setText(String.valueOf(objbean.getCalcium()));
            txt19_2.setText(String.valueOf(objbean.getPhosphorous()));
            txt20_2.setText(String.valueOf(objbean.getAcid_phosphates()));
            txt21_2.setText(String.valueOf(objbean.getProteins()));
            txt22_2.setText(String.valueOf(objbean.getAlbumin()));
            txt23_2.setText(String.valueOf(objbean.getFasting_blood_sugar()));

        }
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
                if (msg.equals("no internet")) {
                    Utility.showInSnackbar(R.string.nointernet, v);
                } else {
                    frame1.setVisibility(View.VISIBLE);
                    if (getExpertAdvice() != null) {
                        DialogInterface.OnClickListener dialogclicklistener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE: {
                                        //SpUtility.clearSharedPreference(MainActivity.this, "med");
                                        break;
                                    }
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReportView.this);
                        builder.setMessage("Disease : " + disease);
                        builder.setPositiveButton("Go Back", dialogclicklistener);
                        builder.show();
                    }
                }
            }
        });
    }

    public String getExpertAdvice() {
        Retrofit retrofit = RestClient.build(ALLURL.APP_BASE_URL);
        UserApi userApi = retrofit.create(UserApi.class);
        Call<ResultModel<ExpertReportBean>> call = userApi.getExpertAdvice(objbean);
        call.enqueue(new Callback<ResultModel<ExpertReportBean>>() {
            @Override
            public void onResponse(Call<ResultModel<ExpertReportBean>> call, Response<ResultModel<ExpertReportBean>> response) {
                ResultModel<ExpertReportBean> r = response.body();
                ExpertReportBean expertReportBean = r.getResult_data();
                if (r.getResult_code() > 0) {
                    disease = expertReportBean.getDisease();
                    System.out.println(disease);
                }

            }

            @Override
            public void onFailure(Call<ResultModel<ExpertReportBean>> call, Throwable t) {
                t.printStackTrace();
                frame1.setVisibility(View.GONE);

            }
        });
        frame1.setVisibility(View.GONE);
        return disease;
    }
}
