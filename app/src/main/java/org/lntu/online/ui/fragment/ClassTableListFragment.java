package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lntu.online.R;

import org.joda.time.LocalDate;
import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.ui.adapter.ClassTableListAdapter;
import org.lntu.online.ui.base.ClassTableFragment;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassTableListFragment extends ClassTableFragment {

    @InjectView(R.id.class_table_list_recycler_view)
    protected RecyclerView recyclerView;

    private ClassTableListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_class_table_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClassTableListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDataSetInit(int year, String term, LocalDate today) {
        // 不在这里初始化
    }

    @Override
    public void onDataSetUpdate(ClassTable classTable, Map<String, List<ClassTable.Course>> classTableMap) {
        adapter.updateClassTable(classTable);
    }

}
