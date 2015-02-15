package com.lntu.online.activity;

import java.io.IOException;

import com.lntu.online.R;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MottoActivity extends Activity {

    private MediaPlayer maleVoice;
    private MediaPlayer femaleVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motto);
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

    public void onBtnMaleVoice(View view) {
        if (maleVoice != null) {
            maleVoice.start();
        }
    }

    public void onBtnFemaleVoice(View view) {
        if (femaleVoice != null) {
            femaleVoice.start();
        }
    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

}
