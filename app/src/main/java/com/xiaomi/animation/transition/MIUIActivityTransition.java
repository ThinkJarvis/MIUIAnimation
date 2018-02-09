package com.xiaomi.animation.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.xiaomi.animation.Info.IconInfo;
import com.xiaomi.animation.PropertyAnimator.AnimationUtils;



/**
 * Created by admin on 2018/2/6.
 */

public class MIUIActivityTransition extends Transition{
    public static final int ENTER_TRANSITION = 0;
    public static final int RETURN_TRANSITION = 1;
    private static final DecelerateInterpolator sDecelerateInterpolator = new DecelerateInterpolator();
    private static final String TRANSITION_FLAG = "transition_flag";
    private int transitionFlag;
    private Intent mIntent;


    public MIUIActivityTransition(int flag, Intent intent) {
        this.transitionFlag = flag;
        this.mIntent = intent;
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
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        AnimatorSet animatorSet = null;
        IconInfo iconInfo = mIntent.getParcelableExtra("Icon_Info");
        ViewGroup rootView = (ViewGroup) sceneRoot.getChildAt(0);
        int statusBarHeight = rootView.getPaddingTop();
        if (transitionFlag == ENTER_TRANSITION) {
            float[] animationValue = initEnterView(sceneRoot,rootView,iconInfo,statusBarHeight);
            animatorSet = playAnimatorSet(rootView,animationValue,true);

        }else if (transitionFlag == RETURN_TRANSITION) {
            float[] animationValue = initExitView(sceneRoot,rootView,iconInfo,statusBarHeight);
            animatorSet = playAnimatorSet(rootView,animationValue,false);
        }

        return animatorSet;
    }


    private void captureValues(TransitionValues transitionValues, int flag) {
        transitionValues.values.put(TRANSITION_FLAG, flag);
    }

    private static final String[] sTransitionProperties = {
            TRANSITION_FLAG,
    };

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private float[] initEnterView(ViewGroup sceneRoot, ViewGroup rootView,IconInfo iconInfo,int statusBarHeight) {
        sceneRoot.setBackgroundColor(Color.TRANSPARENT);
        float scaleX = (float) iconInfo.getWidth() / (float) sceneRoot.getWidth();
        float scaleY = (float) iconInfo.getHeight() / (float) sceneRoot.getHeight();
        float startX = iconInfo.getRectF().centerX() - iconInfo.getWidth() / 2;
        float startY = iconInfo.getRectF().centerY() - iconInfo.getHeight() / 2 + statusBarHeight;
        rootView.setX(startX);
        rootView.setY(startY);
        rootView.setPivotX(0);
        rootView.setPivotY(0);
        rootView.setScaleX(scaleX);
        rootView.setScaleY(scaleY);
        float[] animationValue = new float[10];
        animationValue[0] = 0f;animationValue[1] = 1f;
        animationValue[2] = iconInfo.getRectF().centerX() - iconInfo.getWidth() / 2;animationValue[3] = 0f;
        animationValue[4] = iconInfo.getRectF().centerY() - iconInfo.getHeight() / 2 + statusBarHeight;animationValue[5] = 0f;
        animationValue[6] = scaleX;animationValue[7] = 1f;
        animationValue[8] = scaleY;animationValue[9] = 1f;
        return animationValue;
    }

    private float[] initExitView(ViewGroup sceneRoot, ViewGroup rootView,IconInfo iconInfo, int statusBarHeight) {

        float endScaleX = (float) iconInfo.getWidth();
        float endScaleY = (float) iconInfo.getHeight();
        float scaleX = endScaleX / (float) sceneRoot.getWidth();
        final float scaleY = endScaleY / (float) sceneRoot.getHeight();
        float[] animationValue = new float[10];
        animationValue[0] = 1f;animationValue[1] = 0f;

        rootView.setPivotX(0);
        rootView.setPivotY(0);

        float endX = iconInfo.getRectF().centerX() - endScaleX / 2;
        float endY = iconInfo.getRectF().centerY() - endScaleY / 2  + statusBarHeight;

        animationValue[2] =0f;animationValue[3] = endX;
        animationValue[4] = 0f;animationValue[5] = endY;
        animationValue[6] = 1f;animationValue[7] = scaleX;
        animationValue[8] = 1f;animationValue[9] = scaleY;
        return animationValue;
    }


    private AnimatorSet playAnimatorSet(ViewGroup rootView,float[] animationValue,boolean isEnter) {
        AnimatorSet animatorSet = AnimationUtils.createAnimatorSet();
        ObjectAnimator alphaAnim = AnimationUtils.ofFloat(rootView, "alpha", animationValue[0], animationValue[1]);
        ObjectAnimator xAnim = AnimationUtils.ofFloat(rootView, "x",animationValue[2], animationValue[3]);
        ObjectAnimator yAnim = AnimationUtils.ofFloat(rootView, "y", animationValue[4], animationValue[5]);
        ObjectAnimator scaleXAnim = AnimationUtils.ofFloat(rootView, "scaleX", animationValue[6], animationValue[7]);
        ObjectAnimator scaleYAnim = AnimationUtils.ofFloat(rootView, "scaleY", animationValue[8], animationValue[9]);
        animatorSet.setDuration(1000 * 4);
        animatorSet.setInterpolator(sDecelerateInterpolator);
        animatorSet.playTogether(alphaAnim, xAnim, yAnim, scaleXAnim, scaleYAnim);
        return animatorSet;
    }



}
