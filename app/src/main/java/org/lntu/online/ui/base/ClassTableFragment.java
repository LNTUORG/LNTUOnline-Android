package org.lntu.online.ui.base;

import android.support.v4.app.Fragment;

import org.joda.time.LocalDate;
import org.lntu.online.model.entity.ClassTable;

import java.util.List;
import java.util.Map;

public abstract class ClassTableFragment extends Fragment {

    public abstract void onDataSetInit(int year, String term, LocalDate today);

    public abstract void onDataSetUpdate(ClassTable classTable, Map<String, List<ClassTable.Course>> classTableMap);

}
