package org.lntu.online.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lntu.online.R;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.DayInWeek;
import org.lntu.online.model.gson.GsonWrapper;
import org.lntu.online.ui.activity.ClassTableCourseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
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
        if (firstWeekMonday == null || firstWeekMonday.isBefore(startDate) || firstWeekMonday.isAfter(endDate)) {
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
        String title = currentDate.getMonthOfYear() + "月" + currentDate.getDayOfMonth() + "日 " + DayInWeek.getFromIndex(currentDate.getDayOfWeek()).value();
        int weekOfTerm = getWeekOfTerm(currentDate);
        if (weekOfTerm > 0) {
            title += "（第" + getWeekOfTerm(currentDate) + "周）";
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
        View convertView = (View) object;
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.scrollView.scrollTo(0, 0);
        container.removeView(convertView);
    }

    @Override
    public void notifyDataSetChanged() {
        if (viewPool != null) {
            for (View view : viewPool) {
                ViewHolder holder = (ViewHolder) view.getTag();
                if (holder.position >= 0) {
                    holder.update(holder.position, holder.currentDate);
                }
            }
        }
        super.notifyDataSetChanged();
    }

    protected class ViewHolder {

        @InjectView(R.id.class_table_page_item_scroll_view)
        protected ScrollView scrollView;

        @InjectViews({
                R.id.class_table_page_item_tv_stage_1,
                R.id.class_table_page_item_tv_stage_2,
                R.id.class_table_page_item_tv_stage_3,
                R.id.class_table_page_item_tv_stage_4,
                R.id.class_table_page_item_tv_stage_5
        })
        protected List<TextView> tvStageList;

        @InjectViews({
                R.id.class_table_page_item_layout_stage_1,
                R.id.class_table_page_item_layout_stage_2,
                R.id.class_table_page_item_layout_stage_3,
                R.id.class_table_page_item_layout_stage_4,
                R.id.class_table_page_item_layout_stage_5
        })
        protected List<ViewGroup> layoutStageList;

        @InjectViews({
                R.id.class_table_page_item_icon_stage_1,
                R.id.class_table_page_item_icon_stage_2,
                R.id.class_table_page_item_icon_stage_3,
                R.id.class_table_page_item_icon_stage_4,
                R.id.class_table_page_item_icon_stage_5
        })
        protected List<View> iconStageList;

        protected int position = -1;
        protected LocalDate currentDate;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

        protected void update(int position, LocalDate currentDate) {
            this.position = position;
            this.currentDate = currentDate;
            if (classTableMap == null) {
                return;
            }
            for (int stage = 1; stage <= 5; stage++) {
                // 设置上课时间
                tvStageList.get(stage - 1).setText(ClassTable.getStageTimeString(stage, currentDate));
                // 获取今天这一大节的课程列表
                List<ClassTable.Course> courseList = classTableMap.get(currentDate.getDayOfWeek() + "-" + stage);
                ViewGroup layoutStage = layoutStageList.get(stage - 1);
                View iconStage = iconStageList.get(stage - 1);
                if (courseList == null || courseList.size() == 0) { // 没有课程
                    layoutStage.setVisibility(View.GONE);
                    iconStage.setVisibility(View.VISIBLE);
                } else { // 有课程
                    // 判断布局是否多余，删除多余
                    if (layoutStage.getChildCount() > courseList.size()) {
                        layoutStage.removeViews(0, layoutStage.getChildCount() - courseList.size());
                    }
                    // 遍历课程，生成布局
                    iconStage.setVisibility(View.VISIBLE); // 这里先标记为显示
                    for (int i = 0; i < courseList.size(); i++) {
                        ClassTable.Course course = courseList.get(i);
                        View viewCourse = layoutStage.getChildAt(i);
                        if (viewCourse == null) {
                            viewCourse = inflater.inflate(R.layout.activity_class_table_page_item_course, layoutStage, false);
                            viewCourse.setTag(new CourseViewHolder(viewCourse));
                            layoutStage.addView(viewCourse);
                        }
                        CourseViewHolder holder = (CourseViewHolder) viewCourse.getTag();
                        holder.update(course, currentDate.getDayOfWeek(), stage);
                        // 这里判断课程是否在时间段内，不在则隐藏
                        iconStage.setVisibility(View.GONE);
                        layoutStage.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }

    protected class CourseViewHolder {

        @InjectView(R.id.class_table_page_item_course_tv_name)
        protected TextView tvName;

        @InjectView(R.id.class_table_page_item_course_tv_teacher)
        protected TextView tvTeacher;

        @InjectView(R.id.class_table_page_item_course_tv_place)
        protected TextView tvPlace;

        protected ClassTable.Course course;

        public CourseViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

        public void update(ClassTable.Course course, int dayInWeek, int stage) {
            this.course = course;
            tvName.setText(course.getName());
            tvTeacher.setText(course.getTeacher());
            for (ClassTable.TimeAndPlace timeAndPlace : course.getTimesAndPlaces()) {
                if (timeAndPlace.getStage() == stage && timeAndPlace.getDayInWeek().index() == dayInWeek) {
                    tvPlace.setText(timeAndPlace.getRoom());
                    return;
                }
            }
            tvPlace.setText(null);
        }

        @OnClick(R.id.class_table_page_item_course_btn_card)
        protected void onBtnCardClick() {
            if (course != null) {
                Intent intent = new Intent(context, ClassTableCourseActivity.class);
                intent.putExtra("course", GsonWrapper.gson.toJson(course));
                intent.putExtra("year", classTable.getYear());
                intent.putExtra("term", classTable.getTerm());
                context.startActivity(intent);
            }
        }

    }

}
