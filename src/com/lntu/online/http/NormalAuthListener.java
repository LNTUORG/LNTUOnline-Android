package com.lntu.online.http;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.lntu.online.LoginActivity;

public class NormalAuthListener extends BaseListener {

    public NormalAuthListener(Context context) {
        super(context, true);
    }
    
    @Override
    public void onCancel() {
        Toast.makeText(getContext(), "��������ȡ��", Toast.LENGTH_SHORT).show();
    }
    
    @Override
	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
    	if (statusCode == 500) {
        	showErrorDialog("�������", "0x03010500", "�������ڲ�����������");
    	}
    	else if (statusCode == 502) {
        	showErrorDialog("�������", "0x03010502", "���������ش���������");
    	}
    	else if (throwable instanceof SocketTimeoutException) {
        	showErrorDialog("�������", "0x03010002", "���������ӳ�ʱ��������");
    	}
    	else if (throwable instanceof IOException) {
    		showErrorDialog("�������", "0x03010001", "����ͨ��ʧ�ܣ�������������");
    	} else {
        	showErrorDialog("�������", "0x03010" + statusCode, "������ʴ���������");
    	}
	}

	protected void showErrorDialog(String title, String code, String message) {
        if (code.equals("0x02010001")) { //�û��Ựδ����
            new AlertDialog.Builder(getContext())    
            .setTitle(title)
            .setMessage("�û��Ự�ѹ��ڣ������µ�¼" + "\n" + "������룺" + code)
            .setCancelable(false)
            .setPositiveButton("ȷ��", new OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }

            })
            .show();
        } else {
            new AlertDialog.Builder(getContext())    
            .setTitle(title)
            .setMessage(message + "\n" + "������룺" + code)
            .setPositiveButton("ȷ��", null)
            .show();
        }
    }

}
