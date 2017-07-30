package patient.medical.dt.mediaid.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Divya on 06-05-2017.
 */
public class MyTextViewRegularcomf extends TextView {
    public MyTextViewRegularcomf(Context context) {
        super(context);
        init();
    }

    public MyTextViewRegularcomf(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewRegularcomf(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Comfortaa-Regular.ttf");
            setTypeface(tf);
        }
    }
}
