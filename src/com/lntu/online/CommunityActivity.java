package com.lntu.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommunityActivity extends Activity {

	String info = "" +
			"��ģ�黹�ڽ����У���Ƶ���ҪĿ����ΪѧУѧ����֯�����������ṩһ������չʾ�����Ϣ����ƽ̨��ʵʩ��ʽĿǰ���������С������λ�кõĽ�����������ӭͨ�����·�ʽ��ϵ���ǣ�" +
            "\n" +
            "QQ��10771533\n" +
            "QQ��2318805370\n" +
            "\n" +
            "������ע�������Ŵ����ˡ���лл������\n" +
            "\n" +
            "2014.9.1 �������߿ͻ����Ŷ�\n" +
            "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        TextView tvInfo = (TextView) findViewById(R.id.community_tv_info);
        tvInfo.setText(info);
    }

    public void onActionBarBtnLeft(View view) {
    	finish();
    }

}
