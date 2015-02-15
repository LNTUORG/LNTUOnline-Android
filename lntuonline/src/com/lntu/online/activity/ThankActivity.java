package com.lntu.online.activity;

import com.lntu.online.R;

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
                "小马哥\n\n" +
                "兔子大叔\n\n" +
                "LZQ1993\n\n" +
                "晓风残月\n\n" +
                "浪子醉梦\n\n" +
                "关正\n\n" +
                "开源中国社区\n\n" +
                "新浪云计算\n\n" +
                "以及所有默默支持我们的你们\n";
        tvNames.setText(names);
    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

}
