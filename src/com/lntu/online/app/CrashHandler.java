package com.lntu.online.app;

import java.lang.Thread.UncaughtExceptionHandler;

import com.lntu.online.CrashShowActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler instance; //��ǰ�쳣������-����ģʽ
	@SuppressWarnings("unused")
	private UncaughtExceptionHandler defaultHandler; //ϵͳĬ��δ�����쳣������
	private Context context; //Ӧ��������

	/**
	 * ���ع�����
	 */
	private CrashHandler() {}
	
	/**
	 * ��ȡ�쳣����������
	 */
	public static synchronized CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}
	
	/**
	 * �������
	 * @param context
	 */
	public void active(Context context) {
		this.context = context; 
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	/**
	 * δ��������쳣������ø÷�������
	 */
	@Override
	public void uncaughtException(Thread thread, final Throwable e) {
		//ϵͳĬ�ϴ����쳣
		//defaultHandler.uncaughtException(thread, e);

		//����ErrorShowActivity
        Intent intent = new Intent(context, CrashShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("e", e);
        intent.putExtras(bundle);
        context.startActivity(intent);

		//�˳�����
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}

}
