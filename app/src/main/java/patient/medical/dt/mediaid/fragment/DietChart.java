package patient.medical.dt.mediaid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import patient.medical.dt.mediaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DietChart extends Fragment {

    private TextView txtTime1, txtTime2, txtTime3, txtTime4, txtTime5;
    private TextView txtFood1, txtFood2, txtFood3, txtFood4, txtFood5;
    private TextView txtCalory1, txtCalory2, txtCalory3, txtCalory4, txtCalory5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_chart, container, false);
        init(view);
        return view;
    }

    public void init(View v) {
        txtTime1 = (TextView) v.findViewById(R.id.txtTime1);
        txtTime2 = (TextView) v.findViewById(R.id.txtTime2);
        txtTime3 = (TextView) v.findViewById(R.id.txtTime3);
        txtTime4 = (TextView) v.findViewById(R.id.txtTime4);

        txtFood1 = (TextView) v.findViewById(R.id.txtFood1);
        txtFood2 = (TextView) v.findViewById(R.id.txtFood2);
        txtFood3 = (TextView) v.findViewById(R.id.txtFood3);
        txtFood4 = (TextView) v.findViewById(R.id.txtFood4);

        txtCalory1 = (TextView) v.findViewById(R.id.txtCalory1);
        txtCalory2 = (TextView) v.findViewById(R.id.txtCalory2);
        txtCalory3 = (TextView) v.findViewById(R.id.txtCalory3);
        txtCalory4 = (TextView) v.findViewById(R.id.txtCalory4);

        txtTime1.setText("Mid-Morning");
        txtTime2.setText("Lunch");
        txtTime3.setText("Evening");
        txtTime4.setText("Dinner");

        txtFood1.setText("1 Banana/ 1/2 cup Melon/ 20 Grapes");
        txtFood2.setText("Brown Rice 1 cup + Mixed Vegetables 1/2n cup + Salad 1 bowl + Raita 1 small bowl");
        txtFood3.setText("Butter Milk 1 cup");
        txtFood4.setText("2 Rotis + Vegetable Soup 1 bowl + Salad 1 bowl");

        txtCalory1.setText("50");
        txtCalory2.setText("345");
        txtCalory3.setText("35");
        txtCalory4.setText("370");

    }

}
