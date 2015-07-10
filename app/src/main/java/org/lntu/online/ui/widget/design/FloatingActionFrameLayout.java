package org.lntu.online.ui.widget.design;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.lntu.online.R;

@CoordinatorLayout.DefaultBehavior(FloatingActionFrameLayout.Behavior.class)
public class FloatingActionFrameLayout extends FrameLayout {

    public FloatingActionFrameLayout(Context context) {
        super(context);
    }

    public FloatingActionFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingActionFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static class Behavior extends CoordinatorLayout.Behavior<FloatingActionFrameLayout> {

        private Rect mTmpRect;
        private boolean mIsAnimatingOut;

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionFrameLayout child, View dependency) {
            if(dependency instanceof AppBarLayout) { // app:layout_anchor 设定为 AppBarLayout的时候
                AppBarLayout appBarLayout = (AppBarLayout)dependency;
                if(mTmpRect == null) {
                    mTmpRect = new Rect();
                }

                Rect rect = mTmpRect;
                ViewGroupUtils.getDescendantRect(parent, dependency, rect);
                //if(rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) { // TODO
                if(rect.bottom <= appBarLayout.getResources().getDimension(R.dimen.floating_action_frame_layout_anim_height)) {
                    if(!mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
                        animateOut(child);
                    }
                } else if(child.getVisibility() != View.VISIBLE) {
                    animateIn(child);
                }
            }
            return false;
        }

        private void animateIn(FloatingActionFrameLayout button) {
            button.setVisibility(View.VISIBLE);
            if(Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).withLayer().setListener(null).start();
            } else {
                Animation anim = android.view.animation.AnimationUtils.loadAnimation(button.getContext(), android.support.design.R.anim.fab_in);
                anim.setDuration(200L);
                anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                button.startAnimation(anim);
            }

        }

        private void animateOut(final FloatingActionFrameLayout button) {
            if(Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        Behavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        Behavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        Behavior.this.mIsAnimatingOut = false;
                        view.setVisibility(View.GONE);
                    }
                }).start();
            } else {
                Animation anim = android.view.animation.AnimationUtils.loadAnimation(button.getContext(), android.support.design.R.anim.fab_out);
                anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                anim.setDuration(200L);
                anim.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() {
                    public void onAnimationStart(Animation animation) {
                        Behavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationEnd(Animation animation) {
                        Behavior.this.mIsAnimatingOut = false;
                        button.setVisibility(View.GONE);
                    }
                });
                button.startAnimation(anim);
            }
        }

    }

}
