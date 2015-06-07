package org.lntu.online.ui.base;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.lntu.online.R;

import org.lntu.online.model.entity.ClassTable;
import org.lntu.online.util.ShipUtils;

import java.util.List;
import java.util.Map;

public abstract class ClassTableFragment extends Fragment {

    public abstract void updateDataView(ClassTable classTable, Map<String, List<ClassTable.Course>> classTableMap);

}
