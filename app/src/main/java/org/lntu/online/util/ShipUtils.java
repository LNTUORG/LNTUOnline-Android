package org.lntu.online.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.lntu.online.R;

public final class ShipUtils {

    private ShipUtils() {}

    public static void openInAppStore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("您的系统中没有安装应用商店");
        }
    }

    public static void share(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "辽工大教务在线客户端");
        intent.putExtra(Intent.EXTRA_SUBJECT, "辽工大教务在线客户端");
        intent.putExtra(Intent.EXTRA_TEXT, "辽工大的童鞋，推荐给你一个APP：辽工大教务在线客户端，查课表、查成绩、一键评课没有验证码，还有更多好玩的功能！我们工大人自己的掌上教务在线，下载地址：http://fir.im/LntuOnline");
        context.startActivity(Intent.createChooser(intent, "分享给好友"));
    }

    public static void openInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("您的系统中没有安装浏览器");
        }
    }

}
