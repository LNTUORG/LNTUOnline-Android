package com.lntu.online;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/** 
 * @author Luo
 * @time 2014年6月10日 下午3:13:43
 */
public class TestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	}
	
	public void gotoMainActivity(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
}
