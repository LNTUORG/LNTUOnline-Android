package org.lntu.online.ui.base;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import org.lntu.online.util.ResUtils;

public abstract class DrawerLayoutActivity extends FullLayoutActivity {

    public void adaptStatusBar(View statusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ResUtils.getStatusBarHeight(this);
            statusBar.setLayoutParams(layoutParams);
        }
    }

}
