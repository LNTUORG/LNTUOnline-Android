package com.lntu.online.activity;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lntu.online.R;
import com.lntu.online.adapter.StudentInfoAdapter;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.ClientStudent;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class StudentInfoActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private ListView listView;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        listView = (ListView) findViewById(R.id.student_info_lv_root);
        LayoutInflater inflater = LayoutInflater.from(this);
        imgPhoto = (ImageView) inflater.inflate(R.layout.activity_student_info_item_photo, listView, false);
        listView.addHeaderView(imgPhoto);

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
        HttpUtil.get(this, NetworkInfo.serverUrl + "student/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ClientStudent cs = ClientStudent.dao.fromJson(responseString);
                    listView.setAdapter(new StudentInfoAdapter(getContext(), cs));
                    startPhotoDownload(cs.getPhotoUrl());
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });    
    }

    private void startPhotoDownload(String url) {
        HttpUtil.baseGet(this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Bitmap bmp = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                    imgPhoto.setImageBitmap(bmp);
                } catch(Exception e) {
                    Toast.makeText(StudentInfoActivity.this, "用户头像解析失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(StudentInfoActivity.this, "用户头像获取失败", Toast.LENGTH_SHORT).show();
            }

        });

    }

}
