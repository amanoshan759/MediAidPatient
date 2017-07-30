package patient.medical.dt.mediaid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import patient.medical.dt.mediaid.R;
import patient.medical.dt.mediaid.util.Utility;

public class Bmi extends Fragment {


    double sqr, htsqr, wt, BMI;
    int ft, inch;
    private EditText etAge, etFeet, etInch, etBMI, etWeight;
    private Button btnCalculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        etAge = (EditText) v.findViewById(R.id.etAge);
        etFeet = (EditText) v.findViewById(R.id.etFeet);
        etInch = (EditText) v.findViewById(R.id.etInch);
        etWeight = (EditText) v.findViewById(R.id.etWeight);
        etBMI = (EditText) v.findViewById(R.id.etBMI);
        btnCalculate = (Button) v.findViewById(R.id.btn);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etWeight.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_wieght, v);
                } else if (etFeet.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_feet, v);
                } else if (etInch.getText().toString().trim().isEmpty()) {
                    Utility.showInSnackbar(R.string.snackbar_inch, v);
                } else {
                    Utility.hideKeyboard(getActivity());
                    ft = Integer.parseInt(etFeet.getText().toString().trim());
                    inch = Integer.parseInt(etInch.getText().toString().trim());
                    htsqr = Utility.calculateHeight(ft, inch);
                    wt = Double.parseDouble(etWeight.getText().toString().trim());
                    BMI = Utility.calculateBMI(htsqr, wt);
                    etBMI.setText(String.valueOf(BMI));
                }
            }
        });
    }
}
