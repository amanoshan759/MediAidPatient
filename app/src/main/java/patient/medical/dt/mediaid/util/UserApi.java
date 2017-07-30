package patient.medical.dt.mediaid.util;

import java.util.ArrayList;

import patient.medical.dt.mediaid.beans.AppointmentBean;
import patient.medical.dt.mediaid.beans.DoctorBean;
import patient.medical.dt.mediaid.beans.ExpertReportBean;
import patient.medical.dt.mediaid.beans.GuestLabReportBean;
import patient.medical.dt.mediaid.beans.HospitalBean;
import patient.medical.dt.mediaid.beans.LabReportBean;
import patient.medical.dt.mediaid.beans.PasswordBean;
import patient.medical.dt.mediaid.beans.PatientBean;
import patient.medical.dt.mediaid.beans.UserBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Amandeep on 5/11/2017.
 */
public interface UserApi {
    @POST(ALLURL.POST_AUTHENTICATE_PATIENT)
    Call<ResultModel<UserBean>> authenticatePatient(@Body UserBean userbean);

    @POST(ALLURL.POST_PATIENT_SIGN_UP)
    Call<ResultModel<UserBean>> patientSignUp(@Body PatientBean patientbean);

    @POST(ALLURL.POST_FORGOT_PASSWORD)
    Call<ResultModel> forgotPassword(@Body UserBean userbean);

    @POST(ALLURL.POST_RESET_PASSWORD)
    Call<ResultModel> resetPasssword(@Body UserBean userbean);

    @POST(ALLURL.POST_CHANGE_PASSWORD)
    Call<ResultModel> changePassword(@Body PasswordBean passwordBean);

    @POST(ALLURL.POST_GET_PROFILE)
    Call<ResultModel<PatientBean>> getProfile(@Body UserBean userbean);

    @POST(ALLURL.POST_UPDATE_PROFILE)
    Call<ResultModel> updatePatientProfile(@Body PatientBean patientbean);

    @GET(ALLURL.GET_HOSPITAL)
    Call<ResultModel<ArrayList<HospitalBean>>> getHospital();

    @POST(ALLURL.GET_DOCTORS)
    Call<ResultModel<ArrayList<DoctorBean>>> getDoctors(@Body DoctorBean doctorBean);

    @POST(ALLURL.POST_GET_APPOINTMENT)
    Call<ResultModel<ArrayList<AppointmentBean>>> getAppointment(@Body AppointmentBean appointmentBean);

    @POST(ALLURL.POST_SET_APPOINTMENT)
    Call<ResultModel> setAppointment(@Body AppointmentBean appointmentBean);

    @POST(ALLURL.POST_DOCTOR_REPORTS)
    Call<ResultModel<ArrayList<LabReportBean>>> getDoctorReports(@Body LabReportBean labReportBean);

    @POST(ALLURL.POST_EXPERT_REPORTS)
    Call<ResultModel<ExpertReportBean>> getExpertAdvice(@Body LabReportBean labReportBean);

    @POST(ALLURL.POST_EXPERT_ADVICE)
    Call<ResultModel> getGuestExpertAdvice(@Body GuestLabReportBean guestLabReportBean);

}
