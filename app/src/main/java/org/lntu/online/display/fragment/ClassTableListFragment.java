package org.lntu.online.display.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.LocalDate;
import org.lntu.online.R;
import org.lntu.online.display.activity.ClassTableActivity;
import org.lntu.online.display.adapter.ClassTableListAdapter;
import org.lntu.online.model.entity.ClassTable;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassTableListFragment extends ClassTableActivity.BaseFragment {

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    private ClassTableListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_class_table_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClassTableListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDataSetInit(int year, String term, LocalDate today) {
        // List布局不在这里初始化
    }

    @Override
    public void onDataSetUpdate(ClassTable classTable, Map<String, List<ClassTable.CourseWrapper>> classTableMap) {
        adapter.updateClassTable(classTable);
    }

}
