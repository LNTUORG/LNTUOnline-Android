package org.lntu.online.display.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.lntu.online.R;
import org.lntu.online.model.api.ApiClient;
import org.lntu.online.model.api.DefaultCallback;
import org.lntu.online.model.entity.ErrorInfo;
import org.lntu.online.model.entity.Student;
import org.lntu.online.model.storage.CacheShared;
import org.lntu.online.model.storage.LoginShared;
import org.lntu.online.display.adapter.StudentInfoAdapter;
import org.lntu.online.display.base.StatusBarActivity;
import org.lntu.online.display.listener.NavigationFinishClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.client.Response;

public class StudentInfoActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.img_avatar)
    protected ImageView imgAvatar;

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    private Student student;
    private StudentInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        student = CacheShared.getStudent(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentInfoAdapter(this, student);
        recyclerView.setAdapter(adapter);

        loadAvatar(student);
        getStudentAsyncTask();
    }

    private void loadAvatar(Student student) {
        if (student != null && !TextUtils.isEmpty(student.getPhotoUrl())) {
            Picasso.with(this).load(student.getPhotoUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
        }
    }

    private void getStudentAsyncTask() {
        ApiClient.service.getStudent(LoginShared.getLoginToken(this), new DefaultCallback<Student>(this) {

            @Override
            public void success(Student student, Response response) {
                CacheShared.setStudent(StudentInfoActivity.this, student);
                StudentInfoActivity.this.student = student;
                loadAvatar(student);
                adapter.setStudent(student);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ErrorInfo errorInfo) {
                if (student == null) {
                    super.failure(errorInfo);
                }
            }

        });
    }

}
