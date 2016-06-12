package org.lntu.online.display.widget.design;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.lntu.online.R;

@CoordinatorLayout.DefaultBehavior(FloatingActionFrameLayout.Behavior.class)
public class FloatingActionFrameLayout extends FrameLayout {

    private static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();

    public FloatingActionFrameLayout(Context context) {
        super(context);
    }

    public FloatingActionFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingActionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloatingActionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static class Behavior extends CoordinatorLayout.Behavior<FloatingActionFrameLayout> {

        private Rect mTmpRect;
        private boolean mIsAnimatingOut;

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionFrameLayout child, View dependency) {
            if (dependency instanceof AppBarLayout) { // app:layout_anchor 设定为 AppBarLayout的时候
                AppBarLayout appBarLayout = (AppBarLayout) dependency;
                if (mTmpRect == null) {
                    mTmpRect = new Rect();
                }

                Rect rect = mTmpRect;
                ViewGroupUtils.getDescendantRect(parent, dependency, rect);
                //if(rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) { // TODO
                if (rect.bottom <= appBarLayout.getResources().getDimension(R.dimen.floating_action_frame_layout_anim_height)) {
                    if (!mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
                        animateOut(child);
                    }
                } else if (child.getVisibility() != View.VISIBLE) {
                    animateIn(child);
                }
            }
            return false;
        }

        private void animateIn(FloatingActionFrameLayout button) {
            button.setVisibility(View.VISIBLE);
            ViewPropertyAnimator.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(null).start();
        }

        private void animateOut(final FloatingActionFrameLayout button) {
            ViewPropertyAnimator.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    Behavior.this.mIsAnimatingOut = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Behavior.this.mIsAnimatingOut = false;
                    button.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Behavior.this.mIsAnimatingOut = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            }).start();
        }

    }

}
