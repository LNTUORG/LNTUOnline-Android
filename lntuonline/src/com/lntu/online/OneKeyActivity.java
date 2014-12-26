package com.lntu.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class OneKeyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key);

    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

}
