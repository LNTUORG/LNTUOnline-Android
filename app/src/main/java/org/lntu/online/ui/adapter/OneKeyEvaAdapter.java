package org.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lntu.online.R;

import org.lntu.online.model.entity.CourseEvaInfo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

public class OneKeyEvaAdapter extends RecyclerView.Adapter<OneKeyEvaAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<CourseEvaInfo> infoList;

    public OneKeyEvaAdapter(Context context, List<CourseEvaInfo> infoList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.infoList = infoList;
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_one_key_eva_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.one_key_eva_item_tv_name)
        protected TextView tvName;

        @Bind(R.id.one_key_eva_item_tv_num)
        protected TextView tvNum;

        @Bind(R.id.one_key_eva_item_tv_teacher)
        protected TextView tvTeacher;

        @Bind(R.id.one_key_eva_item_tv_state)
        protected TextView tvState;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            CourseEvaInfo info = infoList.get(position);
            tvName.setText(info.getName());
            tvNum.setText(info.getNum());
            tvTeacher.setText(info.getTeacher());
            if (info.isDone()) {
                tvState.setText("已评估");
                tvState.setTextColor(context.getResources().getColor(R.color.score_level_normal));
            } else {
                tvState.setText("未评估");
                tvState.setTextColor(context.getResources().getColor(R.color.score_level_unpass));
            }
        }

    }

}
