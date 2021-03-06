package patient.medical.dt.mediaid.views;

/**
 * Created by Neha on 5/6/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Hashtable;

/**
 * Created by INDU BALA on 5/6/2017.
 */
public class FaktSoftProBold {
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();
    private Typeface mCustomTypeFace1;

    public FaktSoftProBold(Context context) {
        mCustomTypeFace1 = get(context, "FaktSoftPro-Blond");
        //if you want to use more than one custom font then declare all of them here like above.
    }

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(c.getAssets(),
                        String.format("%s.ttf", name));
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }

    public TextView changeFontOfTextView(View view) {

        TextView objTextView = (TextView) view;
        objTextView.setTypeface(this.mCustomTypeFace1);
        return objTextView;
    }

    public EditText changeFontOfEditText(View view) {

        EditText objEditText = (EditText) view;
        objEditText.setTypeface(this.mCustomTypeFace1);
        return objEditText;

    }

    public Button changeFontOfButton(View view) {

        Button objButtonView = (Button) view;
        objButtonView.setTypeface(this.mCustomTypeFace1);
        return objButtonView;
    }
}
