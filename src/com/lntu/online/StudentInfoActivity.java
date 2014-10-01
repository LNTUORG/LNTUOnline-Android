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
                    showErrorDialog("��ʾ", msgs[0], msgs[1]);
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
            //��Ƭ
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_photo, null);
                ImageView ivPhoto = (ImageView) itemView.findViewById(R.id.student_info_item_iv_photo);
                startPhotoDownload(cs.getPhotoUrl(), ivPhoto);
                itemViews.add(itemView);
            }
            //������Ϣ
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("������Ϣ");
                itemViews.add(itemView);
            }
            for (int n = 0; n < 34; n++) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                switch(n) {
                case 0:
                    tvTitle.setText("ѧ��");
                    tvValue.setText(cs.getUserId() + "");
                    break;
                case 1:
                    tvTitle.setText("����");
                    tvValue.setText(cs.getName() + "");
                    break;
                case 2:
                    tvTitle.setText("Ӣ����");
                    tvValue.setText(cs.getEnglishName() + "");
                    break;
                case 3:
                    tvTitle.setText("�Ա�");
                    tvValue.setText(cs.getSex() + "");
                    break;
                case 4:
                    tvTitle.setText("����");
                    tvValue.setText(cs.getNationality() + "");
                    break;
                case 5:
                    tvTitle.setText("����");
                    tvValue.setText(cs.getNation() + "");
                    break;
                case 6:
                    tvTitle.setText("������ò");
                    tvValue.setText(cs.getPoliticalAffiliation() + "");
                    break;
                case 7:
                    tvTitle.setText("֤������");
                    tvValue.setText(cs.getIdCardType() + "");
                    break;
                case 8:
                    tvTitle.setText("֤������");
                    tvValue.setText(cs.getIdCardNum() + "");
                    break;
                case 9:
                    tvTitle.setText("��������");
                    tvValue.setText(cs.getDateOfBirth() + "");
                    break;
                case 10:
                    tvTitle.setText("����");
                    tvValue.setText(cs.getBirthplace() + "");
                    break;
                case 11:
                    tvTitle.setText("ѧԺ");
                    tvValue.setText(cs.getCollege() + "");
                    break;
                case 12:
                    tvTitle.setText("רҵ");
                    tvValue.setText(cs.getMajor() + "");
                    break;
                case 13:
                    tvTitle.setText("�༶");
                    tvValue.setText(cs.getClassInfo() + "");
                    break;
                case 14:
                    tvTitle.setText("ѧ������");
                    tvValue.setText(cs.getStudentType() + "");
                    break;
                case 15:
                    tvTitle.setText("ѧ�����");
                    tvValue.setText(cs.getStudentInfoTableNum() + "");
                    break;
                case 16:
                    tvTitle.setText("����");
                    tvValue.setText(cs.getEntranceExamArea() + "");
                    break;
                case 17:
                    tvTitle.setText("׼��֤����");
                    tvValue.setText(cs.getEntranceExamNum() + "");
                    break;
                case 18:
                    tvTitle.setText("��������");
                    tvValue.setText(cs.getForeignLanguage() + "");
                    break;
                case 19:
                    tvTitle.setText("������ʽ");
                    tvValue.setText(cs.getEducationType() + "");
                    break;
                case 20:
                    tvTitle.setText("¼ȡ֤��");
                    tvValue.setText(cs.getAdmissionNum() + "");
                    break;
                case 21:
                    tvTitle.setText("¼ȡ��ʽ");
                    tvValue.setText(cs.getAdmissionType() + "");
                    break;    
                case 22:
                    tvTitle.setText("ѧ����Դ");
                    tvValue.setText(cs.getSourceOfStudent() + "");
                    break;
                case 23:
                    tvTitle.setText("��ҵѧУ");
                    tvValue.setText(cs.getGraduateSchool() + "");
                    break;
                case 24:
                    tvTitle.setText("�߿��ܷ�");
                    tvValue.setText(cs.getEntranceExamScore() + "");
                    break;
                case 25:
                    tvTitle.setText("��ѧ����");
                    tvValue.setText(cs.getDateOfAdmission() + "");
                    break;
                case 26:
                    tvTitle.setText("��ҵ����");
                    tvValue.setText(cs.getDateOfGraduation() + "");
                    break;
                case 27:
                    tvTitle.setText("��ҵȥ��");
                    tvValue.setText(cs.getWhereaboutsAftergraduation() + "");
                    break;
                case 28:
                    tvTitle.setText("��ͥ��ַ");
                    tvValue.setText(cs.getHomeAddress() + "");
                    break;
                case 29:
                    tvTitle.setText("�˳�����");
                    tvValue.setText(cs.getTravelRange() + "");
                    break;
                case 30:
                    tvTitle.setText("��ϵ�绰");
                    tvValue.setText(cs.getContactTel() + "");
                    break;
                case 31:
                    tvTitle.setText("��������");
                    tvValue.setText(cs.getZipCode() + "");
                    break;
                case 32:
                    tvTitle.setText("�����ʼ�");
                    tvValue.setText(cs.getEmail() + "");
                    break;
                case 33:
                    tvTitle.setText("��ע");
                    tvValue.setText(cs.getRemarks() + "");
                    break;
                }
                //��ӵ�������
                itemViews.add(itemView);
            }
            //�߿���Ŀ
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("�߿���Ŀ");
                itemViews.add(itemView);
            }
            for (int n = 0; n < cs.getEntranceExams().size(); n++) {
                ClientEntranceExam ee = cs.getEntranceExams().get(n);
                //����
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText(ee.getName() + "");
                tvValue.setText(ee.getScore() + "");
                //��ӵ�����
                itemViews.add(itemView);
            }
            //û�и߿���Ŀ
            if (cs.getEntranceExams().size() == 0) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText("������Ϣ");
                tvValue.setText("");
                itemViews.add(itemView);
            }
            //��������
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("��������");
                itemViews.add(itemView);
            }
            for (int n = 0; n < cs.getEducationExperiences().size(); n++) {
                ClientEducationExperience ee = cs.getEducationExperiences().get(n);
                //����
                View itemView = inflater.inflate(R.layout.activity_student_info_item_edex, null);
                TextView tvDateOfStart = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_start);
                TextView tvDateOfEnd = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_date_of_end);
                TextView tvSchoolName = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_school_name);
                TextView tvWitness = (TextView) itemView.findViewById(R.id.student_info_item_edex_tv_witness);
                tvDateOfStart.setText(ee.getDateOfStart() + "");
                tvDateOfEnd.setText(ee.getDateOfEnd() + "");
                tvSchoolName.setText(ee.getSchoolName() + "");
                tvWitness.setText(ee.getWitness() + "");
                //���
                itemViews.add(itemView);
            }
            //û�н�������
            if (cs.getEducationExperiences().size() == 0) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText("������Ϣ");
                tvValue.setText("");
                itemViews.add(itemView);
            }
            //��ͥ��Ϣ
            {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_category, null);
                TextView tvCategory = (TextView) itemView.findViewById(R.id.student_info_item_tv_category);
                tvCategory.setText("��ͥ��Ϣ");
                itemViews.add(itemView);
            }
            for (int n = 0; n < cs.getFamilys().size(); n++) {
                ClientFamily f = cs.getFamilys().get(n);
                //����
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
                //���
                itemViews.add(itemView);
            }
            //û�м�ͥ��Ϣ
            if (cs.getFamilys().size() == 0) {
                View itemView = inflater.inflate(R.layout.activity_student_info_item_hor, null);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.student_info_item_tv_title);
                TextView tvValue = (TextView) itemView.findViewById(R.id.student_info_item_tv_value);
                tvTitle.setText("������Ϣ");
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
                Toast.makeText(imageView.getContext(), "�û�ͷ�����ʧ��", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(imageView.getContext(), "�û�ͷ���ȡʧ��", Toast.LENGTH_SHORT).show();
        }

    }

    public void onActionBarBtnLeft(View view) {
    	finish();
    }

}
