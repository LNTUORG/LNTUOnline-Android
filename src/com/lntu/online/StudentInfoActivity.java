package com.lntu.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lntu.online.http.HttpUtil;
import com.lntu.online.http.RetryAuthListener;
import com.lntu.online.info.NetworkInfo;
import com.lntu.online.model.client.ClientEducationExperience;
import com.lntu.online.model.client.ClientEntranceExam;
import com.lntu.online.model.client.ClientFamily;
import com.lntu.online.model.client.ClientStudent;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class StudentInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        startNetwork();
    }

    private void startNetwork() {
        HttpUtil.get(this, NetworkInfo.serverUrl + "student/info", new RetryAuthListener(this) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    ClientStudent cs = ClientStudent.dao.fromJson(responseString);
                    ListView lvRoot = (ListView) findViewById(R.id.student_info_lv_root);
                    lvRoot.setAdapter(new ListViewAdapter(getContext(), cs));
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

    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, ClientStudent cs) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemViews = new ArrayList<View>();
            //照片
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_photo, null);
                ImageView ivPhoto = (ImageView) itemView.findViewById(R.id.student_info_item_iv_photo);
                startPhotoDownload(cs.getPhotoUrl(), ivPhoto);
                itemViews.add(itemView);
            }
            //基本信息
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("基本信息");
                itemViews.add(itemView);
            }
            for (int n = 0; n < 34; n++) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                switch(n) {
                case 0:
                    tvTitle.setText("学号");
                    tvValue.setText(cs.getUserId() + "");
                    break;
                case 1:
                    tvTitle.setText("姓名");
                    tvValue.setText(cs.getName() + "");
                    break;
                case 2:
                    tvTitle.setText("英文名");
                    tvValue.setText(cs.getEnglishName() + "");
                    break;
                case 3:
                    tvTitle.setText("性别");
                    tvValue.setText(cs.getSex() + "");
                    break;
                case 4:
                    tvTitle.setText("国籍");
                    tvValue.setText(cs.getNationality() + "");
                    break;
                case 5:
                    tvTitle.setText("民族");
                    tvValue.setText(cs.getNation() + "");
                    break;
                case 6:
                    tvTitle.setText("政治面貌");
                    tvValue.setText(cs.getPoliticalAffiliation() + "");
                    break;
                case 7:
                    tvTitle.setText("证件类型");
                    tvValue.setText(cs.getIdCardType() + "");
                    break;
                case 8:
                    tvTitle.setText("证件号码");
                    tvValue.setText(cs.getIdCardNum() + "");
                    break;
                case 9:
                    tvTitle.setText("出生日期");
                    tvValue.setText(cs.getDateOfBirth() + "");
                    break;
                case 10:
                    tvTitle.setText("籍贯");
                    tvValue.setText(cs.getBirthplace() + "");
                    break;
                case 11:
                    tvTitle.setText("学院");
                    tvValue.setText(cs.getCollege() + "");
                    break;
                case 12:
                    tvTitle.setText("专业");
                    tvValue.setText(cs.getMajor() + "");
                    break;
                case 13:
                    tvTitle.setText("班级");
                    tvValue.setText(cs.getClassInfo() + "");
                    break;
                case 14:
                    tvTitle.setText("学生类型");
                    tvValue.setText(cs.getStudentType() + "");
                    break;
                case 15:
                    tvTitle.setText("学籍表号");
                    tvValue.setText(cs.getStudentInfoTableNum() + "");
                    break;
                case 16:
                    tvTitle.setText("考区");
                    tvValue.setText(cs.getEntranceExamArea() + "");
                    break;
                case 17:
                    tvTitle.setText("准考证号码");
                    tvValue.setText(cs.getEntranceExamNum() + "");
                    break;
                case 18:
                    tvTitle.setText("外语语种");
                    tvValue.setText(cs.getForeignLanguage() + "");
                    break;
                case 19:
                    tvTitle.setText("培养方式");
                    tvValue.setText(cs.getEducationType() + "");
                    break;
                case 20:
                    tvTitle.setText("录取证号");
                    tvValue.setText(cs.getAdmissionNum() + "");
                    break;
                case 21:
                    tvTitle.setText("录取方式");
                    tvValue.setText(cs.getAdmissionType() + "");
                    break;    
                case 22:
                    tvTitle.setText("学生来源");
                    tvValue.setText(cs.getSourceOfStudent() + "");
                    break;
                case 23:
                    tvTitle.setText("毕业学校");
                    tvValue.setText(cs.getGraduateSchool() + "");
                    break;
                case 24:
                    tvTitle.setText("高考总分");
                    tvValue.setText(cs.getEntranceExamScore() + "");
                    break;
                case 25:
                    tvTitle.setText("入学日期");
                    tvValue.setText(cs.getDateOfAdmission() + "");
                    break;
                case 26:
                    tvTitle.setText("毕业日期");
                    tvValue.setText(cs.getDateOfGraduation() + "");
                    break;
                case 27:
                    tvTitle.setText("毕业去向");
                    tvValue.setText(cs.getWhereaboutsAftergraduation() + "");
                    break;
                case 28:
                    tvTitle.setText("家庭地址");
                    tvValue.setText(cs.getHomeAddress() + "");
                    break;
                case 29:
                    tvTitle.setText("乘车区间");
                    tvValue.setText(cs.getTravelRange() + "");
                    break;
                case 30:
                    tvTitle.setText("联系电话");
                    tvValue.setText(cs.getContactTel() + "");
                    break;
                case 31:
                    tvTitle.setText("邮政编码");
                    tvValue.setText(cs.getZipCode() + "");
                    break;
                case 32:
                    tvTitle.setText("电子邮件");
                    tvValue.setText(cs.getEmail() + "");
                    break;
                case 33:
                    tvTitle.setText("备注");
                    tvValue.setText(cs.getRemarks() + "");
                    break;
                }
                //添加到布局中
                itemViews.add(itemView);
            }
            //高考科目
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("高考科目");
                itemViews.add(itemView);
            }
            for (int n = 0; n < cs.getEntranceExams().size(); n++) {
                ClientEntranceExam ee = cs.getEntranceExams().get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText(ee.getName() + "");
                tvValue.setText(ee.getScore() + "");
                //添加到布局
                itemViews.add(itemView);
            }
            //没有高考科目
            if (cs.getEntranceExams().size() == 0) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText("暂无信息");
                tvValue.setText("");
                itemViews.add(itemView);
            }
            //教育经历
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("教育经历");
                itemViews.add(itemView);
            }
            for (int n = 0; n < cs.getEducationExperiences().size(); n++) {
                ClientEducationExperience ee = cs.getEducationExperiences().get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_student_info_item_edex, null);
                TextView tvDateOfStart = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_start);
                TextView tvDateOfEnd = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_end);
                TextView tvSchoolName = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_school_name);
                TextView tvWitness = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_witness);
                tvDateOfStart.setText(ee.getDateOfStart() + "");
                tvDateOfEnd.setText(ee.getDateOfEnd() + "");
                tvSchoolName.setText(ee.getSchoolName() + "");
                tvWitness.setText(ee.getWitness() + "");
                //填充
                itemViews.add(itemView);
            }
            //没有教育经历
            if (cs.getEducationExperiences().size() == 0) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText("暂无信息");
                tvValue.setText("");
                itemViews.add(itemView);
            }
            //家庭信息
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("家庭信息");
                itemViews.add(itemView);
            }
            for (int n = 0; n < cs.getFamilys().size(); n++) {
                ClientFamily f = cs.getFamilys().get(n);
                //布局
                View itemView = inflater.inflate(R.layout.activity_student_info_item_family, null);
                TextView tvName = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_name);
                TextView tvRelationship = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_relationship);
                TextView tvPolAff = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_political_affiliation);
                TextView tvJob = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_job);
                TextView tvPost = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_post);
                TextView tvWorkLocation = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_work_location);
                TextView tvTel = (TextView) itemView.findViewById(R.id.student_info_item_family_tv_tel);
                tvName.setText(f.getName() + "");
                tvRelationship.setText(f.getRelationship() + "");
                tvPolAff.setText(f.getPoliticalAffiliation() + "");
                tvJob.setText(f.getJob() + "");
                tvPost.setText(f.getPost() + "");
                tvWorkLocation.setText(f.getWorkLocation() + "");
                tvTel.setText(f.getTel() + "");
                //填充
                itemViews.add(itemView);
            }
            //没有家庭信息
            if (cs.getFamilys().size() == 0) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText("暂无信息");
                tvValue.setText("");
                itemViews.add(itemView);
            }
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public Object getItem(int position) {
            return itemViews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return itemViews.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return itemViews.get(position);
        }

    }

    private void startPhotoDownload(String url, ImageView imageView) {
        HttpUtil.baseGet(this, url, new PhotoDownloadHandler(imageView));
    }
    
    private class PhotoDownloadHandler extends AsyncHttpResponseHandler {

        private ImageView imageView;
        
        public PhotoDownloadHandler(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                Bitmap bmp = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                imageView.setImageBitmap(bmp);
            } catch(Exception e) {
                Toast.makeText(imageView.getContext(), "用户头像解析失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(imageView.getContext(), "用户头像获取失败", Toast.LENGTH_SHORT).show();
        }

    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

}
