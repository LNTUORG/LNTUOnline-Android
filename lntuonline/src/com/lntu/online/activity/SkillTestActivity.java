package com.lntu.online.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.ClientSkillTestScore;
import com.lntu.online.util.JsonUtil;

public class SkillTestActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_test);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        
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
        HttpUtil.get(this, NetworkInfo.serverUrl + "grades/skillTestScoresInfo", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {                    
                    List<ClientSkillTestScore> cstss = JsonUtil.fromJson(responseString, new TypeToken<List<ClientSkillTestScore>>(){}.getType());
                    ListView lvRoot = (ListView) findViewById(R.id.skill_test_lv_root);
                    lvRoot.setAdapter(new ListViewAdapter(getContext(), cstss));
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    if (msgs[0].equals("0x01050003")) {
                        showNothingDialog();
                    } else {
                        showErrorDialog("提示", msgs[0], msgs[1]);
                    }
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });
    }
    
    private void showNothingDialog() {
        new AlertDialog.Builder(this)    
        .setTitle("提示")
        .setMessage("您还没有参加过技能考试呢，赶快去报名啊~~")
        .setCancelable(false)
        .setPositiveButton("确定", new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        })
        .show();
    }

    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, List<ClientSkillTestScore> cstss) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            for (int n = 0; n < cstss.size(); n++) {
                ClientSkillTestScore csts = cstss.get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_skill_test_item, null);
                TextView tvName = (TextView) itemView.findViewById(R.id.skill_test_item_tv_name);
                TextView tvTime = (TextView) itemView.findViewById(R.id.skill_test_item_tv_time);
                TextView tvScore = (TextView) itemView.findViewById(R.id.skill_test_item_tv_score);
                tvName.setText(csts.getName() + "");
                tvTime.setText(csts.getTime() + "");
                tvScore.setText(csts.getScore() + "");
                //填充布局
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

}
