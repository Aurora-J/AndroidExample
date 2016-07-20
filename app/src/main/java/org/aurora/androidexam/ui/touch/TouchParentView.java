package org.aurora.androidexam.ui.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author Aurora
 * @date 2016-07-07
 */
public class TouchParentView extends ViewGroup{
    public TouchParentView(Context context) {
        super(context);
    }

    public TouchParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
