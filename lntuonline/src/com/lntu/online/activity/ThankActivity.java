package com.lntu.online.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lntu.online.R;

public class ThankActivity extends ActionBarActivity {

	private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
