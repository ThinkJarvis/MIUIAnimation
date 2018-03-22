package com.xiaomi.animation.script;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.View;

import com.xiaomi.animation.LaunchApplication;
import com.xiaomi.animation.R;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2018/3/22.
 */

public class ClockScript extends IconScript {
    private static Context sContext;
    private static BitmapDrawable sDrawableClockPointerHour;
    private static BitmapDrawable sDrawableClockPointerMinute;
    private static BitmapDrawable sDrawableClockPointerSecond;
    private static BitmapDrawable sDrawableClockBackground;

    private static float sStartAngle = -180.0f;
    private static float sCircleAngle = 360.0f;

    private Rect mRect = null;
    private View mView;
    private ClockThread mClockThread = null;

    public ClockScript() {
        super();
        sContext = LaunchApplication.getAppContext();
        sDrawableClockPointerHour = getBitmapDrawable(R.mipmap.hour);
        sDrawableClockPointerMinute = getBitmapDrawable(R.mipmap.minute);
        sDrawableClockPointerSecond = getBitmapDrawable(R.mipmap.second);
        sDrawableClockBackground = getBitmapDrawable(R.mipmap.clock);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(sDrawableClockBackground.getBitmap(), null, getBounds(), mPaint);//画底图
        drawIndicator(canvas, mRect.centerX(), mRect.centerY(), mPaint);
    }

    @Override
    public void run(View view) {
        super.run(view);
        mView = view;
        mRect = getBounds();
        if (mClockThread == null) {
            mClockThread = new ClockThread(mView);
            mClockThread.start();
        }
    }



    @Override
    public void onStop() {
        if(mClockThread != null){
            mClockThread.stopRun();
            super.onStop();
            mClockThread.interrupt();
            mClockThread = null;
            mView = null;
        }
    }

    private BitmapDrawable getBitmapDrawable(int resouceId) {
        return (BitmapDrawable) sContext.getDrawable(resouceId);
    }

    private void drawIndicator(Canvas canvas, int centerX, int centerY, Paint p) {
        Time t = new Time();
        t.setToNow();

        double hourAngle = (t.hour + t.minute / 60.0f) / 12.0f * sCircleAngle + sStartAngle;
        drawPointer(canvas, sDrawableClockPointerHour, hourAngle, centerX, centerY);

        double minuteAngle = (t.minute + t.second / 60.0f) / 60.0f * sCircleAngle + sStartAngle;
        drawPointer(canvas, sDrawableClockPointerMinute, minuteAngle, centerX, centerY);

        double secondAngle = t.second / 60.0f * sCircleAngle + sStartAngle;
        drawPointer(canvas, sDrawableClockPointerSecond, secondAngle, centerX, centerY);

    }

    private void drawPointer(Canvas canvas, Drawable drawable, double angle, int x, int y) {
        canvas.save();
        canvas.rotate((float) angle, x, y);
        drawable.setBounds(
                (x - drawable.getIntrinsicWidth() / 2),
                (y - drawable.getIntrinsicHeight() / 2),
                (x + drawable.getIntrinsicWidth() / 2),
                (y + drawable.getIntrinsicHeight() / 2));
        drawable.draw(canvas);
        canvas.restore();
    }


    private static class ClockThread extends Thread {
        boolean running = true;
        WeakReference<View> mViewReference;

        public ClockThread(View view) {
            mViewReference = new WeakReference<View>(view);
        }

        public void stopRun() {
            running = false;
            mViewReference = null;
        }

        public void run() {
            while (running) {
                if (mViewReference != null && mViewReference.get() != null) {
                    synchronized (mViewReference.get()) {
                        mViewReference.get().postInvalidate();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }

        }
    }

}
