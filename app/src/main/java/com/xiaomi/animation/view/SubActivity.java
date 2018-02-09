package com.xiaomi.animation.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.xiaomi.animation.R;
import com.xiaomi.animation.transition.MIUIActivityTransition;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new MIUIActivityTransition(0, getIntent()));
        getWindow().setReturnTransition(new MIUIActivityTransition(1, getIntent()));





        setContentView(R.layout.activity_sub);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
