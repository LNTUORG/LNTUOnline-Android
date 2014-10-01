package com.lntu.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommunityActivity extends Activity {

	String info = "" +
			"该模块还在建设中，设计的主要目的是为学校学生组织和社团组团提供一个自我展示，活动消息推送平台。实施方式目前正在讨论中。如果各位有好的建议和意见，欢迎通过以下方式联系我们：" +
            "\n" +
            "QQ：10771533\n" +
            "QQ：2318805370\n" +
            "\n" +
            "来意请注明“社团大联盟”，谢谢合作！\n" +
            "\n" +
            "2014.9.1 教务在线客户端团队\n" +
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
