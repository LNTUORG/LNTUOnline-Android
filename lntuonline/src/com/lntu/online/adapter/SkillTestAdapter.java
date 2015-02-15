package com.lntu.online.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.model.ClientSkillTestScore;

public class SkillTestAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ClientSkillTestScore> scoreList;

    public SkillTestAdapter(Context context, List<ClientSkillTestScore> scoreList) {
        inflater = LayoutInflater.from(context);
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList.size();
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
            convertView = inflater.inflate(R.layout.activity_skill_test_item, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.skill_test_item_tv_name);
            holder.tvTime = (TextView) convertView.findViewById(R.id.skill_test_item_tv_time);
            holder.tvScore = (TextView) convertView.findViewById(R.id.skill_test_item_tv_score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClientSkillTestScore score = scoreList.get(position);
        holder.tvName.setText(score.getName() + "");
        holder.tvTime.setText(score.getTime() + "");
        holder.tvScore.setText(score.getScore() + "");
        return convertView;
    }

    private static class ViewHolder {

        TextView tvName;
        TextView tvTime;
        TextView tvScore;

    }

}
