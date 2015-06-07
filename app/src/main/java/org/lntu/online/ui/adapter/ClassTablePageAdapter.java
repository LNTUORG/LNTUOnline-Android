package org.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lntu.online.R;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.DayInWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassTablePageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<View> viewPool;

    private DateTime nowTime;
    private DateTime startTime;
    private DateTime endTime;
    private DateTime firstWeekMonday;

    private ClassTable classTable;
    private Map<String, List<ClassTable.Course>> classTableMap;

    public ClassTablePageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        nowTime = new DateTime();
        if (nowTime.getMonthOfYear() >= 3 && nowTime.getMonthOfYear() < 9) { // 春季
            startTime = new DateTime(nowTime.getYear() + "-3-1");
            endTime = new DateTime(nowTime.getYear() + "-9-1");
        } else { // 秋季
            startTime = new DateTime(nowTime.getYear() + "-9-1");
            endTime = new DateTime(nowTime.getYear() + 1 + "-3-1");
        }
    }

    public void setDataSet(ClassTable classTable, Map<String, List<ClassTable.Course>> classTableMap) {
        this.classTable = classTable;
        this.classTableMap = classTableMap;
        firstWeekMonday = new DateTime(classTable.getFirstWeekMondayAt());
        if (firstWeekMonday.getDayOfWeek() != 1) { // 不是星期1
            firstWeekMonday = firstWeekMonday.minusDays(firstWeekMonday.getDayOfWeek() - 1);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        Period period = new Period(startTime, endTime, PeriodType.days());
        return period.getDays();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        DateTime currentTime = startTime.plusDays(position);
        String title = currentTime.getMonthOfYear() + "月" + currentTime.getDayOfMonth() + "日 " + DayInWeek.getFromIndex(currentTime.getDayOfWeek()).value() + "（第" + getWeekOfTerm(currentTime) + "周）";
        if (currentTime.getDayOfYear() == nowTime.getDayOfYear()) { // 是今天-主题色标注
            SpannableString spanString = new SpannableString(title);
            ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.color_primary));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spanString;
        } else {
            return title;
        }
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

    @Override
    public void notifyDataSetChanged() {
        // TODO
        super.notifyDataSetChanged();
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public DateTime getNowTime() {
        return nowTime;
    }

    public int getNowTimePosition() {
        Period period = new Period(startTime, nowTime, PeriodType.days());
        return period.getDays();
    }

    public int getWeekOfTerm(DateTime time) {
        if (firstWeekMonday != null) {
            Period period = new Period(firstWeekMonday, time, PeriodType.days());
            int days = period.getDays();
            return days >= 0 ? days / 7 + 1 : 0;
        } else {
            return 0;
        }
    }

}
