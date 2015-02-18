package com.lntu.online.activity;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lntu.online.R;
import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.ClientEducationExperience;
import com.lntu.online.model.ClientEntranceExam;
import com.lntu.online.model.ClientFamily;
import com.lntu.online.model.ClientStudent;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class StudentInfoActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private ViewGroup layoutContent;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        layoutContent = (ViewGroup) findViewById(R.id.student_info_layout_content);

        inflater = getLayoutInflater();

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
        HttpUtil.get(this, NetworkInfo.serverUrl + "student/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ClientStudent student = ClientStudent.dao.fromJson(responseString);
                    updateContentView(student);
                } catch(Exception e) {
                    String[] msgs = responseString.split("\n");
                    showErrorDialog("提示", msgs[0], msgs[1]);
                }
            }

            @Override
            public void onBtnRetry() {
                startNetwork();
            }

        });
    }

    private void updateContentView(ClientStudent student) {
        //头像信息
        {
            ImageView imgPhoto = (ImageView) inflater.inflate(R.layout.activity_student_info_item_photo, layoutContent, false);
            startPhotoDownload(imgPhoto, student.getPhotoUrl());
            layoutContent.addView(imgPhoto);
        }
        //基本信息
        {
            TextView tvCategory = (TextView) inflater.inflate(R.layout.activity_student_info_item_category, layoutContent, false);
            tvCategory.setText("基本信息");
            layoutContent.addView(tvCategory);
        }
        for (int n = 0; n < 34; n++) {
            View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, layoutContent, false);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
            TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
            switch(n) {
            case 0:
                tvTitle.setText("学号");
                tvValue.setText(student.getUserId() + "");
                break;
            case 1:
                tvTitle.setText("姓名");
                tvValue.setText(student.getName() + "");
                break;
            case 2:
                tvTitle.setText("英文名");
                tvValue.setText(student.getEnglishName() + "");
                break;
            case 3:
                tvTitle.setText("性别");
                tvValue.setText(student.getSex() + "");
                break;
            case 4:
                tvTitle.setText("国籍");
                tvValue.setText(student.getNationality() + "");
                break;
            case 5:
                tvTitle.setText("民族");
                tvValue.setText(student.getNation() + "");
                break;
            case 6:
                tvTitle.setText("政治面貌");
                tvValue.setText(student.getPoliticalAffiliation() + "");
                break;
            case 7:
                tvTitle.setText("证件类型");
                tvValue.setText(student.getIdCardType() + "");
                break;
            case 8:
                tvTitle.setText("证件号码");
                tvValue.setText(student.getIdCardNum() + "");
                break;
            case 9:
                tvTitle.setText("出生日期");
                tvValue.setText(student.getDateOfBirth() + "");
                break;
            case 10:
                tvTitle.setText("籍贯");
                tvValue.setText(student.getBirthplace() + "");
                break;
            case 11:
                tvTitle.setText("学院");
                tvValue.setText(student.getCollege() + "");
                break;
            case 12:
                tvTitle.setText("专业");
                tvValue.setText(student.getMajor() + "");
                break;
            case 13:
                tvTitle.setText("班级");
                tvValue.setText(student.getClassInfo() + "");
                break;
            case 14:
                tvTitle.setText("学生类型");
                tvValue.setText(student.getStudentType() + "");
                break;
            case 15:
                tvTitle.setText("学籍表号");
                tvValue.setText(student.getStudentInfoTableNum() + "");
                break;
            case 16:
                tvTitle.setText("考区");
                tvValue.setText(student.getEntranceExamArea() + "");
                break;
            case 17:
                tvTitle.setText("准考证号码");
                tvValue.setText(student.getEntranceExamNum() + "");
                break;
            case 18:
                tvTitle.setText("外语语种");
                tvValue.setText(student.getForeignLanguage() + "");
                break;
            case 19:
                tvTitle.setText("培养方式");
                tvValue.setText(student.getEducationType() + "");
                break;
            case 20:
                tvTitle.setText("录取证号");
                tvValue.setText(student.getAdmissionNum() + "");
                break;
            case 21:
                tvTitle.setText("录取方式");
                tvValue.setText(student.getAdmissionType() + "");
                break;    
            case 22:
                tvTitle.setText("学生来源");
                tvValue.setText(student.getSourceOfStudent() + "");
                break;
            case 23:
                tvTitle.setText("毕业学校");
                tvValue.setText(student.getGraduateSchool() + "");
                break;
            case 24:
                tvTitle.setText("高考总分");
                tvValue.setText(student.getEntranceExamScore() + "");
                break;
            case 25:
                tvTitle.setText("入学日期");
                tvValue.setText(student.getDateOfAdmission() + "");
                break;
            case 26:
                tvTitle.setText("毕业日期");
                tvValue.setText(student.getDateOfGraduation() + "");
                break;
            case 27:
                tvTitle.setText("毕业去向");
                tvValue.setText(student.getWhereaboutsAftergraduation() + "");
                break;
            case 28:
                tvTitle.setText("家庭地址");
                tvValue.setText(student.getHomeAddress() + "");
                break;
            case 29:
                tvTitle.setText("乘车区间");
                tvValue.setText(student.getTravelRange() + "");
                break;
            case 30:
                tvTitle.setText("联系电话");
                tvValue.setText(student.getContactTel() + "");
                break;
            case 31:
                tvTitle.setText("邮政编码");
                tvValue.setText(student.getZipCode() + "");
                break;
            case 32:
                tvTitle.setText("电子邮件");
                tvValue.setText(student.getEmail() + "");
                break;
            case 33:
                tvTitle.setText("备注");
                tvValue.setText(student.getRemarks() + "");
                break;
            }
            //添加到布局中
            layoutContent.addView(itemView);
            //添加分割线
            View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
            layoutContent.addView(deepLine);
        }
        //高考科目
        {
            TextView tvCategory = (TextView) inflater.inflate(R.layout.activity_student_info_item_category, layoutContent, false);
            tvCategory.setText("高考科目");
            layoutContent.addView(tvCategory);
        }
        for (int n = 0; n < student.getEntranceExams().size(); n++) {
            ClientEntranceExam ee = student.getEntranceExams().get(n);
            //布局
            View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, layoutContent, false);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
            TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
            tvTitle.setText(ee.getName() + "");
            tvValue.setText(ee.getScore() + "");
            //添加到布局
            layoutContent.addView(itemView);
            //添加分割线
            View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
            layoutContent.addView(deepLine);
        }
        //没有高考科目
        if (student.getEntranceExams().size() == 0) {
            View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, layoutContent, false);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
            TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
            tvTitle.setText("暂无信息");
            tvValue.setText("");
            layoutContent.addView(itemView);
        }
        //教育经历
        {
            TextView tvCategory = (TextView) inflater.inflate(R.layout.activity_student_info_item_category, layoutContent, false);
            tvCategory.setText("教育经历");
            layoutContent.addView(tvCategory);
        }
        for (int n = 0; n < student.getEducationExperiences().size(); n++) {
            ClientEducationExperience ee = student.getEducationExperiences().get(n);
            //布局
            View itemView = inflater.inflate(R.layout.activity_student_info_item_edex, layoutContent, false);
            TextView tvDateOfStart = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_start);
            TextView tvDateOfEnd = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_end);
            TextView tvSchoolName = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_school_name);
            TextView tvWitness = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_witness);
            tvDateOfStart.setText(ee.getDateOfStart() + "");
            tvDateOfEnd.setText(ee.getDateOfEnd() + "");
            tvSchoolName.setText(ee.getSchoolName() + "");
            tvWitness.setText(ee.getWitness() + "");
            //填充
            layoutContent.addView(itemView);
            //添加分割线
            View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
            layoutContent.addView(deepLine);
        }
        //没有教育经历
        if (student.getEducationExperiences().size() == 0) {
            View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, layoutContent, false);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
            TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
            tvTitle.setText("暂无信息");
            tvValue.setText("");
            layoutContent.addView(itemView);
        }
        //家庭信息
        {
            TextView tvCategory = (TextView) inflater.inflate(R.layout.activity_student_info_item_category, layoutContent, false);
            tvCategory.setText("家庭信息");
            layoutContent.addView(tvCategory);
        }
        for (int n = 0; n < student.getFamilys().size(); n++) {
            ClientFamily f = student.getFamilys().get(n);
            //布局
            View itemView = inflater.inflate(R.layout.activity_student_info_item_family, layoutContent, false);
            TextView tvName = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_name);
            TextView tvRelationship = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_relationship);
            TextView tvPolAff = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_political_affiliation);
            TextView tvJob = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_job);
            TextView tvPost = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_post);
            TextView tvWorkLocation = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_work_location);
            TextView tvTel = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_tel);
            tvName.setText(f.getName() + "");
            tvRelationship.setText("（" + f.getRelationship() + "）");
            tvPolAff.setText(f.getPoliticalAffiliation() + "");
            tvJob.setText(f.getJob() + "");
            tvPost.setText(f.getPost() + "");
            tvWorkLocation.setText(f.getWorkLocation() + "");
            tvTel.setText(f.getTel() + "");
            //填充
            layoutContent.addView(itemView);
            //添加分割线
            View deepLine = inflater.inflate(R.layout.activity_deep_line, layoutContent, false);
            layoutContent.addView(deepLine);
        }
        //没有家庭信息
        if (student.getFamilys().size() == 0) {
            View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, layoutContent, false);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
            TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
            tvTitle.setText("暂无信息");
            tvValue.setText("");
            layoutContent.addView(itemView);
        }
    }

    private void startPhotoDownload(final ImageView img, String url) {
        HttpUtil.baseGet(this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Bitmap bmp = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                    img.setImageBitmap(bmp);
                } catch(Exception e) {
                    Toast.makeText(StudentInfoActivity.this, "用户头像解析失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(StudentInfoActivity.this, "用户头像获取失败", Toast.LENGTH_SHORT).show();
            }

        });

    }

}
