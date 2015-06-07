package org.lntu.online.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lntu.online.R;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.ui.adapter.ClassTablePageAdapter;
import org.lntu.online.ui.base.ClassTableFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassTablePageFragment extends ClassTableFragment {

    @InjectView(R.id.class_table_page_view_pager)
    protected ViewPager viewPager;

    private ClassTablePageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_class_table_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        adapter = new ClassTablePageAdapter(getActivity());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void updateDataView(ClassTable classTable) {
        for (ClassTable.Course course : classTable.getCourses()) {
            for (ClassTable.TimeAndPlace timeAndPlace : course.getTimesAndPlaces()) {

                // timeAndPlace.

            }
        }
    }

}
