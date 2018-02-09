package com.xiaomi.animation.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.xiaomi.animation.Info.IconInfo;
import com.xiaomi.animation.PropertyAnimator.AnimationUtils;
import com.xiaomi.animation.R;

/**
 * Created by admin on 2018/1/29.
 */

public class ActivityTransition extends Transition {
    private final static String FLAG = "status_bar";
    private Intent mIntent;
    private Window mWindow;
    private boolean mIsEnter = true;

    public ActivityTransition(Window window,Intent intent,boolean isEnter) {
        mIntent = intent;
        mWindow = window;
        mIsEnter = isEnter;
        if (!mIsEnter) {
            Log.e("wjq","44444444444444444444");
        }
    }


    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (!mIsEnter) {
            Log.e("wjq","111111111111111");
        }
        captureValues(transitionValues,0);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (!mIsEnter) {
            Log.e("wjq","222222222222222");
        }
        captureValues(transitionValues,1);
    }


    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) { return null;}
        AnimatorSet animatorSet = AnimationUtils.createAnimatorSet();
        final IconInfo iconInfo = mIntent.getParcelableExtra("Icon_Info");
        final int statusBarHeight = endValues.view.getHeight();
//        ImageView bubbleTextView = createThumbnailView(sceneRoot,iconInfo,statusBarHeight);
//        ViewPropertyAnimator thumbnailAnimator = new ViewPropertyAnimator(bubbleTextView);
//        thumbnailAnimator.setInterpolator(new AccelerateInterpolator());
//        thumbnailAnimator.scaleX(1.5f).scaleY(1.5f)
//                .alpha(0f)
//                .setDuration(1000 * 3);
        if (mIsEnter) {
            float scaleX = (float) iconInfo.getWidth() / (float) mWindow.getDecorView().getWidth();
            float scaleY = (float) iconInfo.getHeight() / (float) mWindow.getDecorView().getHeight();
            View rootView = sceneRoot.getChildAt(0);
            rootView.setX(iconInfo.getRectF().centerX() - iconInfo.getWidth() / 2);
            rootView.setY(iconInfo.getRectF().centerY() + statusBarHeight - iconInfo.getHeight() / 2);
            rootView.setPivotX(0);
            rootView.setPivotY(0);
            rootView.setScaleX(scaleX);
            rootView.setScaleY(scaleY);
            rootView.setBackgroundResource(R.drawable.background);


            ObjectAnimator alphaAnim = AnimationUtils.ofFloat(rootView, "alpha", 0f, 1f);
            ObjectAnimator xAnim = AnimationUtils.ofFloat(rootView, "x", iconInfo.getRectF().centerX() - iconInfo.getWidth() / 2, 0);
            ObjectAnimator yAnim = AnimationUtils.ofFloat(rootView, "y", iconInfo.getRectF().centerY() + statusBarHeight - iconInfo.getHeight() / 2, 0);
            ObjectAnimator scaleXAnim = AnimationUtils.ofFloat(rootView, "scaleX", scaleX, 1f);
            ObjectAnimator scaleYAnim = AnimationUtils.ofFloat(rootView, "scaleY", scaleY, 1f);

            animatorSet.setDuration(1000 * 1);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.playTogether(alphaAnim, xAnim, yAnim, scaleXAnim, scaleYAnim);
        }else {
            Log.e("wjq","33333333333333333");
        }

        return animatorSet;
    }

    @Override
    public String[] getTransitionProperties() {
        return new String[]{ FLAG };
    }

    private void captureValues(TransitionValues transitionValues, int flag) {
        if (android.R.id.statusBarBackground == transitionValues.view.getId()) {
            transitionValues.values.put(FLAG, flag);
        }
    }

    private ImageView createThumbnailView(ViewGroup rootView, IconInfo iconInfo, int statusBarHeight) {
        Context context = rootView.getContext();
        ImageView imageView = new ImageView(context);
        Drawable drawable = context.getResources().getDrawable(iconInfo.getDrawableId());
        imageView.setImageDrawable(drawable);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(iconInfo.getWidth(),iconInfo.getHeight());
        rootView.addView(imageView,layoutParams);
        imageView.setX(iconInfo.getRectF().centerX() - iconInfo.getWidth() / 2);
        imageView.setY(iconInfo.getRectF().centerY() -  iconInfo.getHeight() / 2 + statusBarHeight);
        return imageView;
    }
}
