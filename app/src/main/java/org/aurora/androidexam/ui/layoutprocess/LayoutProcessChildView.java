package org.aurora.androidexam.ui.layoutprocess;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.aurora.androidexam.log.Logger;

/**
 * @author Aurora
 * @date 2016-07-07
 */
public class LayoutProcessChildView extends View{
    public LayoutProcessChildView(Context context) {
        super(context);
    }

    public LayoutProcessChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutProcessChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Logger.info("onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.info("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Logger.info("onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
