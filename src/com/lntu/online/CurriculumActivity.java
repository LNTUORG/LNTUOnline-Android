package com.lntu.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.NormalAuthListener;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.info.SecretKey;
import com.lntu.online.info.UserInfo;
import com.lntu.online.model.client.ClientCurriculum;
import com.lntu.online.util.Des3Util;

public class CurriculumActivity extends Activity {

    private static final String[] weekdayNames = {
        "周六", "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日", "周一", "周二"
    };

    private Time time;
    private String strTime;
    private ViewPager vpRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        //定义时间控件
        time = new Time();
        time.setToNow();
        strTime = time.year + "-" + (time.month + 1) + "-" + time.monthDay + " （" + weekdayNames[(time.weekDay == 0 ? 7 : time.weekDay) + 1] + "）";
        //ActionBar显示日期
        TextView tvActionBar = (TextView) findViewById(R.id.actionbar_title);
        tvActionBar.setText(strTime);
        //ViewPager
        vpRoot = (ViewPager) findViewById(R.id.curriculum_vp_root);
        vpRoot.setOnPageChangeListener(new ViewPagerPageChangeListener());
        //读取本地课表
        SharedPreferences sp = getSharedPreferences("curriculum_" + UserInfo.getSavedUserId(), Context.MODE_PRIVATE);
        try {
			ClientCurriculum cc = ClientCurriculum.dao.fromJson(Des3Util.decode(SecretKey.SP_KEY, sp.getString("json", "")));
            vpRoot.setAdapter(new ViewPagerAdapter(this, cc));
            vpRoot.setCurrentItem((time.weekDay == 0 ? 7 : time.weekDay) + 1);
			Toast.makeText(this, "上次更新时间为：" + sp.getString("update_time", "未知"), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
			startNetwork();
		}
    }

    private void startNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "curriculum/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ClientCurriculum cc = ClientCurriculum.dao.fromJson(responseString);
                    vpRoot.setAdapter(new ViewPagerAdapter(getContext(), cc));
                    vpRoot.setCurrentItem((time.weekDay == 0 ? 7 : time.weekDay) + 1);
                    //保存在本地
                    Editor editer = getSharedPreferences("curriculum_" + UserInfo.getSavedUserId(), Context.MODE_PRIVATE).edit();
                    editer.putString("json", Des3Util.encode(SecretKey.SP_KEY, responseString));
                    editer.putString("update_time", strTime);
                    editer.commit();
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });    
    }
    
    private void updateNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "curriculum/info", new NormalAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ClientCurriculum cc = ClientCurriculum.dao.fromJson(responseString);
                    vpRoot.setAdapter(new ViewPagerAdapter(getContext(), cc));
                    vpRoot.setCurrentItem((time.weekDay == 0 ? 7 : time.weekDay) + 1);
                    //保存在本地
                    Editor editer = getSharedPreferences("curriculum_" + UserInfo.getSavedUserId(), Context.MODE_PRIVATE).edit();
                    editer.putString("json", Des3Util.encode(SecretKey.SP_KEY, responseString));
                    editer.putString("update_time", strTime);
                    editer.commit();
                    //Toast
                    Toast.makeText(getContext(), "数据更新成功", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

        });    
    }
    
    public void onActionBarIconRefresh(View view) {
    	updateNetwork();
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private List<View> views;

        public ViewPagerAdapter(Context context, ClientCurriculum cc) {
            LayoutInflater inflater = LayoutInflater.from(context);
            views = new ArrayList<View>();
            for (int n = 0; n < weekdayNames.length; n++) {
                View view = inflater.inflate(R.layout.activity_curriculum_item, null);
                //时间
                TextView tvTime1 = (TextView) view.findViewById(R.id.curriculum_tv_time1);
                TextView tvTime2 = (TextView) view.findViewById(R.id.curriculum_tv_time2);
                TextView tvTime3 = (TextView) view.findViewById(R.id.curriculum_tv_time3);
                TextView tvTime4 = (TextView) view.findViewById(R.id.curriculum_tv_time4);
                TextView tvTime5 = (TextView) view.findViewById(R.id.curriculum_tv_time5);
                tvTime1.setText("第1节\n" + cc.getTimes().get(1) + "\n第2节\n" + cc.getTimes().get(2));
                tvTime2.setText("第3节\n" + cc.getTimes().get(3) + "\n第4节\n" + cc.getTimes().get(4));
                tvTime3.setText("第5节\n" + cc.getTimes().get(5) + "\n第6节\n" + cc.getTimes().get(6));
                tvTime4.setText("第7节\n" + cc.getTimes().get(7) + "\n第8节\n" + cc.getTimes().get(8));
                tvTime5.setText("第9节\n" + cc.getTimes().get(9) + "\n第10节\n" + cc.getTimes().get(10));
                //课程
                TextView tvCourse1 = (TextView) view.findViewById(R.id.curriculum_tv_course1);
                TextView tvCourse2 = (TextView) view.findViewById(R.id.curriculum_tv_course2);
                TextView tvCourse3 = (TextView) view.findViewById(R.id.curriculum_tv_course3);
                TextView tvCourse4 = (TextView) view.findViewById(R.id.curriculum_tv_course4);
                TextView tvCourse5 = (TextView) view.findViewById(R.id.curriculum_tv_course5);
                int w = ((n + 6) % 7 == 0 ? 7 : (n + 6) % 7);
                tvCourse1.setText(cc.getCourses().get(w + "-1"));
                tvCourse2.setText(cc.getCourses().get(w + "-2"));
                tvCourse3.setText(cc.getCourses().get(w + "-3"));
                tvCourse4.setText(cc.getCourses().get(w + "-4"));
                tvCourse5.setText(cc.getCourses().get(w + "-5"));
                //填充布局
                views.add(view);
            }
        }

        @Override  
        public Object instantiateItem(ViewGroup container, int position) {    
        	View view = views.get(position);
        	container.addView(view);  
            return view;
        }

        @Override  
        public void destroyItem(ViewGroup container, int position, Object object) {  
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
        	return weekdayNames.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return weekdayNames[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

    }
    
    private class ViewPagerPageChangeListener implements OnPageChangeListener {
    	
    	private int current = 0;
    	
		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			//判断current
			if (positionOffset == 0.0f) { //静止
				current = position;
			}
			else if (position >= current) {
				current = position;
			} 
			else if (current - position >= 2) {
				current = position + 1;
			}
			//根据current跳转
			if (current == 1) { //周日
				vpRoot.setCurrentItem(8, false);
			}
			else if (current == 9) { //周一
				vpRoot.setCurrentItem(2, false);
			}			
		}

		@Override
		public void onPageSelected(int position) {			
		}

    }

    public void onActionBarBtnLeft(View view) {
    	finish();
    }

}
