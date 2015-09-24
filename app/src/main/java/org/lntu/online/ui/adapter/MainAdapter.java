package org.lntu.online.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.ui.activity.ClassTableActivity;
import org.lntu.online.ui.activity.GradesActivity;
import org.lntu.online.ui.activity.ExamPlanActivity;
import org.lntu.online.ui.activity.MottoActivity;
import org.lntu.online.ui.activity.NoticeActivity;
import org.lntu.online.ui.activity.OneKeyEvaActivity;
import org.lntu.online.ui.activity.SkillTestActivity;
import org.lntu.online.ui.activity.StudentInfoActivity;
import org.lntu.online.ui.activity.UnpassCourseActivity;

import butterknife.ButterKnife;
import butterknife.Bind;
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

    private static final int[] titles = {
            R.string.student_info,
            R.string.my_class_table,
            R.string.exam_plan,
            R.string.grades_query,
            R.string.one_key_eva,
            R.string.unpass_query,
            R.string.skill_test,
            R.string.senate_notice,
            R.string.remember_motto
    };

    private static final Class<?>[] clzs = {
            StudentInfoActivity.class,
            ClassTableActivity.class,
            ExamPlanActivity.class,
            GradesActivity.class,
            OneKeyEvaActivity.class,
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
        return new ViewHolder(inflater.inflate(R.layout.activity_main_item, parent, false));
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

        @Bind(R.id.main_item_img_icon)
        protected ImageView imgIcon;

        @Bind(R.id.main_item_tv_title)
        protected TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.main_item_btn_item)
        public void onBtnItemClick() {
            context.startActivity(new Intent(context, clzs[getLayoutPosition()]));
        }

    }

}
