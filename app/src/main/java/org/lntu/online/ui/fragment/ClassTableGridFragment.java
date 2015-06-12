package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lntu.online.R;

import org.joda.time.LocalDate;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.ui.base.ClassTableFragment;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class ClassTableGridFragment extends ClassTableFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_class_table_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @Override
    public void onDataSetInit(int year, String term, LocalDate today) {
        // 不在这里初始化
    }

    @Override
    public void onDataSetUpdate(ClassTable classTable, Map<String, List<ClassTable.CourseWrapper>> classTableMap) {

    }

}
