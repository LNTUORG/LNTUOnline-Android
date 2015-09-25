package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.lntu.online.R;
import org.lntu.online.ui.listener.NavigationFinishClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.feedback_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

}
