package patient.medical.dt.mediaid.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Amandeep on 5/9/2017.
 */
public class SpUtility {
    public final static String KEY_SIGN_UP_NAME = "sign_up_name";
    public final static String KEY_SIGN_UP_EMAIL = "sign_up_email";
    public final static String KEY_SIGN_UP_FLAG = "sign_up_flag";
    public final static String KEY_PATIENT_NAME = "patient_name";
    public final static String KEY_PATIENT_USERNAME = "patient_username";
    public final static String KEY_PATIENT_EMAIL = "patient_email";
    public final static String KEY_PATIENT_DOB = "patient_dob";
    public final static String KEY_PATIENT_GENDER = "patient_gender";
    public final static String KEY_PATIENT_BLOODGROUP = "patient_bloodgroup";
    public final static String KEY_PATIENT_CONTACT = "patient_contact";
    public final static String KEY_PATIENT_PASSWORD = "patient_password";
    public final static String KEY_USER_ID = "user_id";
    public final static String KEY_USER_ID_FORGOT = "user_id_forgot";
    public final static String KEY_FORGOT_CONTACT = "forgot_contact";
    public final static String KEY_FLAG_CHANGE_PASSWORD = "change_password_flag";
    public final static String KEY_PATIENT_APPOINTMENT_ID = "patient_appoint_id";
    public final static String KEY_FLAG_SIGN_SET = "sign_set_flag";
    public final static String KEY_FLAG_LOGIN = "login_flag";
    public final static String KEY_DOCTOR_ID = "doctor_id";
    public final static String DEFAULT_VALUE_STRING = "null";
    public final static boolean DEFAULT_VALUE_BOOLEAN = false;
    public final static int DEFAULT_VALUE_INT = 0;

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences("med", Context.MODE_PRIVATE);
    }

    public static void clearSharedPreference(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("med", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.commit();
    }
}
