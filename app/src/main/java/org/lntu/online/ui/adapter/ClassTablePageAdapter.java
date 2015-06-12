package org.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lntu.online.R;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.DayInWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ClassTablePageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<View> viewPool;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate firstWeekMonday;
    private LocalDate today;

    private ClassTable classTable;
    private Map<String, List<ClassTable.Course>> classTableMap;

    public ClassTablePageAdapter(Context context, int year, String term, LocalDate today) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if ("春".equals(term)) {
            startDate = new LocalDate(year, 3, 1);
            endDate = new LocalDate(year, 9, 1);
        } else {
            startDate = new LocalDate(year, 9, 1);
            endDate = new LocalDate(year + 1, 3, 1);
        }
        this.today = today;
    }

    public void updateDataSet(ClassTable classTable, Map<String, List<ClassTable.Course>> classTableMap) {
        this.classTable = classTable;
        this.classTableMap = classTableMap;
        firstWeekMonday = new LocalDate(classTable.getFirstWeekMondayAt());
        firstWeekMonday = firstWeekMonday.minusDays(firstWeekMonday.getDayOfWeek() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        Period period = new Period(startDate, endDate, PeriodType.days());
        return period.getDays();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getPositionFromDate(LocalDate date) {
        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            return 0;
        } else {
            Period period = new Period(startDate, date, PeriodType.days());
            return period.getDays();
        }
    }

    public LocalDate getDateAt(int position) {
        return startDate.plusDays(position);
    }

    public int getWeekOfTerm(LocalDate date) {
        if (firstWeekMonday == null ||firstWeekMonday.isBefore(startDate) || firstWeekMonday.isAfter(endDate)) {
            return -1;
        } else {
            Period period = new Period(firstWeekMonday, date, PeriodType.days());
            int days = period.getDays();
            return days < 0 ? 0 : days / 7 + 1;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        LocalDate currentDate = getDateAt(position);
        String title = currentDate.getMonthOfYear() + "月" + currentDate.getDayOfMonth() + "日";
        int weekOfTerm = getWeekOfTerm(currentDate);
        if (weekOfTerm > 0) {
            title += " " + DayInWeek.getFromIndex(currentDate.getDayOfWeek()).value() + "（第" + getWeekOfTerm(currentDate) + "周）";
        }
        if (currentDate.equals(today)) { // 是今天-主题色标注
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
            for (int n = 0; n < 7; n++) {
                View viewItem = inflater.inflate(R.layout.activity_class_table_page_item, container, false);
                viewItem.setTag(new ViewHolder(viewItem));
                viewPool.add(viewItem);
            }
        }
        convertView = viewPool.get(position % 7);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        LocalDate currentDate = getDateAt(position);
        holder.update(position, currentDate);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void notifyDataSetChanged() {
        if (viewPool != null) {
            for (View view : viewPool) {
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.update(holder.position, holder.currentDate);
            }
        }
        super.notifyDataSetChanged();
    }

    protected class ViewHolder {

        @InjectView(R.id.class_table_page_item_scroll_view)
        protected ScrollView scrollView;

        @InjectView(R.id.class_table_page_item_tv_stage_1)
        protected TextView tvStage1;

        @InjectView(R.id.class_table_page_item_tv_stage_2)
        protected TextView tvStage2;

        @InjectView(R.id.class_table_page_item_tv_stage_3)
        protected TextView tvStage3;

        @InjectView(R.id.class_table_page_item_tv_stage_4)
        protected TextView tvStage4;

        @InjectView(R.id.class_table_page_item_tv_stage_5)
        protected TextView tvStage5;

        protected int position;
        protected LocalDate currentDate;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

        protected void update(int position, LocalDate currentDate) {
            this.position = position;
            this.currentDate = currentDate;
            scrollView.scrollTo(0, 0);
            tvStage1.setText(ClassTable.getStageTimeString(1, currentDate));
            tvStage2.setText(ClassTable.getStageTimeString(2, currentDate));
            tvStage3.setText(ClassTable.getStageTimeString(3, currentDate));
            tvStage4.setText(ClassTable.getStageTimeString(4, currentDate));
            tvStage5.setText(ClassTable.getStageTimeString(5, currentDate));
        }

    }

}
