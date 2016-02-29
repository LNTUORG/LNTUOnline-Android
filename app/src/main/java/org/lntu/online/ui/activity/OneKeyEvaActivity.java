package org.lntu.online.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.CourseEvaInfo;
import org.lntu.online.storage.LoginShared;
import org.lntu.online.ui.adapter.OneKeyEvaAdapter;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.dialog.DialogUtils;
import org.lntu.online.ui.dialog.ProgressDialog;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.ShipUtils;
import org.lntu.online.ui.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OneKeyEvaActivity extends StatusBarActivity {

    @Bind(R.id.one_key_eva_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.one_key_eva_layout_content)
    protected ViewGroup layoutContent;

    @Bind(R.id.one_key_eva_icon_loading)
    protected View iconLoading;

    @Bind(R.id.one_key_eva_icon_empty)
    protected View iconEmpty;

    @Bind(R.id.one_key_eva_icon_loading_anim)
    protected View iconLoadingAnim;

    @Bind(R.id.one_key_eva_tv_load_failed)
    protected TextView tvLoadFailed;

    @Bind(R.id.one_key_eva_recycler_view)
    protected RecyclerView recyclerView;

    @Bind(R.id.one_key_eva_fab)
    protected FloatingActionButton fab;

    private OneKeyEvaAdapter adapter;
    private List<CourseEvaInfo> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_eva);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

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

    private void startNetwork() {
        ApiClient.service.getCourseEvaInfoList(LoginShared.getLoginToken(this), new BackgroundCallback<List<CourseEvaInfo>>(this) {

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

    @OnClick(R.id.one_key_eva_fab)
    protected void onBtnFabClick() {
        int n = 0;
        for (CourseEvaInfo info : infoList) {
            if (!info.isDone()) {
                n++;
            }
        }
        if (n <= 0) { //不需要评估
            DialogUtils.createAlertDialogBuilder(this)
                    .setMessage("您的课程都已经评价完成了。")
                    .setPositiveButton("确定", null)
                    .show();
        } else { //需要评估
            DialogUtils.createAlertDialogBuilder(this)
                    .setMessage("您有" + n + "门课程需要评价。只有所有的课程完成评价之后，才能够正常查询成绩信息。点击【评价】按钮将会授权应用为您自动全部评价为好评。您也可以通过浏览器登录教务在线手动评价。\n\n您是否授权应用为您自动评价课程呢？")
                    .setPositiveButton("评价", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = DialogUtils.createProgressDialog(OneKeyEvaActivity.this);
                            progressDialog.setMessage(R.string.networking);
                            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    ToastUtils.with(OneKeyEvaActivity.this).show("评课任务已停止");
                                }

                            });
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            startEvaCourse(0, progressDialog);
                        }

                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    }

    private void startEvaCourse(final int current, final ProgressDialog progressDialog) {
        if (current == infoList.size()) { //评价已经完成
            progressDialog.dismiss();
            int n = 0;
            for (CourseEvaInfo info : infoList) {
                if (!info.isDone()) {
                    n++;
                }
            }
            if (n <= 0) { //不需要评估
                DialogUtils.createAlertDialogBuilder(this)
                        .setMessage("您的课程都已经评价完成了。\n不给我们一个好评吗？")
                        .setPositiveButton("给好评", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShipUtils.openInAppStore(OneKeyEvaActivity.this);
                            }

                        })
                        .setNegativeButton("不评价", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtils.with(OneKeyEvaActivity.this).show("呜呜呜~~~o(>_<)o");
                            }

                        })
                        .show();
            } else { //需要评估
                DialogUtils.createAlertDialogBuilder(this)
                        .setMessage("您有" + n + "门课程评价失败，您可以再试一次。")
                        .setPositiveButton("确定", null)
                        .show();
            }
        } else { //没评完
            final CourseEvaInfo info = infoList.get(current);
            if (info.isDone()) { //不需要评价，跳过
                startEvaCourse(current + 1, progressDialog);
            } else { //需要评价
                progressDialog.setMessage("正在评价：" + info.getName());
                ApiClient.service.doCourseEva(LoginShared.getLoginToken(this), info.getEvaKey(), new Callback<Void>() {

                    @Override
                    public void success(Void nothing, Response response) {
                        if (progressDialog.isShowing()) {
                            info.setDone(true);
                            adapter.notifyItemChanged(current);
                            startEvaCourse(current + 1, progressDialog);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (progressDialog.isShowing()) {
                            startEvaCourse(current + 1, progressDialog);
                        }
                    }

                });
            }
        }
    }

}
