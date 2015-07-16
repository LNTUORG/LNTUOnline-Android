package org.lntu.online.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.model.entity.Student;
import org.lntu.online.util.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EDEX = 1;
    private static final int TYPE_FAMILY = 2;
    private static final int TYPE_ACTION = 3;
    private static final int TYPE_TITLE = 4;
    private static final int TYPE_DEEPLINE = 5;
    private static final int TYPE_TOP = 6;
    private static final int TYPE_BOTTOM = 7;

    private LayoutInflater inflater;
    private List<Integer> typeList = new ArrayList<>();
    private Map<Integer, Object> sourceMap = new HashMap<>();

    public StudentInfoAdapter(Context context, Student student) {
        inflater = LayoutInflater.from(context);
        setStudent(student);
    }

    public void setStudent(Student student) {
        if (student != null) {
            typeList.clear();
            sourceMap.clear();

            // 添加顶部边距
            typeList.add(TYPE_TOP);
            // 添加基本信息
            for (int n = 0; n < 34; n++) {
                String[] kv = new String[2];
                switch (n) {
                    case 0:
                        kv[0] = "学号";
                        kv[1] = student.getId();
                        break;
                    case 1:
                        kv[0] = "姓名";
                        kv[1] = student.getName();
                        break;
                    case 2:
                        kv[0] = "英文名";
                        kv[1] = student.getEnglishName();
                        break;
                    case 3:
                        kv[0] = "性别";
                        kv[1] = student.getSex();
                        break;
                    case 4:
                        kv[0] = "国籍";
                        kv[1] = student.getNationality();
                        break;
                    case 5:
                        kv[0] = "民族";
                        kv[1] = student.getNation();
                        break;
                    case 6:
                        kv[0] = "政治面貌";
                        kv[1] = student.getPoliticalAffiliation();
                        break;
                    case 7:
                        kv[0] = "证件类型";
                        kv[1] = student.getIdCardType();
                        break;
                    case 8:
                        kv[0] = "证件号码";
                        kv[1] = student.getIdCardNum();
                        break;
                    case 9:
                        kv[0] = "出生日期";
                        kv[1] = TimeUtils.getTimeFormat(student.getBirthday());
                        break;
                    case 10:
                        kv[0] = "籍贯";
                        kv[1] = student.getBirthplace();
                        break;
                    case 11:
                        kv[0] = "学院";
                        kv[1] = student.getCollege();
                        break;
                    case 12:
                        kv[0] = "专业";
                        kv[1] = student.getMajor();
                        break;
                    case 13:
                        kv[0] = "班级";
                        kv[1] = student.getClassInfo();
                        break;
                    case 14:
                        kv[0] = "学生类型";
                        kv[1] = student.getStudentType();
                        break;
                    case 15:
                        kv[0] = "学籍表号";
                        kv[1] = student.getStudentInfoTableNum();
                        break;
                    case 16:
                        kv[0] = "考区";
                        kv[1] = student.getEntranceExamArea();
                        break;
                    case 17:
                        kv[0] = "准考证号码";
                        kv[1] = student.getEntranceExamNum();
                        break;
                    case 18:
                        kv[0] = "外语语种";
                        kv[1] = student.getForeignLanguage();
                        break;
                    case 19:
                        kv[0] = "培养方式";
                        kv[1] = student.getEducationType();
                        break;
                    case 20:
                        kv[0] = "录取证号";
                        kv[1] = student.getAdmissionNum();
                        break;
                    case 21:
                        kv[0] = "录取方式";
                        kv[1] = student.getAdmissionType();
                        break;
                    case 22:
                        kv[0] = "学生来源";
                        kv[1] = student.getSourceOfStudent();
                        break;
                    case 23:
                        kv[0] = "毕业学校";
                        kv[1] = student.getGraduateSchool();
                        break;
                    case 24:
                        kv[0] = "高考总分";
                        kv[1] = student.getEntranceExamScore();
                        break;
                    case 25:
                        kv[0] = "入学日期";
                        kv[1] = TimeUtils.getTimeFormat(student.getAdmissionTime());
                        break;
                    case 26:
                        kv[0] = "毕业日期";
                        kv[1] = TimeUtils.getTimeFormat(student.getGraduationTime());
                        break;
                    case 27:
                        kv[0] = "毕业去向";
                        kv[1] = student.getWhereaboutsAftergraduation();
                        break;
                    case 28:
                        kv[0] = "家庭地址";
                        kv[1] = student.getHomeAddress();
                        break;
                    case 29:
                        kv[0] = "乘车区间";
                        kv[1] = student.getTravelRange();
                        break;
                    case 30:
                        kv[0] = "联系电话";
                        kv[1] = student.getTel();
                        break;
                    case 31:
                        kv[0] = "邮政编码";
                        kv[1] = student.getZipCode();
                        break;
                    case 32:
                        kv[0] = "电子邮件";
                        kv[1] = student.getEmail();
                        break;
                    case 33:
                        kv[0] = "备注";
                        kv[1] = student.getRemarks();
                        break;
                }
                typeList.add(TYPE_NORMAL);
                sourceMap.put(typeList.size() - 1, kv);
                //添加深度线
                if (n != 34 - 1) {
                    typeList.add(TYPE_DEEPLINE);
                }
            }
            // 高考科目
            if (student.getEntranceExams() != null && student.getEntranceExams().size() > 0) {
                // 添加标题
                typeList.add(TYPE_TITLE);
                Object[] objs = new Object[2];
                objs[0] = "高考科目";
                objs[1] = R.drawable.student_info_ic_exam_theme_18dp;
                sourceMap.put(typeList.size() - 1, objs);
                // 添加项目
                for (int n = 0; n < student.getEntranceExams().size(); n++) {
                    typeList.add(TYPE_NORMAL);
                    String[] kv = new String[2];
                    Student.EntranceExam ee = student.getEntranceExams().get(n);
                    kv[0] = ee.getName();
                    kv[1] = ee.getScore();
                    sourceMap.put(typeList.size() - 1, kv);
                    //添加深度线
                    if (n != student.getEntranceExams().size() - 1) {
                        typeList.add(TYPE_DEEPLINE);
                    }
                }
            }
            // 教育经历
            if (student.getEducationExperiences() != null && student.getEducationExperiences().size() > 0) {
                // 添加标题
                typeList.add(TYPE_TITLE);
                Object[] objs = new Object[2];
                objs[0] = "教育经历";
                objs[1] = R.drawable.student_info_ic_edex_theme_18dp;
                sourceMap.put(typeList.size() - 1, objs);
                // 添加项目
                for (int n = 0; n < student.getEducationExperiences().size(); n++) {
                    typeList.add(TYPE_EDEX);
                    Student.EducationExperience ee = student.getEducationExperiences().get(n);
                    sourceMap.put(typeList.size() - 1, ee);
                    //添加深度线
                    if (n != student.getEducationExperiences().size() - 1) {
                        typeList.add(TYPE_DEEPLINE);
                    }
                }
            }
            // 家庭信息
            if (student.getFamilys() != null && student.getFamilys().size() > 0) {
                // 添加标题
                typeList.add(TYPE_TITLE);
                Object[] objs = new Object[2];
                objs[0] = "家庭情况";
                objs[1] = R.drawable.student_info_ic_family_theme_18dp;
                sourceMap.put(typeList.size() - 1, objs);
                // 添加项目
                for (int n = 0; n < student.getFamilys().size(); n++) {
                    typeList.add(TYPE_FAMILY);
                    Student.Family family = student.getFamilys().get(n);
                    sourceMap.put(typeList.size() - 1, family);
                    //添加深度线
                    if (n != student.getFamilys().size() - 1) {
                        typeList.add(TYPE_DEEPLINE);
                    }
                }
            }
            // 警告处分
            if (student.getDisciplinaryActions() != null && student.getDisciplinaryActions().size() > 0) {
                // 添加标题
                typeList.add(TYPE_TITLE);
                Object[] objs = new Object[2];
                objs[0] = "警告处分";
                objs[1] = R.drawable.student_info_ic_action_theme_18dp;
                sourceMap.put(typeList.size() - 1, objs);
                // 添加项目
                for (int n = 0; n < student.getDisciplinaryActions().size(); n++) {
                    typeList.add(TYPE_ACTION);
                    Student.DisciplinaryAction action = student.getDisciplinaryActions().get(n);
                    sourceMap.put(typeList.size() - 1, action);
                    //添加深度线
                    if (n != student.getDisciplinaryActions().size() - 1) {
                        typeList.add(TYPE_DEEPLINE);
                    }
                }
            }
            // 底部
            typeList.add(TYPE_BOTTOM);
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return typeList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.activity_student_info_item_normal, parent, false));
            case TYPE_EDEX:
                return new EdexViewHolder(inflater.inflate(R.layout.activity_student_info_item_edex, parent, false));
            case TYPE_FAMILY:
                return new FamilyViewHolder(inflater.inflate(R.layout.activity_student_info_item_family, parent, false));
            case TYPE_ACTION:
                return new ActionViewHolder(inflater.inflate(R.layout.activity_student_info_item_action, parent, false));
            case TYPE_TITLE:
                return new TitleViewHolder(inflater.inflate(R.layout.activity_student_info_item_title, parent, false));
            case TYPE_DEEPLINE:
                return new ViewHolder(inflater.inflate(R.layout.activity_deep_line, parent, false));
            case TYPE_TOP:
                return new ViewHolder(inflater.inflate(R.layout.activity_student_info_item_top, parent, false));
            case TYPE_BOTTOM:
                return new ViewHolder(inflater.inflate(R.layout.activity_shadow_gap, parent, false));
            default:
                throw new RuntimeException("Unknow view type.");
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void update(int position) {}

    }

    public class NormalViewHolder extends ViewHolder {

        @Bind(R.id.student_info_item_normal_tv_name)
        protected TextView tvName;

        @Bind(R.id.student_info_item_normal_tv_value)
        protected TextView tvValue;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            String[] kv = (String[]) sourceMap.get(position);
            tvName.setText(kv[0]);
            tvValue.setText(kv[1]);
        }

    }

    public class EdexViewHolder extends ViewHolder {

        @Bind(R.id.student_info_item_edex_tv_date_of_start)
        protected TextView tvDateOfStart;

        @Bind(R.id.student_info_item_edex_tv_date_of_end)
        protected TextView tvDateOfEnd;

        @Bind(R.id.student_info_item_edex_tv_school_name)
        protected TextView tvSchoolName;

        @Bind(R.id.student_info_item_edex_tv_witness)
        protected TextView tvWitness;

        public EdexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            Student.EducationExperience ee = (Student.EducationExperience) sourceMap.get(position);
            tvDateOfStart.setText(TimeUtils.getTimeFormat(ee.getStartTime()));
            tvDateOfEnd.setText(TimeUtils.getTimeFormat(ee.getEndTime()));
            tvSchoolName.setText(ee.getSchoolInfo());
            tvWitness.setText(ee.getWitness());
        }

    }

    public class FamilyViewHolder extends ViewHolder {

        @Bind(R.id.student_info_item_family_tv_name)
        protected TextView tvName;

        @Bind(R.id.student_info_item_family_tv_relationship)
        protected TextView tvRelationship;

        @Bind(R.id.student_info_item_family_tv_political_affiliation)
        protected TextView tvPolAff;

        @Bind(R.id.student_info_item_family_tv_job)
        protected TextView tvJob;

        @Bind(R.id.student_info_item_family_tv_post)
        protected TextView tvPost;

        @Bind(R.id.student_info_item_family_tv_work_location)
        protected TextView tvWorkLocation;

        @Bind(R.id.student_info_item_family_tv_tel)
        protected TextView tvTel;

        public FamilyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            Student.Family family = (Student.Family) sourceMap.get(position);
            tvName.setText(family.getName());
            tvRelationship.setText("（" + family.getRelationship() + "）");
            tvPolAff.setText(family.getPoliticalAffiliation());
            tvJob.setText(family.getJob());
            tvPost.setText(family.getPost());
            tvWorkLocation.setText(family.getWorkLocation());
            tvTel.setText(family.getTel());
        }

    }

    public class ActionViewHolder extends ViewHolder {

        @Bind(R.id.student_info_item_action_tv_level)
        protected TextView tvLevel;

        @Bind(R.id.student_info_item_action_tv_create_time)
        protected TextView tvCreateTime;

        @Bind(R.id.student_info_item_action_tv_create_reason)
        protected TextView tvCreateReason;

        @Bind(R.id.student_info_item_action_tv_cancel_time)
        protected TextView tvCancelTime;

        @Bind(R.id.student_info_item_action_tv_cancel_reason)
        protected TextView tvCancelReason;

        @Bind(R.id.student_info_item_action_tv_state)
        protected TextView tvState;

        @Bind(R.id.student_info_item_action_tv_remarks)
        protected TextView tvRemarks;

        public ActionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            Student.DisciplinaryAction action = (Student.DisciplinaryAction) sourceMap.get(position);
            tvLevel.setText(action.getLevel());
            tvCreateTime.setText(TimeUtils.getTimeFormat(action.getCreateTime()));
            tvCreateReason.setText(action.getCreateReason());
            tvCancelTime.setText(TimeUtils.getTimeFormat(action.getCancelTime()));
            tvCancelReason.setText(action.getCancelReason());
            tvState.setText(action.getState());
            tvRemarks.setText(action.getRemarks());
        }

    }

    public class TitleViewHolder extends ViewHolder {

        @Bind(R.id.student_info_item_title_tv_title)
        protected TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            Object[] objs  = (Object[]) sourceMap.get(position);
            tvTitle.setText((String) objs[0]);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds((int) objs[1], 0, 0, 0);
        }

    }

}
