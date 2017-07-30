package patient.medical.dt.mediaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import patient.medical.dt.mediaid.beans.LabReportBean;

/**
 * Created by Amandeep on 5/12/2017.
 */
public class Report_Adapter extends RecyclerView.Adapter<Report_Adapter.MyViewHolder> {
    View itemview;
    private ArrayList al;
    private Activity a;
    private Bundle b;


    public Report_Adapter(ArrayList al, Activity a) {
        this.al = al;
        this.a = a;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_report_layout, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final LabReportBean labReportBean = (LabReportBean) al.get(position);
        holder.txtReport.setText(labReportBean.getDoctor_name());
        holder.txtTestDate.setText(labReportBean.getTest_date());
        System.out.println(labReportBean.getRbc() + "gggggggggggggggggggggggggggggggggg");
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(a, ReportView.class);
                b = new Bundle();
                b.putSerializable("bean", labReportBean);
                i.putExtras(b);
                a.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtReport, txtTestDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtReport = (TextView) itemView.findViewById(R.id.txtReport);
            txtTestDate = (TextView) itemView.findViewById(R.id.txtTestDate);
        }
    }
}
