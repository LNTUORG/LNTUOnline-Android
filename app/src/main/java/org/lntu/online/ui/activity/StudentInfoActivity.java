package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lntu.online.R;
import com.squareup.picasso.Picasso;

import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.BackgroundCallback;
import org.lntu.online.model.entity.Student;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.util.TimeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.client.Response;

public class StudentInfoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.student_info_layout_content)
    protected ViewGroup layoutContent;

    @InjectView(R.id.student_info_icon_content)
    protected View iconContent;

    @InjectView(R.id.student_info_icon_loading)
    protected View iconLoading;

    @InjectView(R.id.student_info_icon_empty)
    protected View iconEmpty;

    @InjectView(R.id.student_info_icon_loading_anim)
    protected View iconLoadingAnim;

    @InjectView(R.id.student_info_tv_load_failed)
    protected TextView tvLoadFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animation dataLoadAnim = AnimationUtils.loadAnimation(this, R.anim.data_loading);
        dataLoadAnim.setInterpolator(new LinearInterpolator());
        iconLoadingAnim.startAnimation(dataLoadAnim);

        startNetwork();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startNetwork() {
        ApiClient.with(this).apiService.getStudent(LoginShared.getLoginToken(this), new BackgroundCallback<Student>(this) {

            @Override
            public void handleSuccess(Student student, Response response) {
                updateInfoView(student);
                iconContent.setVisibility(View.VISIBLE);
                iconLoading.setVisibility(View.GONE);
                iconEmpty.setVisibility(View.GONE);
            }

            @Override
            public void handleFailure(String message) {
                showIconEmptyView(message);
            }

        });
    }

    private void showIconEmptyView(String message) {
        iconLoading.setVisibility(View.GONE);
        iconEmpty.setVisibility(View.VISIBLE);
        tvLoadFailed.setText(message);
    }

    @OnClick(R.id.student_info_icon_empty)
    protected void onBtnIconEmptyClick() {
        iconLoading.setVisibility(View.VISIBLE);
        iconEmpty.setVisibility(View.GONE);
        startNetwork();
    }

    public void updateInfoView(Student student) {
        LayoutInflater inflater = getLayoutInflater();
        // 头像信息
        {
            View itemView  = inflater.inflate(R.layout.activity_student_info_item_avatar, layoutContent, false);
            ImageView imgAvatar = (ImageView) itemView.findViewById(R.id.student_info_item_avatar_img_avatar);
            Picasso.with(this).load(student.getPhotoUrl()).into(imgAvatar);
            layoutContent.addView(itemView);
        }
        // 基本信息
        {
            TextView tvTitle = (TextView) inflater.inflate(R.layout.activity_student_info_item_title, layoutContent, false);
            tvTitle.setText("基本信息");
            tvTitle.setBackgroundResource(R.color.red_light);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_assignment_ind_white_24dp, 0, 0, 0);
            layoutContent.addView(tvTitle);
        }
        for (int n = 0; n < 34; n++) {
            View itemView = inflater.inflate(R.layout.activity_student_info_item_normal, layoutContent, false);
            TextView tvName = (TextView) itemView.findViewById(R.id.student_info_item_normal_tv_name);
            TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_normal_tv_value);
            switch(n) {
                case 0:
                    tvName.setText("学号");
                    tvValue.setText(student.getId());
                    break;
                case 1:
                    tvName.setText("姓名");
                    tvValue.setText(student.getName());
                    break;
                case 2:
                    tvName.setText("英文名");
                    tvValue.setText(student.getEnglishName());
                    break;
                case 3:
                    tvName.setText("性别");
                    tvValue.setText(student.getSex());
                    break;
                case 4:
                    tvName.setText("国籍");
                    tvValue.setText(student.getNationality());
                    break;
                case 5:
                    tvName.setText("民族");
                    tvValue.setText(student.getNation());
                    break;
                case 6:
                    tvName.setText("政治面貌");
                    tvValue.setText(student.getPoliticalAffiliation());
                    break;
                case 7:
                    tvName.setText("证件类型");
                    tvValue.setText(student.getIdCardType());
                    break;
                case 8:
                    tvName.setText("证件号码");
                    tvValue.setText(student.getIdCardNum());
                    break;
                case 9:
                    tvName.setText("出生日期");
                    tvValue.setText(TimeUtils.getTimeFormat(student.getBirthday()));
                    break;
                case 10:
                    tvName.setText("籍贯");
                    tvValue.setText(student.getBirthplace());
                    break;
                case 11:
                    tvName.setText("学院");
                    tvValue.setText(student.getCollege());
                    break;
                case 12:
                    tvName.setText("专业");
                    tvValue.setText(student.getMajor());
                    break;
                case 13:
                    tvName.setText("班级");
                    tvValue.setText(student.getClassInfo());
                    break;
                case 14:
                    tvName.setText("学生类型");
                    tvValue.setText(student.getStudentType());
                    break;
                case 15:
                    tvName.setText("学籍表号");
                    tvValue.setText(student.getStudentInfoTableNum());
                    break;
                case 16:
                    tvName.setText("考区");
                    tvValue.setText(student.getEntranceExamArea());
                    break;
                case 17:
                    tvName.setText("准考证号码");
                    tvValue.setText(student.getEntranceExamNum());
                    break;
                case 18:
                    tvName.setText("外语语种");
                    tvValue.setText(student.getForeignLanguage());
                    break;
                case 19:
                    tvName.setText("培养方式");
                    tvValue.setText(student.getEducationType());
                    break;
                case 20:
                    tvName.setText("录取证号");
                    tvValue.setText(student.getAdmissionNum());
                    break;
                case 21:
                    tvName.setText("录取方式");
                    tvValue.setText(student.getAdmissionType());
                    break;
                case 22:
                    tvName.setText("学生来源");
                    tvValue.setText(student.getSourceOfStudent());
                    break;
                case 23:
                    tvName.setText("毕业学校");
                    tvValue.setText(student.getGraduateSchool());
                    break;
                case 24:
                    tvName.setText("高考总分");
                    tvValue.setText(student.getEntranceExamScore());
                    break;
                case 25:
                    tvName.setText("入学日期");
                    tvValue.setText(TimeUtils.getTimeFormat(student.getAdmissionTime()));
                    break;
                case 26:
                    tvName.setText("毕业日期");
                    tvValue.setText(TimeUtils.getTimeFormat(student.getGraduationTime()));
                    break;
                case 27:
                    tvName.setText("毕业去向");
                    tvValue.setText(student.getWhereaboutsAftergraduation());
                    break;
                case 28:
                    tvName.setText("家庭地址");
                    tvValue.setText(student.getHomeAddress());
                    break;
                case 29:
                    tvName.setText("乘车区间");
                    tvValue.setText(student.getTravelRange());
                    break;
                case 30:
                    tvName.setText("联系电话");
                    tvValue.setText(student.getTel());
                    break;
                case 31:
                    tvName.setText("邮政编码");
                    tvValue.setText(student.getZipCode());
                    break;
                case 32:
                    tvName.setText("电子邮件");
                    tvValue.setText(student.getEmail());
                    break;
                case 33:
                    tvName.setText("备注");
                    tvValue.setText(student.getRemarks());
                    break;
            }
            layoutContent.addView(itemView);
            View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
            layoutContent.addView(deepLine);    
        }
        // 高考科目
        if (student.getEntranceExams() != null && student.getEntranceExams().size() > 0) {

            TextView tvTitle = (TextView) inflater.inflate(R.layout.activity_student_info_item_title, layoutContent, false);
            tvTitle.setText("高考科目");
            tvTitle.setBackgroundResource(R.color.green_light);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle_white_24dp, 0, 0, 0);
            layoutContent.addView(tvTitle);

            for (Student.EntranceExam ee : student.getEntranceExams()) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_normal, layoutContent, false);
                TextView tvName = (TextView) itemView.findViewById(R.id.student_info_item_normal_tv_name);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_normal_tv_value);
                tvName.setText(ee.getName());
                tvValue.setText(ee.getScore());
                // 添加到布局
                layoutContent.addView(itemView);
                // 添加分割线
                View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
                layoutContent.addView(deepLine);
            }

        }
        // 教育经历
        if (student.getEducationExperiences() != null && student.getEducationExperiences().size() > 0) {

            TextView tvTitle = (TextView) inflater.inflate(R.layout.activity_student_info_item_title, layoutContent, false);
            tvTitle.setText("教育经历");
            tvTitle.setBackgroundResource(R.color.blue_light);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_book_white_24dp, 0, 0, 0);
            layoutContent.addView(tvTitle);

            for (Student.EducationExperience ee : student.getEducationExperiences()) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_edex, layoutContent, false);
                TextView tvDateOfStart = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_start);
                TextView tvDateOfEnd = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_end);
                TextView tvSchoolName = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_school_name);
                TextView tvWitness = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_witness);
                tvDateOfStart.setText(TimeUtils.getTimeFormat(ee.getStartTime()));
                tvDateOfEnd.setText(TimeUtils.getTimeFormat(ee.getEndTime()));
                tvSchoolName.setText(ee.getSchoolInfo());
                tvWitness.setText(ee.getWitness());
                // 添加到布局
                layoutContent.addView(itemView);
                // 添加分割线
                View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
                layoutContent.addView(deepLine);
            }

        }
        // 家庭信息
        if (student.getFamilys() != null && student.getFamilys().size() > 0) {

            TextView tvTitle = (TextView) inflater.inflate(R.layout.activity_student_info_item_title, layoutContent, false);
            tvTitle.setText("家庭情况");
            tvTitle.setBackgroundResource(R.color.orange_light);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_white_24dp, 0, 0, 0);
            layoutContent.addView(tvTitle);

            for (Student.Family family : student.getFamilys()) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_family, layoutContent, false);
                TextView tvName = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_name);
                TextView tvRelationship = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_relationship);
                TextView tvPolAff = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_political_affiliation);
                TextView tvJob = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_job);
                TextView tvPost = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_post);
                TextView tvWorkLocation = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_work_location);
                TextView tvTel = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_tel);
                tvName.setText(family.getName());
                tvRelationship.setText("（" + family.getRelationship() + "）");
                tvPolAff.setText(family.getPoliticalAffiliation());
                tvJob.setText(family.getJob());
                tvPost.setText(family.getPost());
                tvWorkLocation.setText(family.getWorkLocation());
                tvTel.setText(family.getTel());
                layoutContent.addView(itemView);
                View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
                layoutContent.addView(deepLine);
            }

        }
        // 警告处分
        if (student.getDisciplinaryActions() != null && student.getDisciplinaryActions().size() > 0) {

            TextView tvTitle = (TextView) inflater.inflate(R.layout.activity_student_info_item_title, layoutContent, false);
            tvTitle.setText("警告处分");
            tvTitle.setBackgroundResource(R.color.grey_light);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_report_problem_white_24dp, 0, 0, 0);
            layoutContent.addView(tvTitle);

            for (Student.DisciplinaryAction action : student.getDisciplinaryActions()) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_action, layoutContent, false);
                TextView tvLevel = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_level);
                TextView tvCreateTime = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_create_time);
                TextView tvCreateReason = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_create_reason);
                TextView tvCancelTime = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_cancel_time);
                TextView tvCancelReason = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_cancel_reason);
                TextView tvState = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_state);
                TextView tvRemarks = (TextView) itemView.findViewById(R.id.student_info_item_action_tv_remarks);
                tvLevel.setText(action.getLevel());
                tvCreateTime.setText(TimeUtils.getTimeFormat(action.getCreateTime()));
                tvCreateReason.setText(action.getCreateReason());
                tvCancelTime.setText(TimeUtils.getTimeFormat(action.getCancelTime()));
                tvCancelReason.setText(action.getCancelReason());
                tvState.setText(action.getState());
                tvRemarks.setText(action.getRemarks());
                layoutContent.addView(itemView);
                View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
                layoutContent.addView(deepLine);
            }

        }
    }

}
