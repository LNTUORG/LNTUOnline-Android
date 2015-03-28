package com.lntu.online.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.activity.CourseScoreActivity;
import com.lntu.online.activity.CurriculumActivity;
import com.lntu.online.activity.ExamPlanActivity;
import com.lntu.online.activity.MottoActivity;
import com.lntu.online.activity.NoticeActivity;
import com.lntu.online.activity.OneKeyActivity;
import com.lntu.online.activity.SkillTestActivity;
import com.lntu.online.activity.StudentInfoActivity;
import com.lntu.online.activity.UnpassCourseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final int[] icons = {
            R.drawable.main_icon_profle,
            R.drawable.main_icon_clock2,
            R.drawable.main_icon_calendar,
            R.drawable.main_icon_right2,
            R.drawable.main_icon_gps,
            R.drawable.main_icon_stop,
            R.drawable.main_icon_trophy,
            R.drawable.main_icon_megaphone,
            R.drawable.main_icon_chat
    };

    private static final String[] titles = {
            "学籍信息",
            "学期课表",
            "考试安排",
            "成绩查询",
            "一键评课",
            "挂科查询",
            "等级考试",
            "教务公告",
            "牢记校训"
    };

    private static final Class<?>[] clzs = {
            StudentInfoActivity.class,
            CurriculumActivity.class,
            ExamPlanActivity.class,
            CourseScoreActivity.class,
            OneKeyActivity.class,
            UnpassCourseActivity.class,
            SkillTestActivity.class,
            NoticeActivity.class,
            MottoActivity.class
    };

    private Context context;
    private LayoutInflater inflater;

    public MainAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.activity_main_center_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imgIcon.setImageResource(icons[position]);
        holder.tvTitle.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.main_center_item_img_icon)
        protected ImageView imgIcon;

        @InjectView(R.id.main_center_item_tv_title)
        protected TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.main_center_item_btn_root)
        public void onBtnItemClick() {
            context.startActivity(new Intent(context, clzs[getLayoutPosition()]));
        }

    }

}
