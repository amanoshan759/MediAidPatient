package patient.medical.dt.mediaid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utility {
    static String msg;

    public static void startIntent(Context source, Class destination) {
        Intent i = new Intent(source, destination);
        source.startActivity(i);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static double calculateHeight(int feet, int inches) {
        int f = feet, i = inches;
        i = i + (f * 12);
        double inMetre = i * 0.025;
        double sqr = inMetre * inMetre;
        return sqr;
    }

    public static double calculateBMI(double heightsqr, double weight) {
        double bmi = weight / heightsqr;
        return bmi;
    }

    public static void showInSnackbar(int msg, View view) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#a8e063"));
        snackbar.show();
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String[] OTPSplit(String message) {
        msg = message;
        String[] words = msg.split("\\s");
        String OTP = words[3];
        String ch1 = OTP.substring(0, 1);
        String ch2 = OTP.substring(1, 2);
        String ch3 = OTP.substring(2, 3);
        String ch4 = OTP.substring(3, 4);
        String[] ch = {ch1, ch2, ch3, ch4};
        return ch;
    }

    public static String internetCheck(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        String answer = "An Error occurred";
        try {
            if (null != activeNetworkInfo) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    answer = "You are connected to a Wi-Fi network";
                }
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    answer = "You are connected to a Mobile network";
                }
            } else
                answer = "no internet";
        } catch (Exception e) {
            System.out.println(e);
        }
        return answer;
    }
}

