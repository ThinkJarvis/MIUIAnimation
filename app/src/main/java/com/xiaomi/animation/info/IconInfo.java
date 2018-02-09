package com.xiaomi.animation.info;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/1/25.
 */

public class IconInfo  implements Parcelable{
    private Integer drawableId;
    private String text;
    private RectF rectF;
    private int width;
    private int height;

    public IconInfo() {

    }

    protected IconInfo(Parcel in) {
        drawableId = in.readInt();
        text = in.readString();
        rectF = in.readParcelable(getClass().getClassLoader());
        width = in.readInt();
        height = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drawableId);
        dest.writeString(text);
        dest.writeParcelable(rectF,Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IconInfo> CREATOR = new Creator<IconInfo>() {
        @Override
        public IconInfo createFromParcel(Parcel in) {
            return new IconInfo(in);
        }

        @Override
        public IconInfo[] newArray(int size) {
            return new IconInfo[size];
        }
    };

    public Integer getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(Integer drawableId) {
        this.drawableId = drawableId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
