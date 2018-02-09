package com.xiaomi.animation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.xiaomi.animation.transition.MIUIActivityTransition;

/**
 * Created by admin on 2018/2/1.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new MIUIActivityTransition(MIUIActivityTransition.ENTER_TRANSITION, getIntent()));
        getWindow().setReturnTransition(new MIUIActivityTransition(MIUIActivityTransition.RETURN_TRANSITION, getIntent()));
        super.onCreate(savedInstanceState);
    }

}
