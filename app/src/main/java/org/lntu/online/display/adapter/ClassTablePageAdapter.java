package org.lntu.online.display.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.lntu.online.R;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.model.entity.DayInWeek;
import org.lntu.online.model.entity.WeekMode;
import org.lntu.online.display.activity.ClassTableCourseActivity;
import org.lntu.online.util.gson.GsonWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
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
    private Map<String, String> openStateMap = new HashMap<>();

    public ClassTablePageAdapter(Context context, int year, String term, LocalDate today) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if ("春".equals(term)) {
            startDate = new LocalDate(year, 2, 1);
            endDate = new LocalDate(year, 8, 1);
        } else {
            startDate = new LocalDate(year, 8, 1);
            endDate = new LocalDate(year + 1, 2, 1);
        }
        this.today = today;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void updateDataSet(ClassTable classTable, Map<String, List<ClassTable.CourseWrapper>> classTableMap) {
        this.classTable = classTable;
        this.classTableMap = classTableMap;
        firstWeekMonday = new LocalDate(classTable.getFirstWeekMondayAt());
        if (firstWeekMonday.isBefore(startDate) || firstWeekMonday.isAfter(endDate)) { // 第一周周一不在时间区域，重新定义第一周周一
            if ("春".equals(classTable.getTerm())) {
                firstWeekMonday = new LocalDate(classTable.getYear(), 3, 1);
            } else {
                firstWeekMonday = new LocalDate(classTable.getYear(), 9, 1);
            }
            firstWeekMonday = firstWeekMonday.plusDays((7 - firstWeekMonday.getDayOfWeek() + 1) % 7);
        } else { // 第一周周一在时间区域
            firstWeekMonday = firstWeekMonday.minusDays(firstWeekMonday.getDayOfWeek() - 1);
        }
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
        if (date == null || date.isBefore(startDate) || date.isAfter(endDate)) {
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
        holder.update(position);
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
                holder.update(holder.position);
            }
        }
        super.notifyDataSetChanged();
    }

    protected class ViewHolder {

        @BindView(R.id.scroll_view)
        protected ScrollView scrollView;

        @BindViews({
                R.id.tv_stage_1,
                R.id.tv_stage_2,
                R.id.tv_stage_3,
                R.id.tv_stage_4,
                R.id.tv_stage_5
        })
        protected List<TextView> tvStageList;

        @BindViews({
                R.id.layout_stage_1_show,
                R.id.layout_stage_2_show,
                R.id.layout_stage_3_show,
                R.id.layout_stage_4_show,
                R.id.layout_stage_5_show
        })
        protected List<ViewGroup> layoutStageShowList;

        @BindViews({
                R.id.layout_stage_1_hide,
                R.id.layout_stage_2_hide,
                R.id.layout_stage_3_hide,
                R.id.layout_stage_4_hide,
                R.id.layout_stage_5_hide
        })
        protected List<ViewGroup> layoutStageHideList;

        @BindViews({
                R.id.icon_stage_1,
                R.id.icon_stage_2,
                R.id.icon_stage_3,
                R.id.icon_stage_4,
                R.id.icon_stage_5
        })
        protected List<View> iconStageList;

        @BindViews({
                R.id.btn_stage_1,
                R.id.btn_stage_2,
                R.id.btn_stage_3,
                R.id.btn_stage_4,
                R.id.btn_stage_5,
        })
        protected List<View> btnStageList;

        protected int position = -1;
        protected LocalDate currentDate;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

        protected void update(int position) {
            this.position = position;
            currentDate = getDateAt(position);
            int weekOfTerm = getWeekOfTerm(currentDate);
            if (classTableMap == null) {
                return;
            }
            for (int stage = 1; stage <= 5; stage++) {
                // 设置上课时间
                tvStageList.get(stage - 1).setText(ClassTable.getStageTimeString(stage, currentDate));
                // 获取今天这一大节的课程列表
                List<ClassTable.CourseWrapper> courseWrapperList = classTableMap.get(currentDate.getDayOfWeek() + "-" + stage);
                ViewGroup layoutStageShow = layoutStageShowList.get(stage - 1);
                ViewGroup layoutStageHide = layoutStageHideList.get(stage - 1);
                View iconStage = iconStageList.get(stage - 1);
                View btnStage = btnStageList.get(stage - 1);
                // 清除布局
                layoutStageShow.removeAllViews();
                layoutStageHide.removeAllViews();
                // 更新布局显示
                int showCount = 0;
                int hideCount = 0;
                for (int i = 0; i < (courseWrapperList == null ? 0 : courseWrapperList.size()); i++) {
                    ClassTable.CourseWrapper courseWrapper = courseWrapperList.get(i);
                    boolean isCurrent = weekOfTerm >= courseWrapper.getTimeAndPlace().getStartWeek()
                            && weekOfTerm <= courseWrapper.getTimeAndPlace().getEndWeek()
                            && (courseWrapper.getTimeAndPlace().getWeekMode() == null
                            || courseWrapper.getTimeAndPlace().getWeekMode() == WeekMode.ALL
                            || courseWrapper.getTimeAndPlace().getWeekMode() == WeekMode.ODD && weekOfTerm % 2 == 1
                            || courseWrapper.getTimeAndPlace().getWeekMode() == WeekMode.EVEN && weekOfTerm % 2 == 0);
                    View viewCourse = isCurrent ? layoutStageShow.getChildAt(showCount) : layoutStageHide.getChildAt(hideCount);
                    if (viewCourse == null) {
                        viewCourse = inflater.inflate(R.layout.activity_class_table_page_item_course, isCurrent ? layoutStageShow : layoutStageHide, false);
                        viewCourse.setTag(new CourseViewHolder(viewCourse));
                        if (isCurrent) {
                            layoutStageShow.addView(viewCourse);
                            showCount++;
                        } else {
                            layoutStageHide.addView(viewCourse);
                            hideCount++;
                        }
                    }
                    CourseViewHolder holder = (CourseViewHolder) viewCourse.getTag();
                    holder.update(courseWrapper, isCurrent);
                }
                // 更新整体布局状态
                layoutStageShow.setVisibility(showCount > 0 ? View.VISIBLE : View.GONE);
                iconStage.setVisibility(showCount > 0 ? View.GONE : View.VISIBLE);
                layoutStageHide.setVisibility(TextUtils.isEmpty(openStateMap.get(currentDate.toString() + "-" + stage)) ? View.GONE : View.VISIBLE);
                btnStage.setVisibility(layoutStageHide.getChildCount() > 0 ? View.VISIBLE : View.GONE);
            }
        }

        @OnClick({
                R.id.btn_stage_1,
                R.id.btn_stage_2,
                R.id.btn_stage_3,
                R.id.btn_stage_4,
                R.id.btn_stage_5,
        })
        protected void onBtnStage1Click(View view) {
            switch (view.getId()) {
                case R.id.btn_stage_1:
                    toggleLayout(1);
                    break;
                case R.id.btn_stage_2:
                    toggleLayout(2);
                    break;
                case R.id.btn_stage_3:
                    toggleLayout(3);
                    break;
                case R.id.btn_stage_4:
                    toggleLayout(4);
                    break;
                case R.id.btn_stage_5:
                    toggleLayout(5);
                    break;
            }
        }

        private void toggleLayout(int stage) {
            ViewGroup layoutStageHide = layoutStageHideList.get(stage - 1);
            layoutStageHide.setVisibility(layoutStageHide.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            openStateMap.put(currentDate.toString() + "-" + stage, layoutStageHide.getVisibility() == View.VISIBLE ? "true" : null);
        }

    }

    protected class CourseViewHolder {

        @BindView(R.id.tv_name)
        protected TextView tvName;

        @BindView(R.id.tv_teacher)
        protected TextView tvTeacher;

        @BindView(R.id.tv_place)
        protected TextView tvPlace;

        @BindView(R.id.icon_not_current)
        protected View iconNotCurrent;

        protected ClassTable.CourseWrapper courseWrapper;

        public CourseViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

        public void update(ClassTable.CourseWrapper courseWrapper, boolean isCurrent) {
            this.courseWrapper = courseWrapper;
            tvName.setText(courseWrapper.getCourse().getName());
            tvTeacher.setText(courseWrapper.getCourse().getTeacher());
            tvPlace.setText(courseWrapper.getTimeAndPlace().getRoom());
            iconNotCurrent.setVisibility(isCurrent ? View.GONE : View.VISIBLE);
        }

        @OnClick(R.id.btn_card)
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
