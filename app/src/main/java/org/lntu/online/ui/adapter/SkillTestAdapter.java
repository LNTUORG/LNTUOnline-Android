package org.lntu.online.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.entity.SkillTestScore;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SkillTestAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<SkillTestScore> scoreList;

    public SkillTestAdapter(Context context, List<SkillTestScore> scoreList) {
        inflater = LayoutInflater.from(context);
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList == null ? 0 : scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_main_grades_query_skill_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SkillTestScore score = scoreList.get(position);
        holder.tvName.setText(score.getName());
        holder.tvTime.setText(score.getTime());
        holder.tvScore.setText(score.getScore());
        return convertView;
    }

    protected static class ViewHolder {

        @InjectView(R.id.skill_test_item_tv_name)
        protected TextView tvName;

        @InjectView(R.id.skill_test_item_tv_time)
        protected TextView tvTime;

        @InjectView(R.id.skill_test_item_tv_score)
        protected TextView tvScore;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
        }

    }

}
