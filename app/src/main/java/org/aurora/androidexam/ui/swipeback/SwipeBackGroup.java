package org.aurora.androidexam.ui.swipeback;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import org.aurora.androidexam.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Aurora
 * @date 2016-07-19
 */
public class SwipeBackGroup extends FrameLayout {

    private ViewDragHelper dragHelper;
    private float mScrollPercent;
    private int dragOffset = 0;
    private int draggingState;

    public SwipeBackGroup(Context context) {
        super(context);
        init();
    }

    public SwipeBackGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeBackGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private View pre = null;

    private void init() {

        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Logger.info(child, pointerId, getChildCount());

                if (getChildCount() > 1 && child == getChildAt(getChildCount() - 1))
                    return true;
                return false;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getWidth();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth();

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
//                Logger.info("newLeft" + newLeft);

                return newLeft;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                for (int i = 0; i < getChildCount() && getChildCount() > 0; i++) {
                    if (i + 1 < getChildCount() && getChildAt(i + 1) == capturedChild) {
                        pre = getChildAt(i);
                    }
                }
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                mScrollPercent = Math.abs((float) left
                        / getWidth());

                Logger.info("left " + left + "width" + getWidth() + "position Change " + left / getWidth()
                        + "   " + ViewCompat.getTranslationX(pre)
                        + "   " + ((float) left / getWidth())
                        + "   " + (ViewCompat.getTranslationX(pre) + ((float) left / getWidth()) * 100));

                if (pre != null) {
                    ViewCompat.setTranslationX(pre, 100 * (((float) left / getWidth()) - 1));

                }

                invalidate();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                Logger.info("mScrollPercent" + mScrollPercent);
                if (xvel > 0 && backBySpeed(xvel, yvel)) {
                    smoothScrollToX(getWidth());
//                    ObjectAnimator.ofFloat(pre, "translationX", 0);
                } else if (mScrollPercent >= 0.4f) {
                    smoothScrollToX(getWidth());
//                    ObjectAnimator.ofFloat(pre, "translationX", 0);
                } else {
                    smoothScrollToX(0);
//                    ObjectAnimator.ofFloat(pre, "translationX", 0);
                }
            }

            @Override
            public void onViewDragStateChanged(int state) {
                if (state == draggingState) return;


                if ((draggingState == ViewDragHelper.STATE_DRAGGING || draggingState == ViewDragHelper.STATE_SETTLING) &&
                        state == ViewDragHelper.STATE_IDLE) {
                    // the view stopped from moving.
                    if (mScrollPercent == 1.0f) {
                        removeViewAt(getChildCount() - 1);
                        pre = null;
                    }
                }
                draggingState = state;
            }
        });
        setWillNotDraw(false);
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.DIRECTION_ALL);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();

        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            dragHelper.cancel();
            return false;
        }

        boolean ret = dragHelper.shouldInterceptTouchEvent(ev);
//        Logger.info("onInterceptTouchEvent " + ret);

        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        dragHelper.processTouchEvent(ev);
        return true;
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
//        Logger.info("computeScroll " + mScrimOpacity);
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
//        ViewCompat.postInvalidateOnAnimation(this);
    }

    private View target;
    private View scrollChild;

    private void ensureTarget() {
        if (target == null) {
            target = getChildAt(getChildCount() - 1);

            if (scrollChild == null && target != null) {
                if (target instanceof ViewGroup) {
                    findScrollView((ViewGroup) target);
                } else {
                    scrollChild = target;
                }

            }
        }
    }

    private void findScrollView(ViewGroup viewGroup) {
        scrollChild = viewGroup;
        if (viewGroup.getChildCount() > 0) {
            int count = viewGroup.getChildCount();
            View child;
            for (int i = 0; i < count; i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof AbsListView || child instanceof ScrollView || child instanceof ViewPager || child instanceof WebView) {
                    scrollChild = child;
                    return;
                }
            }
        }
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
//        Logger.info(child);
        drawScrim(canvas, child);
//        drawShadow(canvas, child);
        return ret;

    }

    private void smoothScrollToX(int finalLeft) {
        if (dragHelper.settleCapturedViewAt(finalLeft, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    private static final double AUTO_FINISHED_SPEED_LIMIT = 500.0;

    private boolean backBySpeed(float xvel, float yvel) {
        if (Math.abs(xvel) > Math.abs(yvel) && Math.abs(xvel) > AUTO_FINISHED_SPEED_LIMIT) {
            return true;
        }
        return false;
    }

    private int mScrimColor = 0xd9000000;
    private float mScrimOpacity = 0.5f;
    private Rect mTmpRect = new Rect();

    private void drawScrim(Canvas canvas, View child) {
        if (child != getChildAt(getChildCount() - 1))
            return;
        int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        int alpha = (int) (baseAlpha * mScrimOpacity);

        final int color = alpha << 24 | (mScrimColor & 0xffffff);
//        Logger.info(child.getLeft());
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    private Drawable mShadowLeft = new ColorDrawable(Color.parseColor("#99000000"));
    private static final int FULL_ALPHA = 255;

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);

        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Logger.info("onDraw");
    }
}
