package patient.medical.dt.mediaid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import patient.medical.dt.mediaid.beans.AppointmentBean;

/**
 * Created by harman on 4/5/17.
 */
public class Appointments_Adapter extends RecyclerView.Adapter<Appointments_Adapter.MyViewHolder> {


    Context context;
    View itemView;
    ArrayList<AppointmentBean> al;
    int year, month, day;

    public Appointments_Adapter(ArrayList<AppointmentBean> al) {
        this.al = al;
//        this.context=c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflator is the base class of setContentView
        context = parent.getContext();
        itemView = LayoutInflater.from(context).inflate(R.layout.rowlayout_appointments, parent, false);  //return type of context is LayoutInflater n return type of inflate is view
        //conversion of xml to java using inflate(), boolean argument for setting the margins acc to parent if true
        //LayoutInflater.from(context);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AppointmentBean appointmentBean = al.get(position);
        holder.txtAppointment.setText(String.valueOf(appointmentBean.getDoctor_name()));
        String s = appointmentBean.getAppointment_time().trim();
        String s1[] = s.split(":");
        System.out.println(s1[0] + "----------------------");
        if (s1[0].equals("10")) {
            System.out.println("1");
            holder.txtTime.setText("10");
            holder.txtAMPM.setText("AM");
        } else if (s1[0].equals("12")) {
            System.out.println("2");
            holder.txtTime.setText("12");
            holder.txtAMPM.setText("NOON");
        } else if (s1[0].equals("02")) {
            System.out.println("3");
            holder.txtTime.setText("2");
            holder.txtAMPM.setText("PM");
        } else if (s1[0].equals("04")) {
            System.out.println("4");
            holder.txtTime.setText("4");
            holder.txtAMPM.setText("PM");
        } else if (s1[0].equals("06")) {
            System.out.println("5");
            holder.txtTime.setText("6");
            holder.txtAMPM.setText("PM");
        }
        holder.txtDate.setText(String.valueOf(appointmentBean.getAppointment_date()));
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtAppointment, txtDate, txtTime, txtAMPM;
        LinearLayout layout1;

        public MyViewHolder(View itemView) {

            super(itemView);
            txtAppointment = (TextView) itemView.findViewById(R.id.txtAppointment);  //this thing already done in setContentView in java file
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtAMPM = (TextView) itemView.findViewById(R.id.txtAMPM);
            layout1 = (LinearLayout) itemView.findViewById(R.id.layout1);

        }
    }
}
