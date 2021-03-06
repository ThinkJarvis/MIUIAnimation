package com.xiaomi.animation.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaomi.animation.R;
import com.xiaomi.animation.Util;
import com.xiaomi.animation.info.IconInfo;
import com.xiaomi.animation.script.ClockScript;
import com.xiaomi.animation.script.FrameScript;

import java.util.List;

import static com.xiaomi.animation.Util.DEBUG;
/**
 * Created by admin on 2018/1/25.
 */

public class GridAdapter extends BaseAdapter {

    private List<IconInfo> mIcons;

    private Context mContext;

    public GridAdapter(List<IconInfo> icons, Context context) {
        mIcons = icons;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mIcons == null ? -1 : mIcons.size();
    }

    @Override
    public Object getItem(int position) {
        return mIcons == null ? null : mIcons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mIcons == null ? -1 : mIcons.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gird_item_view, null);
        }
        IconInfo iconInfo = mIcons.get(position);
        BubbleTextView icon = (BubbleTextView) convertView;
        Drawable drawableTop = Util.getDrawableTopByID(mContext,iconInfo.getDrawableId());
        if (iconInfo.getText().equals("时钟")) {
            icon.setIcon(new ClockScript());
        }else if (iconInfo.getText().equals("微信")) {
            icon.setIcon(new FrameScript(drawableTop,"gome_icon_wechat"));
        }else {
            icon.setIcon(drawableTop);
        }
        iconInfo.setWidth(icon.getIconSize());
        iconInfo.setHeight(icon.getIconSize());
        icon.setText(iconInfo.getText());
        icon.setTag(iconInfo);
        if (DEBUG) {
            if (position % 2 == 0) {
                icon.setBackgroundColor(Color.parseColor("#60225ACA"));
            } else {
                icon.setBackgroundColor(Color.parseColor("#6022CA2A"));
            }
        }
        icon = null;
        iconInfo = null;
        return convertView;
    }
}