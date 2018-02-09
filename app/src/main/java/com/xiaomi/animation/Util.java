package com.xiaomi.animation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.xiaomi.animation.info.IconInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/25.
 */

public class Util {
    public static final boolean DEBUG = false;

    public static RectF getDrawTopRect(TextView textView) {
        RectF rectF =new RectF();
        int left = textView.getLeft() + textView.getPaddingLeft();
        int top = textView.getTop() + textView.getPaddingTop();
        int right = textView.getRight() - textView.getPaddingRight();
        int bottom =textView.getTop() +  textView.getExtendedPaddingTop() - textView.getCompoundDrawablePadding();
        rectF.set(left,top,right,bottom);
        Log.e("louhan","rectF = " + rectF.toString());
        return rectF;
    }

    public static RectF getTextViewRect(TextView textView) {
        RectF rectF =new RectF();
        int left = textView.getLeft();
        int top = textView.getTop() + textView.getPaddingTop() + 62;
        int right = textView.getRight();
        int bottom =textView.getBottom();
        rectF.set(left,top,right,bottom);
        return rectF;
    }

    public static Drawable getDrawableTopByID(Context context, int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        return drawable;
    }


    public static List<IconInfo> foundIconList(Context context) {
        List<IconInfo> pairs = new ArrayList<>();
        Pair<ApplicationInfo, Integer> r = getIconArrayResourceId(context);
        if (r != null) {
            try {
                Resources iconRes = context.getPackageManager()
                        .getResourcesForApplication(r.first);
                addIconArrayId(pairs, iconRes, r.first.packageName, r.second);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return pairs;
    }

    private static void addIconArrayId(List<IconInfo> known, Resources res,
                                String packageName, int listResId) {
        IconInfo iconInfo;
        final String[] extras = res.getStringArray(listResId);
        for (String extra : extras) {
            String[]  iconInfoExtra = extra.split("-");
            String drawableIdExtra = iconInfoExtra[0];
            int drawableId = res.getIdentifier(drawableIdExtra, "mipmap", packageName);
            String text = iconInfoExtra[1];
            iconInfo = new IconInfo();
            iconInfo.setDrawableId(drawableId);
            iconInfo.setText(text);
            if (drawableId != 0) {
                known.add(iconInfo);
            } else {
                Log.e("mainActivity", "Couldn't find icon " + extra);
            }
        }
    }


    private static Pair<ApplicationInfo, Integer> getIconArrayResourceId(Context context) {
        // Context.getPackageName() may return the "original" package name,
        // com.android.launcher3; Resources needs the real package name,
        // com.android.launcher3. So we ask Resources for what it thinks the
        // package name should be.
        final String packageName = context.getResources().getResourcePackageName(R.array.icons);
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return new Pair<ApplicationInfo, Integer>(info, R.array.icons);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
