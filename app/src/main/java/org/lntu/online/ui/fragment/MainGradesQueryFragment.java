package org.lntu.online.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.lntu.online.R;
import org.lntu.online.ui.activity.MainActivity;
import org.lntu.online.ui.activity.OneKeyEvaActivity;
import org.lntu.online.util.ShipUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

public class MainGradesQueryFragment extends MainActivity.BaseFragment implements Toolbar.OnMenuItemClickListener {

    @Bind(R.id.main_grades_query_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.main_grades_query_tab_layout)
    protected TabLayout tabLayout;

    @Bind(R.id.main_grades_query_view_pager)
    protected ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_grades_query, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        toolbar.setNavigationOnClickListener(getOpenNavigationClickListener());
        toolbar.inflateMenu(R.menu.grades_query);
        toolbar.setOnMenuItemClickListener(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one_key_eva:
                startActivity(new Intent(getActivity(), OneKeyEvaActivity.class));
                return true;
            default:
                return false;
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fmList = new ArrayList<>();
        private String[] titles = {
                "课程成绩",
                "未通过课程",
                "技能考试"
        };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fmList.add(new MainGradesQueryCourseFragment());
            fmList.add(new MainGradesQueryUnpassFragment());
            fmList.add(new MainGradesQuerySkillFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fmList.get(position);
        }

        @Override
        public int getCount() {
            return fmList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

}
