package org.lntu.online.ui.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import org.lntu.online.R;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class ClassTableTimeDialogHolder extends RecyclerView.Adapter<ClassTableTimeDialogHolder.ViewHolder> {

    @InjectView(R.id.dialog_class_table_time_tv_title)
    protected TextView tvTitle;

    @InjectViews({
            R.id.dialog_class_table_time_btn_month_1,
            R.id.dialog_class_table_time_btn_month_2,
            R.id.dialog_class_table_time_btn_month_3,
            R.id.dialog_class_table_time_btn_month_4,
            R.id.dialog_class_table_time_btn_month_5,
            R.id.dialog_class_table_time_btn_month_6,
    })
    protected List<RadioButton> btnMonthList;

    @InjectView(R.id.dialog_class_table_time_layout_month)
    protected ViewGroup layoutMonth;

    @InjectView(R.id.dialog_class_table_time_recycler_view_day)
    protected RecyclerView recyclerView;

    @InjectView(R.id.dialog_class_table_time_layout_day)
    protected ViewGroup layoutDay;

    private Context context;
    private LayoutInflater inflater;
    private MaterialDialog dialog;
    private LocalDate currentDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private OnDialogFinishListener listener;

    public ClassTableTimeDialogHolder(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_class_table_time, false)
                .positiveText(R.string.confirm)
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (listener != null) {
                            listener.onDialogFinish(currentDate);
                        }
                    }

                })
                .build();
        ButterKnife.inject(this, dialog.getCustomView());

        recyclerView.setLayoutManager(new GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(this);
    }

    public void setOnTimeDialogFinishListener(OnDialogFinishListener listener) {
        this.listener = listener;
    }

    public void showDialog(LocalDate currentDate) {
        this.currentDate = currentDate;
        tvTitle.setText(currentDate.getYear() + "年 " + currentDate.getMonthOfYear() + "月");
        if (currentDate.getMonthOfYear() >= 2 && currentDate.getMonthOfYear() < 8) { // 春季
            btnMonthList.get(0).setText(currentDate.getYear() + "年 7月");
            btnMonthList.get(1).setText(currentDate.getYear() + "年 6月");
            btnMonthList.get(2).setText(currentDate.getYear() + "年 5月");
            btnMonthList.get(3).setText(currentDate.getYear() + "年 4月");
            btnMonthList.get(4).setText(currentDate.getYear() + "年 3月");
            btnMonthList.get(5).setText(currentDate.getYear() + "年 2月");
        } else {
            btnMonthList.get(0).setText(currentDate.getYear() + 1 + "年 1月");
            btnMonthList.get(1).setText(currentDate.getYear() + "年 12月");
            btnMonthList.get(2).setText(currentDate.getYear() + "年 11月");
            btnMonthList.get(3).setText(currentDate.getYear() + "年 10月");
            btnMonthList.get(4).setText(currentDate.getYear() + "年 9月");
            btnMonthList.get(5).setText(currentDate.getYear() + "年 8月");
        }
        layoutMonth.setVisibility(View.GONE);
        layoutDay.setVisibility(View.VISIBLE);
        updateDataSet();
        dialog.show();
    }

    @OnClick(R.id.dialog_class_table_time_tv_title)
    protected void onBtnTitleClick() {
        int index;
        if (currentDate.getMonthOfYear() >= 2 && currentDate.getMonthOfYear() < 8) { // 春季
            index = 7 - currentDate.getMonthOfYear();
        } else {
            index = 13 - (currentDate.getMonthOfYear() == 1 ? 13 : currentDate.getMonthOfYear());
        }
        btnMonthList.get(index).setChecked(true);
        layoutMonth.setVisibility(View.VISIBLE);
        layoutDay.setVisibility(View.GONE);
    }

    @OnClick({
            R.id.dialog_class_table_time_btn_month_1,
            R.id.dialog_class_table_time_btn_month_2,
            R.id.dialog_class_table_time_btn_month_3,
            R.id.dialog_class_table_time_btn_month_4,
            R.id.dialog_class_table_time_btn_month_5,
            R.id.dialog_class_table_time_btn_month_6,
    })
    protected void onBtnMonthClick(CompoundButton btn) {
        tvTitle.setText(btn.getText());
        String[] arr = btn.getText().toString().replace("年", "").replace("月", "").split(" ");
        if (currentDate.getMonthOfYear() != Integer.parseInt(arr[1])) {
            currentDate = new LocalDate(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), 1);
        }
        layoutMonth.setVisibility(View.GONE);
        layoutDay.setVisibility(View.VISIBLE);
        updateDataSet();
    }

    public interface OnDialogFinishListener {

        void onDialogFinish(LocalDate currentDate);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.dialog_class_table_time_day, parent, false);
        return new ViewHolder(itemView);
    }

    private void updateDataSet() {
        if (currentDate != null) {
            startDate = new LocalDate(currentDate.getYear(), currentDate.getMonthOfYear(), 1);
            startDate = startDate.minusDays(startDate.getDayOfWeek() % 7);
            LocalDate temp = currentDate.plusMonths(1);
            endDate = new LocalDate(temp.getYear(), temp.getMonthOfYear(), 1);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (currentDate == null || startDate == null || endDate == null) {
            return 0;
        } else {
            Period period = new Period(startDate, endDate, PeriodType.days());
            return period.getDays() + 7;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < 7) {
            String text;
            switch (position) {
                case 0:
                    text = "日";
                    break;
                case 1:
                    text = "一";
                    break;
                case 2:
                    text = "二";
                    break;
                case 3:
                    text = "三";
                    break;
                case 4:
                    text = "四";
                    break;
                case 5:
                    text = "五";
                    break;
                case 6:
                    text = "六";
                    break;
                default:
                    text = "";
                    break;
            }
            SpannableString spanString = new SpannableString(text);
            ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.text_color_secondary));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv.setText(spanString);
            holder.tv.setChecked(false);
            holder.tv.setClickable(false);
            holder.tv.getPaint().setFakeBoldText(true); // 字体加粗
        } else {
            position -= 7;
            LocalDate positionDate = startDate.plusDays(position);
            if (positionDate.getMonthOfYear() == currentDate.getMonthOfYear()) {
                holder.tv.setText(positionDate.getDayOfMonth() + "");
                holder.tv.setChecked(positionDate.equals(currentDate));
                holder.tv.setClickable(true);
            } else {
                holder.tv.setText("");
                holder.tv.setClickable(false);
            }
            holder.tv.getPaint().setFakeBoldText(false); // 字体加粗
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.dialog_class_table_time_day_tv)
        protected CheckedTextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.dialog_class_table_time_day_tv)
        public void onBtnItemClick(TextView tv) {
            currentDate = new LocalDate(currentDate.getYear(), currentDate.getMonthOfYear(), Integer.parseInt(tv.getText().toString()));
            updateDataSet();
        }

    }

}
