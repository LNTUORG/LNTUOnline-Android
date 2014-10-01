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
import com.lntu.online.model.client.ClientVersion;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends Activity {

    private DrawerLayout drawerLayout;
    private GridView gridView;
    private long firstBackKeyTime = 0; //�״η��ؼ�����ʱ���

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
            if (clz == null) { //����δʵ��
                Toast.makeText(MainActivity.this, "������δʵ�֣������ڴ�...", Toast.LENGTH_SHORT).show();
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
        case KeyEvent.KEYCODE_BACK: //���ؼ�
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else { //�����˳�
                long secondBackKeyTime = System.currentTimeMillis();
                if (secondBackKeyTime - firstBackKeyTime >2000) {
                    Toast.makeText(this, "�ٰ�һ�η�������", Toast.LENGTH_SHORT).show();
                    firstBackKeyTime = secondBackKeyTime;
                } else {
                    moveTaskToBack(true);
                    //finish();
                }
            }
            return true;
        case KeyEvent.KEYCODE_MENU: //�˵���
            toggleSidebar();
            return true;
        default:
            return super.onKeyDown(keyCode, event);
        }
    }

    public void onSlidingMenuItemClick(View view) {
        switch (view.getId()) {
        case R.id.action_main_browser:
            Uri uri = Uri.parse("http://60.18.131.133:11180/academic/index.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;
        case R.id.action_main_about:
            startActivity(new Intent(this, AboutActivity.class));
            break;
        case R.id.action_main_update:
            checkUpdate();
            break;
        case R.id.action_main_feedback:
        	startActivity(new Intent(this, AdviceActivity.class));
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
        .setTitle("ע��")
        .setMessage("��ȷ��Ҫע����ǰ�û���")
        .setPositiveButton("ȷ��", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("gotoMain", true);
                startActivity(intent);
                finish();
            }
        })
        .setNegativeButton("ȡ��", null)
        .show();
    }

    public void showExitDialog() {
        new AlertDialog.Builder(this)    
        .setTitle("�˳�")
        .setMessage("��ȷ��Ҫ�˳�Ӧ����")
        .setPositiveButton("ȷ��", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("ȡ��", null)
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
                	if (cv.getBuild() > AppInfo.getVersionCode()) { //�и���
                		showUpdateDialog(cv);
                	} else { //�޸���
                		showNoUpdateDialog();
                	}
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("��ʾ", msgs[0], msgs[1]);
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
                	if (cv.getBuild() > AppInfo.getVersionCode()) { //�и���
                		if (cv.isForced()) { //ǿ�Ƹ���
                    		showForcedUpdateDialog(cv);
                		} else { //��ǿ�Ƹ���
                    		showUpdateDialog(cv);
                		}
                	}
                } catch(Exception e) {
                	//��̨���¼�鲻��ʾ����
                }
            }

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				//���󲻴���
			}

        });
    }

    public void showUpdateDialog(final ClientVersion cv) {
        new AlertDialog.Builder(this)
        .setTitle("������ʾ")
        .setMessage("���°汾��v" + cv.getName() + "\n������־��\n" + cv.getMessage())
        .setPositiveButton("����", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(cv.getPublishUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        })
        .setNegativeButton("����", null)
        .show();
    }

    public void showForcedUpdateDialog(final ClientVersion cv) {    	
        new AlertDialog.Builder(this)
        .setTitle("������ʾ")
        .setMessage("���°汾��v" + cv.getName() + "\n������־��\n" + cv.getMessage())
        .setPositiveButton("����", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse(cv.getPublishUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                finish();
            }
        })
        .setNegativeButton("�˳�", new OnClickListener() {
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
        .setTitle("��ʾ")
        .setMessage("��ǰ�������°汾")
        .setPositiveButton("ȷ��", null)
        .show();
    }

}
