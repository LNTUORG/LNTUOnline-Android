package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.lntu.online.R;
import com.melnykov.fab.FloatingActionButton;

import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.CourseEvaInfo;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.adapter.OneKeyEvaAdapter;
import org.lntu.online.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class OneKeyEvaActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.one_key_eva_layout_content)
    protected ViewGroup layoutContent;

    @InjectView(R.id.one_key_eva_icon_loading)
    protected View iconLoading;

    @InjectView(R.id.one_key_eva_icon_empty)
    protected View iconEmpty;

    @InjectView(R.id.one_key_eva_icon_loading_anim)
    protected View iconLoadingAnim;

    @InjectView(R.id.one_key_eva_tv_load_failed)
    protected TextView tvLoadFailed;

    @InjectView(R.id.one_key_eva_recycler_view)
    protected RecyclerView recyclerView;

    @InjectView(R.id.one_key_eva_fab)
    protected FloatingActionButton fab;

    private OneKeyEvaAdapter adapter;
    private List<CourseEvaInfo> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_eva);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        infoList = new ArrayList<>();
        adapter = new OneKeyEvaAdapter(this, infoList);
        recyclerView.setAdapter(adapter);

        fab.attachToRecyclerView(recyclerView);

        startNetwork();
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

    private void startNetwork() {
        ApiClient.with(this).apiService.getCourseEvaInfoList(LoginShared.getLoginToken(this), new BackgroundCallback<List<CourseEvaInfo>>(this) {

            @Override
            public void handleSuccess(List<CourseEvaInfo> infoList, Response response) {
                if (infoList.size() == 0) {
                    showIconEmptyView("暂时没有评课信息。");
                } else {
                    OneKeyEvaActivity.this.infoList.clear();
                    OneKeyEvaActivity.this.infoList.addAll(infoList);
                    adapter.notifyDataSetChanged();
                    layoutContent.setVisibility(View.VISIBLE);
                    iconLoading.setVisibility(View.GONE);
                    iconEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void handleFailure(String message) {
                showIconEmptyView(message);
            }

        });
    }

    private void showIconEmptyView(String message) {
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.VISIBLE);
        tvLoadFailed.setText(message);
    }

    @OnClick(R.id.one_key_eva_icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

}
