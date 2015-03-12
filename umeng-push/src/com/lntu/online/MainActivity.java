/**
 * Copyright (C) 2013 Umeng, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lntu.online;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.tag.TagManager;

public class MainActivity extends Activity {
	protected static final String TAG = MainActivity.class.getSimpleName();
	
	private EditText edTag, edAlias;
	private TextView tvStatus, infoTextView;
	private ImageView btnEnable;
	private Button btnaAddTag, btnListTag, btnAddAlias; 
	private ProgressDialog dialog;
	
	private PushAgent mPushAgent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		printKeyValue();
		
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.onAppStart();
		mPushAgent.enable(mRegisterCallback);
		
		tvStatus = (TextView) findViewById(R.id.tvStatus);
		btnEnable = (ImageView) findViewById(R.id.btnEnable);
		btnaAddTag = (Button) findViewById(R.id.btnAddTags);
		btnAddAlias = (Button) findViewById(R.id.btnAddAlias);
		btnListTag = (Button) findViewById(R.id.btnListTags);
		infoTextView = (TextView)findViewById(R.id.info);
		edTag = (EditText) findViewById(R.id.edTag);
		edAlias = (EditText) findViewById(R.id.edAlias);
		
		tvStatus.setOnClickListener(clickListener);
		btnEnable.setOnClickListener(clickListener);
		btnaAddTag.setOnClickListener(clickListener);
		btnListTag.setOnClickListener(clickListener);
		btnAddAlias.setOnClickListener(clickListener);

		updateStatus();
//		mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
	}

	private void printKeyValue() {
		//获取自定义参数
		Bundle bun = getIntent().getExtras();
		if (bun != null)
		{
			Set<String> keySet = bun.keySet();
			for (String key : keySet) {
				String value = bun.getString(key);
				Log.i(TAG, key + ":" + value);
			}
		}
		
	}

	private void switchPush(){		
		String info = String.format("enabled:%s  isRegistered:%s",
				mPushAgent.isEnabled(), mPushAgent.isRegistered());
		Log.i(TAG, "switch Push:" + info);

		btnEnable.setClickable(false);
		if (mPushAgent.isEnabled() || UmengRegistrar.isRegistered(MainActivity.this)) {
			mPushAgent.disable(mUnregisterCallback);
		} else {
			mPushAgent.enable(mRegisterCallback);
		}
	}
	
	private void updateStatus() {
		String pkgName = getApplicationContext().getPackageName();
		String info = String.format("enabled:%s  isRegistered:%s  DeviceToken:%s",
				mPushAgent.isEnabled(), mPushAgent.isRegistered(),
				mPushAgent.getRegistrationId());
		tvStatus.setText("应用包名："+pkgName+"\n"+info);
		
		btnEnable.setImageResource(mPushAgent.isEnabled()?R.drawable.open_button:R.drawable.close_button);
		btnEnable.setClickable(true);
		copyToClipBoard();
		
		Log.i(TAG, "updateStatus:" + String.format("enabled:%s  isRegistered:%s",
				mPushAgent.isEnabled(), mPushAgent.isRegistered()));
		Log.i(TAG, "=============================");
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void copyToClipBoard()
	{
		if (Build.VERSION.SDK_INT<11) 
			return;
		String deviceToken = mPushAgent.getRegistrationId();
		if (!TextUtils.isEmpty(deviceToken))
		{
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			clipboard.setText(deviceToken);
			toast("DeviceToken已经复制到剪贴板了");		
		}
	}
	
	// sample code to add tags for the device / user
	private void addTag() {
		String tag = edTag.getText().toString();
		if (TextUtils.isEmpty(tag))
		{
			toast("请先输入Tag");
			return;
		}
		if (!mPushAgent.isRegistered())
		{
			toast("抱歉，还未注册");
			return;
		}

		showLoading();
		new AddTagTask(tag).execute();
		hideInputKeyboard();
	}

	// sample code to add tags for the device / user
	private void listTags() {
		if (!mPushAgent.isRegistered())
		{
			toast("抱歉，还未注册");
			return;
		}
		showLoading();
		new ListTagTask().execute();
	}

	// sample code to add alias for the device / user
	private void addAlias() {
		String alias = edAlias.getText().toString();
		if (TextUtils.isEmpty(alias))
		{
			toast("请先输入Alias");
			return;
		}
		if (!mPushAgent.isRegistered())
		{
			toast("抱歉，还未注册");
			return;
		}
		showLoading();
		new AddAliasTask(alias).execute();
		hideInputKeyboard();
	}

	public void showLoading(){
		if (dialog == null){
			dialog = new ProgressDialog(this);
			dialog.setMessage("Loading");
		}
		dialog.show();
	}
	
	public void updateInfo(String info){
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
		infoTextView.setText(info);
	}
	
	public OnClickListener clickListener  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btnaAddTag){
				addTag();
			}else if (v == btnAddAlias){
				addAlias();
			}else if (v == btnListTag){
				listTags();
			}else if (v == btnEnable){
				switchPush();
			}else if (v == tvStatus) {
				updateStatus();
			}
		}
	};
	
	
	public Handler handler = new Handler();
	public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {
		
		@Override
		public void onRegistered(String registrationId) {
			// TODO Auto-generated method stub
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					updateStatus();
				}
			});
		}
	};
	
	public IUmengUnregisterCallback mUnregisterCallback = new IUmengUnregisterCallback() {
		
		@Override
		public void onUnregistered(String registrationId) {
			// TODO Auto-generated method stub
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					updateStatus();
				}
			});
		}
	};
	
	private Toast mToast;
	public void toast(String str){
		if (mToast == null)
			mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		mToast.setText(str);
		mToast.show();
	}
	
	
	class AddTagTask extends AsyncTask<Void, Void, String>{

		String tagString;
		String[] tags;
		public AddTagTask(String tag) {
			// TODO Auto-generated constructor stub
			tagString = tag;
			tags = tagString.split(",");
		}
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				TagManager.Result result = mPushAgent.getTagManager().add(tags);
				Log.d(TAG, result.toString());
				return result.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			edTag.setText("");
			updateInfo("Add Tag:\n" + result);
		}
	}
	
	class AddAliasTask extends AsyncTask<Void, Void, Boolean>{
		
		String alias;
		
		public AddAliasTask(String aliasString) {
			// TODO Auto-generated constructor stub
			this.alias = aliasString;
		}

		protected Boolean doInBackground(Void... params) {
			try {
				//return mPushAgent.addAlias(alias, ALIAS_TYPE.SINA_WEIBO);
				return mPushAgent.addAlias(alias, "USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (Boolean.TRUE.equals(result)) 
				Log.i(TAG, "alias was set successfully.");
			
			edAlias.setText("");
			updateInfo("Add Alias:" + (result?"Success":"Fail"));
		}

	}
	
	class ListTagTask extends AsyncTask<Void , Void, List<String>>{
		@Override
		protected List<String> doInBackground(Void... params) {
			List<String> tags = new ArrayList<String>();
			try {
				tags = mPushAgent.getTagManager().list();
				Log.d(TAG, String.format("list tags: %s", TextUtils.join(",", tags)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tags;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			StringBuilder info = new StringBuilder();
			info.append("Tags:\n");
			for (int i=0; i<result.size(); i++){
				String tag = result.get(i);
				info.append(tag+"\n");
			}
			info.append("\n");
			updateInfo(info.toString());
		}
	}

	public void hideInputKeyboard()
	{
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
		.hideSoftInputFromWindow(getCurrentFocus()
		.getWindowToken(),
		InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
