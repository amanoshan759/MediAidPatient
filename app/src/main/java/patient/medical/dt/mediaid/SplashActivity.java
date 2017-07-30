package patient.medical.dt.mediaid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import patient.medical.dt.mediaid.util.SpUtility;
import patient.medical.dt.mediaid.util.Utility;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.patienttttttttttt);


        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(6000);
                    SharedPreferences sp = SpUtility.getSharedPreference(getApplicationContext());

                    if (sp.getBoolean(SpUtility.KEY_FLAG_LOGIN, SpUtility.DEFAULT_VALUE_BOOLEAN)) {
                        Utility.startIntent(SplashActivity.this, MainActivity.class);

                    } else {
                        Utility.startIntent(SplashActivity.this, LoginActivity.class);
                    }

                    SplashActivity.this.finish();
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        };
        t.start();
    }
}
