package org.lntu.online.ui.activity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.lntu.online.R;
import org.lntu.online.ui.base.BaseActivity;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class MottoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    private MediaPlayer maleVoice;
    private MediaPlayer femaleVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motto);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init media
        maleVoice = new MediaPlayer();
        try {
            AssetFileDescriptor fd = getResources().openRawResourceFd(R.raw.motto_male_voice);
            maleVoice.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            maleVoice.prepare();
        } catch (IOException e) {
            maleVoice = null;
        }
        femaleVoice = new MediaPlayer();
        try {
            AssetFileDescriptor fd = getResources().openRawResourceFd(R.raw.motto_female_voice);
            femaleVoice.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            femaleVoice.prepare();
        } catch (IOException e) {
            femaleVoice = null;
        }
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

    @Override
    protected void onPause() {
        if (maleVoice != null) {
            maleVoice.stop();
        }
        if (femaleVoice != null) {
            femaleVoice.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (maleVoice != null) {
            maleVoice.release();
        }
        if (femaleVoice != null) {
            femaleVoice.release();
        }
        super.onDestroy();
    }

    @OnClick(R.id.motto_btn_male_voice)
    protected void onBtnMaleVoiceClick() {
        if (maleVoice != null) {
            maleVoice.start();
        }
    }

    @OnClick(R.id.motto_btn_female_voice)
    protected void onBtnFemaleVoiceClick() {
        if (femaleVoice != null) {
            femaleVoice.start();
        }
    }

}
