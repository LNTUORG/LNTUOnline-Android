package com.lntu.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);
        TextView tvNames = (TextView) findViewById(R.id.thank_tv_names);
        String names = "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "";
        tvNames.setText(names);
    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

}
