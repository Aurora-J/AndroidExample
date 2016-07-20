package org.aurora.androidexam.ui.layoutprocess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author Aurora
 * @date 2016-07-07
 */
public class LayoutProcessView extends ViewGroup{
    public LayoutProcessView(Context context) {
        super(context);
    }

    public LayoutProcessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutProcessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
