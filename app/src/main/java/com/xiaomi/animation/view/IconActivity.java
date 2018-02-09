package com.xiaomi.animation.view;

import android.content.Intent;
import android.os.Bundle;

import com.xiaomi.animation.info.IconInfo;
import com.xiaomi.animation.R;
import com.xiaomi.animation.Util;

public class IconActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }

    private void initViews() {
        setContentView(R.layout.activity_icon);
        BubbleTextView icon = (BubbleTextView) findViewById(R.id.icon_view);
        Intent intent = getIntent();
        IconInfo iconInfo = intent.getParcelableExtra("Icon_Info");
        icon.setCompoundDrawables(null, Util.getDrawableTopByID(this,iconInfo.getDrawableId()),null,null);
        icon.setText(iconInfo.getText());
    }

    @Override
    public void onBackPressed() {
        setResult(101);
        super.onBackPressed();
    }
}
