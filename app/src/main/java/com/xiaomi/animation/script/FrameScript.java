package com.xiaomi.animation.script;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by admin on 2018/3/22.
 */

public class FrameScript extends IconScript{

    private AnimationDrawable mAnimationDrawable;
    private PlayFrameThread mPlayFrameThread = null;
    private View mView;
    private static int index = 0;

    public FrameScript(Context context, int drawableId) {
        mAnimationDrawable = (AnimationDrawable) context.getResources().getDrawable(drawableId);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!isRunning) {
            onStop();
            drawBitmapByIndex(canvas,0);
        }else {
            drawBitmapByIndex(canvas,index);
        }
    }

    @Override
    public void run(View view) {
        super.run(view);
        mView = view;
        if (mPlayFrameThread == null) {
            mPlayFrameThread = new PlayFrameThread(view, mAnimationDrawable);
            mPlayFrameThread.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayFrameThread != null) {
            index = 0;
            mView.postInvalidate();
            mPlayFrameThread.stopRun();
            mPlayFrameThread.interrupt();
            mPlayFrameThread = null;
            mView = null;
        }
    }

    private void drawBitmapByIndex(Canvas canvas, int index) {
        Bitmap bitmap = drawableToBitmap(mAnimationDrawable.getFrame(index));
        canvas.drawBitmap(bitmap, null, getBounds(), mPaint);
    }


    private static class PlayFrameThread extends Thread {
        private boolean running = true;
        private View mView;
        private AnimationDrawable mAnimationDrawable;
        public PlayFrameThread(View view, AnimationDrawable animationDrawable) {
            mView = view;
            mAnimationDrawable = animationDrawable;
        }

        public void stopRun() {
            index = 0;
            running = false;
            mView = null;
        }

        public void run() {
            while (running) {
                index++;
                if (index == mAnimationDrawable.getNumberOfFrames()) {
                    stopRun();
                    return;
                }
                if (mView != null) {
                    mView.postInvalidate();
                }
                try {
                    Thread.sleep(mAnimationDrawable.getDuration(index));
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }


}
