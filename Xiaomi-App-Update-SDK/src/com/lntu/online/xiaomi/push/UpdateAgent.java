package com.lntu.online.xiaomi.push;

import android.content.Context;

import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;

public class UpdateAgent {

	public static void update(Context context) {
		XiaomiUpdateAgent.setUpdateAutoPopup(false);
		XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

		    @Override
		    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
		        switch (updateStatus) {
		        case UpdateStatus.STATUS_UPDATE:
		        	//有更新，UpdateResponse为本次更新的详细信息
		            	
		            	
		            	
		                
		        	break;
		        case UpdateStatus.STATUS_NO_UPDATE:
		        	//无更新，UpdateResponse为null
		            	
		            	
		            	
		                
		        	break;
		        case UpdateStatus.STATUS_NO_WIFI:
		        	//设置了只在WiFi下更新，且WiFi不可用时，UpdateResponse为null
		            	
		            	
		            	
		            	
		                
		        	break;
		        case UpdateStatus.STATUS_NO_NET:
		        	//没有网络，UpdateResponse为null
		            	
		            	
		            	
		            	
		                
		        	break;
		        case UpdateStatus.STATUS_FAILED:
		        	//检查更新与服务器通讯失败，可稍后再试，UpdateResponse为null
		            	
		            	
		            	
		                
		        	break;
		        case UpdateStatus.STATUS_LOCAL_APP_FAILED:
		        	//检查更新获取本地安装应用信息失败， UpdateResponse为null
		            	
		            	
		            	
		            	
		                
		        	break;
		        default:
		        	break;
		        }
		    }

		});
		XiaomiUpdateAgent.update(context);
	}

}
