package com.xiaomi.animation.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.xiaomi.animation.info.IconInfo;
import com.xiaomi.animation.PropertyAnimator.AnimationUtils;

/**
 * Created by admin on 2018/2/7.
 */

public class MIUILauncherTransition extends Transition{

    private static final String TRANSITION_FLAG = "transition_flag";
    private Intent mIntent;

    public MIUILauncherTransition() {
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues, 0);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues, 1);
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Log.e("wjq","createAnimator = ");
        IconInfo iconInfo = mIntent.getParcelableExtra("Icon_Info");
        ViewGroup rootView = (ViewGroup) sceneRoot.getChildAt(0);
        int statusBarHeight = rootView.getPaddingTop();



        rootView.setPivotX(iconInfo.getRectF().centerX());
        rootView.setPivotY(iconInfo.getRectF().centerY());
        AnimatorSet animatorSet = AnimationUtils.createAnimatorSet();
        ObjectAnimator alphaRootViewAnim = AnimationUtils.ofFloat(rootView, "alpha", 0f, 1f);
        ObjectAnimator scaleRootViewXAnim = AnimationUtils.ofFloat(rootView, "scaleX", 1.3f, 1f);
        ObjectAnimator scaleRootViewYAnim = AnimationUtils.ofFloat(rootView, "scaleY",1.3f, 1f);

        animatorSet.setDuration(1000 * 4);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(alphaRootViewAnim,scaleRootViewXAnim,scaleRootViewYAnim);
        return animatorSet;
    }

    private void captureValues(TransitionValues transitionValues, int flag) {
        transitionValues.values.put(TRANSITION_FLAG, flag);
    }

    private static final String[] sTransitionProperties = {
            TRANSITION_FLAG,
    };

    public void setIntent(Intent intent) {
        mIntent = intent;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }


    private ImageView createThumbnailView(ViewGroup rootView, IconInfo iconInfo, int statusBarHeight) {
        Context context = rootView.getContext();
        ImageView imageView = new ImageView(context);
        Drawable drawable = context.getResources().getDrawable(iconInfo.getDrawableId());
        imageView.setImageDrawable(drawable);
        int viewWidth = (int) (iconInfo.getWidth() * 1f);
        int viewHeight =  (int) (iconInfo.getHeight() * 1f);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(viewWidth,viewHeight);
        rootView.addView(imageView,layoutParams);
        imageView.setX(iconInfo.getRectF().centerX() - viewWidth / 2);
        imageView.setY(iconInfo.getRectF().centerY() -  viewHeight / 2 + statusBarHeight);
        return imageView;
    }
}
