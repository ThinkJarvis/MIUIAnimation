package com.xiaomi.animation.script;

import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;

import com.xiaomi.animation.LaunchApplication;

/**
 * Created by admin on 2018/3/22.
 */

public class FrameScript extends IconScript{

    private PlayFrameThread mPlayFrameThread = null;

    private Drawable mStaticFrame;
    private Drawable mCurrentFrame;
    private String mAnimationDrawableName;


    public FrameScript(Drawable staticFrame, String drawableName) {
        mStaticFrame = staticFrame;
        mAnimationDrawableName = drawableName;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!isRunning) {
            onStop();
        }
        canvas.drawBitmap(drawableToBitmap(mCurrentFrame), null, getBounds(), mPaint);
    }

    @Override
    public void run(View view) {
        super.run(view);
        if (mPlayFrameThread == null) {
            mPlayFrameThread = new PlayFrameThread(view);
            mPlayFrameThread.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mCurrentFrame = mStaticFrame;
        if (mPlayFrameThread != null) {
            mPlayFrameThread.stopRun();
            mPlayFrameThread.interrupt();
            mPlayFrameThread = null;
        }
    }

    public Drawable getFirstFrame() {
        String firstFrameName = mAnimationDrawableName + "_00";
        return LaunchApplication.getContext().getResources().
                getDrawable(getDrawableId(firstFrameName,"mipmap"));
    }

    public static int getDrawableId(String drawableName,String defType) {
        return LaunchApplication.getContext().getResources().
                getIdentifier(drawableName,defType,LaunchApplication.getContext().getPackageName());
    }

    private class PlayFrameThread extends Thread {
        private boolean running = true;
        private View mView;
        private AnimationDrawable mAnimationDrawable;
        private int index = 0;
        public PlayFrameThread(View view) {
            mView = view;
            mAnimationDrawable = (AnimationDrawable) LaunchApplication.getContext().
                    getResources().getDrawable(
                            getDrawableId(mAnimationDrawableName,"drawable"));
        }

        public void stopRun() {
            index = 0;
            mAnimationDrawable = null;
            running = false;
            mView = null;
        }

        public void run() {
            while (running) {
                if (index == mAnimationDrawable.getNumberOfFrames()) {
                    stopRun();
                    return;
                }
                if (mView != null) {
                    mCurrentFrame = mAnimationDrawable.getFrame(index);
                    mView.postInvalidate();
                }
                index++;
                try {
                    Thread.sleep(mAnimationDrawable.getDuration(index));
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }


}
