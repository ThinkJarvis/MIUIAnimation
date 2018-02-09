package com.xiaomi.animation;

import android.animation.Animator;
import android.graphics.RectF;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.GridView;

import com.xiaomi.animation.Info.IconInfo;
import com.xiaomi.animation.PropertyAnimator.ViewPropertyAnimator;
import com.xiaomi.animation.view.BubbleTextView;

/**
 * Created by admin on 2018/1/29.
 */

public class LauncherTransition extends Transition {
    private final static String FLAG = "flag";
    private final static String VIEW_RECT = "view_rect";
    private final static String VIEW_TAG = "rootView";

    public LauncherTransition() {

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
        final View animationView = endValues.view;
        BubbleTextView bubbleTextView = findClickedBubbleTextView(animationView);
        if (bubbleTextView != null) {
            Log.e("wjq", "createAnimator = " + ((IconInfo) bubbleTextView.getTag()).getText());
            final RectF rect = Util.getDrawTopRect(bubbleTextView);
            animationView.setPivotX(rect.centerX());
            animationView.setPivotY(rect.centerY());
            ViewPropertyAnimator viewPropertyAnimator = new ViewPropertyAnimator(animationView);
            viewPropertyAnimator.setInterpolator(new AccelerateInterpolator());
            viewPropertyAnimator
                    .scaleX(1.4f).scaleY(1.4f)
                    .alpha(0f)
                    .setDuration(800)
                    .start();

            return viewPropertyAnimator;
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return new String[]{FLAG};
    }

    private void captureValues(TransitionValues transitionValues, int type) {
        View view = transitionValues.view;
        if (VIEW_TAG.equals(view.getTag())) {
            transitionValues.values.put(FLAG, type);
        }
    }


    private BubbleTextView findClickedBubbleTextView(View root) {
        BubbleTextView bubbleTextView = null;
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) instanceof GridView) {
                    GridView gridView = (GridView) parent.getChildAt(i);
                    for (int j = 0; j < gridView.getChildCount(); j++) {
                        View child = gridView.getChildAt(j);
                        boolean flag = false;
                        Object object = child.getTag(child.getId());
                        if (object != null) {
                            flag = (boolean) object;
                        }
                        if (flag && child instanceof BubbleTextView) {
                            bubbleTextView = (BubbleTextView) child;
                        }
                    }
                }else {
                    throw new RuntimeException("instanceof GridView error");
                }
            }
        }else {
            throw new RuntimeException("instanceof ViewGroup error");
        }

        return bubbleTextView;
    }
}
