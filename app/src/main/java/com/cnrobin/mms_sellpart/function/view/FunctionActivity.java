package com.cnrobin.mms_sellpart.function.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsAddFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsBrowseFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsSearchFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsStatisticsFragment;
import com.cnrobin.mms_sellpart.util.ActivityCollector;

public class FunctionActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageView buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        ActivityCollector.addActivity(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        buttonHome = (ImageView) findViewById(R.id.home_doge);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        replaceFragment(GoodsBrowseFragment.newInstance(new Bundle()));
        mNavigationView.setCheckedItem(R.id.branwse_goods);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.branwse_goods:
                        replaceFragment(GoodsBrowseFragment.newInstance(new Bundle()));
                        break;
                    case R.id.add_goods:
                        replaceFragment(GoodsAddFragment.newInstance(new Bundle()));
                        break;
                    case R.id.search_goods:
                        replaceFragment(GoodsSearchFragment.newInstance(new Bundle()));
                        break;
                    case R.id.statistics_goods:
                        replaceFragment(GoodsStatisticsFragment.newInstance(new Bundle()));
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.removeAll();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.linear, fragment);
        transaction.commit();
    }
}
