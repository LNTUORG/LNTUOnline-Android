package org.lntu.online.ui.listener;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class NavigationOpenClickListener implements View.OnClickListener {

    private DrawerLayout drawerLayout;

    public NavigationOpenClickListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onClick(View v) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

}
