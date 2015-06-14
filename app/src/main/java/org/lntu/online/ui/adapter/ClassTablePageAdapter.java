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
import org.lntu.online.model.entity.WeekMode;
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
    private List<View> busyPool = new ArrayList<>();
    private List<View> idelPool = new ArrayList<>();

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate firstWeekMonday;
    private LocalDate today;

    private ClassTable classTable;
    private Map<String, List<ClassTable.CourseWrapper>> classTableMap;

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

    public void updateDataSet(ClassTable classTable, Map<String, List<ClassTable.CourseWrapper>> classTableMap) {
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
        if (idelPool.size() <= 0) { // 没有空闲view
            convertView = inflater.inflate(R.layout.activity_class_table_page_item, container, false);
            convertView.setTag(new ViewHolder(convertView));
        } else {
            convertView = idelPool.get(0);
            idelPool.remove(convertView);
        }
        busyPool.add(convertView);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        LocalDate currentDate = getDateAt(position);
        holder.update(position, currentDate, getWeekOfTerm(currentDate));
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View convertView = (View) object;
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.scrollView.scrollTo(0, 0);
        container.removeView(convertView);
        busyPool.remove(convertView);
        idelPool.add(convertView);
    }

    @Override
    public void notifyDataSetChanged() {
        for (View view : busyPool) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder.position >= 0) {
                holder.update(holder.position, holder.currentDate, holder.weekOfTerm);
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
        protected int weekOfTerm;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

        protected void update(int position, LocalDate currentDate, int weekOfTerm) {
            this.position = position;
            this.currentDate = currentDate;
            this.weekOfTerm = weekOfTerm;
            if (classTableMap == null) {
                return;
            }
            for (int stage = 1; stage <= 5; stage++) {
                // 设置上课时间
                tvStageList.get(stage - 1).setText(ClassTable.getStageTimeString(stage, currentDate));
                // 获取今天这一大节的课程列表
                List<ClassTable.CourseWrapper> courseWrapperList = classTableMap.get(currentDate.getDayOfWeek() + "-" + stage);
                ViewGroup layoutStage = layoutStageList.get(stage - 1);
                View iconStage = iconStageList.get(stage - 1);
                if (courseWrapperList == null || courseWrapperList.size() == 0) { // 没有课程
                    layoutStage.setVisibility(View.GONE);
                    iconStage.setVisibility(View.VISIBLE);
                } else { // 有课程
                    // 判断布局是否多余，删除多余
                    if (layoutStage.getChildCount() > courseWrapperList.size()) {
                        layoutStage.removeViews(0, layoutStage.getChildCount() - courseWrapperList.size());
                    }
                    // 遍历课程，生成布局
                    iconStage.setVisibility(View.VISIBLE); // 这里先标记为显示
                    layoutStage.setVisibility(View.GONE);
                    for (int i = 0; i < courseWrapperList.size(); i++) {
                        ClassTable.CourseWrapper courseWrapper = courseWrapperList.get(i);
                        View viewCourse = layoutStage.getChildAt(i);
                        if (viewCourse == null) {
                            viewCourse = inflater.inflate(R.layout.activity_class_table_page_item_course, layoutStage, false);
                            viewCourse.setTag(new CourseViewHolder(viewCourse));
                            layoutStage.addView(viewCourse);
                        }
                        CourseViewHolder holder = (CourseViewHolder) viewCourse.getTag();
                        holder.update(courseWrapper);
                        // 这里判断课程是否在时间段内，不在则隐藏
                        if (weekOfTerm < 0) { // 不是这个学期，没法判断，全显示
                            viewCourse.setVisibility(View.VISIBLE);
                            iconStage.setVisibility(View.GONE);
                            layoutStage.setVisibility(View.VISIBLE);
                        } else if (weekOfTerm >= courseWrapper.getTimeAndPlace().getStartWeek()
                                && weekOfTerm <= courseWrapper.getTimeAndPlace().getEndWeek()
                                && (courseWrapper.getTimeAndPlace().getWeekMode() == null
                                 || courseWrapper.getTimeAndPlace().getWeekMode() == WeekMode.ALL
                                 || courseWrapper.getTimeAndPlace().getWeekMode() == WeekMode.ODD && weekOfTerm % 2 == 1
                                 || courseWrapper.getTimeAndPlace().getWeekMode() == WeekMode.EVEN && weekOfTerm % 2 == 0)) {
                            viewCourse.setVisibility(View.VISIBLE);
                            iconStage.setVisibility(View.GONE);
                            layoutStage.setVisibility(View.VISIBLE);
                        } else {
                            viewCourse.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }

    }

    protected class CourseViewHolder {

        protected View root;

        @InjectView(R.id.class_table_page_item_course_tv_name)
        protected TextView tvName;

        @InjectView(R.id.class_table_page_item_course_tv_teacher)
        protected TextView tvTeacher;

        @InjectView(R.id.class_table_page_item_course_tv_place)
        protected TextView tvPlace;

        protected ClassTable.CourseWrapper courseWrapper;

        public CourseViewHolder(View convertView) {
            root = convertView;
            ButterKnife.inject(this, convertView);
        }

        public void update(ClassTable.CourseWrapper courseWrapper) {
            this.courseWrapper = courseWrapper;
            tvName.setText(courseWrapper.getCourse().getName());
            tvTeacher.setText(courseWrapper.getCourse().getTeacher());
            tvPlace.setText(courseWrapper.getTimeAndPlace().getRoom());
        }

        @OnClick(R.id.class_table_page_item_course_btn_card)
        protected void onBtnCardClick() {
            if (courseWrapper != null) {
                Intent intent = new Intent(context, ClassTableCourseActivity.class);
                intent.putExtra("course", GsonWrapper.gson.toJson(courseWrapper.getCourse()));
                intent.putExtra("year", classTable.getYear());
                intent.putExtra("term", classTable.getTerm());
                context.startActivity(intent);
            }
        }

    }

}
