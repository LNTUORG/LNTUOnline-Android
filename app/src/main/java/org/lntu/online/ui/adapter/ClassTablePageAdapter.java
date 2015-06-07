package org.lntu.online.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lntu.online.R;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.lntu.online.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class ClassTablePageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<View> viewPool;
    private int pageCount;

    public ClassTablePageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        // 计算page size
        DateTime nowTime = new DateTime();
       // if (nowTime) {

       // }
        DateTime startTime = new DateTime("2015-3-1");
        DateTime endTime = new DateTime("2015-9-1");

        Period p = new Period(startTime, endTime, PeriodType.days());

        ToastUtils.with(context).show(p.getDays() + "");

    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View convertView;
        if (viewPool == null) {
            viewPool = new ArrayList<View>();
            for (int n = 0; n < 3; n++) {
                View viewItem = inflater.inflate(R.layout.activity_class_table_page_item, container, false);

                viewPool.add(viewItem);
            }
        }


        convertView = viewPool.get(position % 3);


        container.removeView(convertView);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 忽略
    }

}
