package org.lntu.online.display.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.lntu.online.R;
import org.lntu.online.display.base.StatusBarActivity;
import org.lntu.online.display.listener.NavigationFinishClickListener;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.DialogCallback;
import org.lntu.online.model.storage.LoginShared;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.client.Response;

public class FeedbackActivity extends StatusBarActivity implements Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.edt_content)
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
        ApiClient.service.advice(LoginShared.getLoginToken(this), editText.getText().toString(), new DialogCallback<Void>(this) {

            @Override
            public void handleSuccess(Void nothing, Response response) {
                Toast.makeText(FeedbackActivity.this, "发送成功，非常感谢~", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

}
