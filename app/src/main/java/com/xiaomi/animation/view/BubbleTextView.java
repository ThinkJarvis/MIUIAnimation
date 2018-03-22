package com.xiaomi.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.xiaomi.animation.R;
import com.xiaomi.animation.script.ClockScript;
import com.xiaomi.animation.script.FrameScript;

import static com.xiaomi.animation.Util.DEBUG;

/**
 * Created by admin on 2018/1/25.
 */

public class BubbleTextView extends TextView {

    private int mIconSize;

    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIconSize = (int) context.getResources().getDimension(R.dimen.app_icon_size);
    }

    public void setIcon(Drawable icon) {
        icon.setBounds(0,0,mIconSize, mIconSize);
        setCompoundDrawables(null, icon, null, null);
    }

    public int getIconSize() {
        return mIconSize;
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (top != null && top instanceof ClockScript) {
            ClockScript clockScript = (ClockScript) top;
            if (!clockScript.isRunning) {
                clockScript.run(this);
            }
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void startFrameAnimation() {
        Drawable top = getDrawableTop();
        if (top != null && top instanceof FrameScript) {
            FrameScript frameScript = (FrameScript)top;
            if (!frameScript.isRunning) {
                frameScript.run(this);
            }
        }
    }

    public void stopFrameAnimation() {
        Drawable top = getDrawableTop();
        if (top != null && top instanceof FrameScript) {
            FrameScript frameScript = (FrameScript)top;
            if (frameScript.isRunning) {
                frameScript.onStop();
            }
        }
    }

    private Drawable getDrawableTop() {
        Drawable top = null;
        Drawable[] drawables = getCompoundDrawables();
        if (drawables.length < 2){
            return top;
        }
        top = getCompoundDrawables()[1];
        return top;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (DEBUG) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            RectF drawTopRect = new RectF();
            drawTopRect.set(getScrollX() + 0 + getPaddingLeft(),
                    getScrollY() + 0 + getPaddingTop(),
                    getScrollX() + getWidth() - getPaddingRight(),
                    getScrollY() + getExtendedPaddingTop() - getCompoundDrawablePadding());

            paint.setColor(Color.parseColor("#FFFF0004"));
            canvas.drawPoint(drawTopRect.centerX(), drawTopRect.centerY(), paint);
            paint.setColor(Color.parseColor("#60ca2222"));
            canvas.drawRect(drawTopRect, paint);
        }
    }
}
