package com.lntu.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.info.AppInfo;
import com.lntu.online.info.ModuleInfo;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.ClientVersion;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends Activity {

    private DrawerLayout drawerLayout;
    private GridView gridView;
    private long firstBackKeyTime = 0; //首次返回键按下时间戳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //drawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        //GridView
        gridView = (GridView) findViewById(R.id.main_grid_view);
        gridView.setAdapter(new GridViewAdapter(this));
        gridView.setOnItemClickListener(new GridViewItemClickListener());
        //checkUpdate
        checkUpdateBackground();        
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getBooleanExtra("is_goback_login", false)) { //返回登陆页面
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private class GridViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public GridViewAdapter(Context context) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < ModuleInfo.getCount(); n++) {
                View itemView = inflater.inflate(R.layout.activity_main_body_gv_item, null);
                ImageView iv = (ImageView) itemView.findViewById(R.id.main_gv_item_iv_icon);
                iv.setImageResource(ModuleInfo.getIconResAt(n));
                TextView tv = (TextView) itemView.findViewById(R.id.main_gv_item_tv_title);
                tv.setText(ModuleInfo.getTitleAt(n));
                itemViews.add(itemView);
            }
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public Object getItem(int position) {
            return itemViews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return itemViews.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return itemViews.get(position);
        }

    }

    private class GridViewItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Class<?> clz = ModuleInfo.getClassAt(position);
            if (clz == null) { //功能未实现
                Toast.makeText(MainActivity.this, "功能暂未实现，敬请期待...", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(MainActivity.this, clz));
            }
        }

    }

    public void onActionBarIconMenu(View view) {
        toggleSidebar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK: //返回键
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else { //两次退出
                long secondBackKeyTime = System.currentTimeMillis();
                if (secondBackKeyTime - firstBackKeyTime > 2000) {
                    Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                    firstBackKeyTime = secondBackKeyTime;
                } else {
                    moveTaskToBack(true);
                    //finish();
                }
            }
            return true;
        case KeyEvent.KEYCODE_MENU: //菜单键
            toggleSidebar();
            return true;
        default:
            return super.onKeyDown(keyCode, event);
        }
    }

    public void onSlidingMenuItemClick(View view) {
        switch (view.getId()) {
        case R.id.action_main_browser: {
            Uri uri = Uri.parse("http://60.18.131.131:11180/academic/index.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;
        }
        case R.id.action_main_about:
            startActivity(new Intent(this, AboutActivity.class));
            break;
        case R.id.action_main_update:
            checkUpdate();
            break;
        case R.id.action_main_feedback:
            startActivity(new Intent(this, AdviceActivity.class));
            break;
        case R.id.action_main_market: {//跳转到市场
                //这里开始执行一个应用市场跳转逻辑，默认this为Context上下文对象
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getPackageName())); //跳转到应用市场，非Google Play市场一般情况也实现了这个接口
                //存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
                if (intent.resolveActivity(getPackageManager()) != null) { //可以接收
                    startActivity(intent);
                } else { //没有应用市场，我们通过浏览器跳转到Google Play
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    //这里存在一个极端情况就是有些用户浏览器也没有，在判断一次
                    if (intent.resolveActivity(getPackageManager()) != null) { //有浏览器
                        startActivity(intent);
                    } else { //天哪，这还是智能手机吗？
                        Toast.makeText(this, "您没应用市场，也没浏览器，开发者给您跪了...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        case R.id.action_main_logout:
            showLogoutDialog();
            break;
        case R.id.action_main_exit:
            showExitDialog();
            break;
        }
    }

    public void toggleSidebar() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    public void showLogoutDialog() {
        new AlertDialog.Builder(this)
        .setTitle("注销")
        .setMessage("您确定要注销当前用户吗？")
        .setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("gotoMain", true);
                startActivity(intent);
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }

    public void showExitDialog() {
        new AlertDialog.Builder(this)    
        .setTitle("退出")
        .setMessage("您确定要退出应用吗？")
        .setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
    
    private void checkUpdate() {
        RequestParams params = new RequestParams();
        params.put("platform", "android");
        HttpUtil.post(this, NetworkInfo.serverUrl + "version/stable", params, new NormalAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {       
                try {
                    ClientVersion cv = ClientVersion.dao.fromJson(responseString);
                    if (cv.getBuild() > AppInfo.getVersionCode()) { //有更新
                        showUpdateDialog(cv);
                    } else { //无更新
                        showNoUpdateDialog();
                    }
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

        });
    }

    private void checkUpdateBackground() {
        RequestParams params = new RequestParams();
        params.put("platform", "android");
        HttpUtil.baseGet(this, NetworkInfo.serverUrl + "version/stable", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {       
                try {
                    ClientVersion cv = ClientVersion.dao.fromJson(responseString);
                    if (cv.getBuild() > AppInfo.getVersionCode()) { //有更新
                        if (cv.isForced()) { //强制更新
                            showForcedUpdateDialog(cv);
                        } else { //非强制更新
                            showUpdateDialog(cv);
                        }
                    }
                } catch(Exception e) {
                    //后台更新检查不提示错误
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //错误不处理
            }

        });
    }

    public void showUpdateDialog(final ClientVersion cv) {
        new AlertDialog.Builder(this)
        .setTitle("更新提示")
        .setMessage("有新版本：v" + cv.getName() + "\n更新日志：\n" + cv.getMessage())
        .setPositiveButton("下载", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(cv.getPublishUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        })
        .setNegativeButton("忽略", null)
        .show();
    }

    public void showForcedUpdateDialog(final ClientVersion cv) {        
        new AlertDialog.Builder(this)
        .setTitle("更新提示")
        .setMessage("有新版本：v" + cv.getName() + "\n更新日志：\n" + cv.getMessage())
        .setPositiveButton("下载", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(cv.getPublishUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                finish();
            }
        })
        .setNegativeButton("退出", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setCancelable(false)
        .show();
    }

    public void showNoUpdateDialog() {
        new AlertDialog.Builder(this)
        .setTitle("提示")
        .setMessage("当前已是最新版本")
        .setPositiveButton("确定", null)
        .show();
    }

}
