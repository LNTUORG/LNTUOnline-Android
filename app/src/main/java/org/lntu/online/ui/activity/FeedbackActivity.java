package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.lntu.online.R;
import org.lntu.online.ui.fragment.ClassTablePageFragment;
import org.lntu.online.ui.listener.NavigationFinishClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeedbackActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @Bind(R.id.feedback_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.feedback_edt_content)
    protected EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.feedback);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                if (editText.length() <= 0) {
                    Toast.makeText(this, "不能啥也不写...", Toast.LENGTH_SHORT).show();
                } else {
                    adviceAsyncTask();
                }
                return true;
            default:
                return false;
        }
    }

    private void adviceAsyncTask() {

    }

}
