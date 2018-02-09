package com.xiaomi.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import static com.xiaomi.animation.Util.DEBUG;

/**
 * Created by admin on 2018/1/25.
 */

public class BubbleTextView extends TextView {
    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
