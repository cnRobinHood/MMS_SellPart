package com.cnrobin.mms_sellpart.function.view;

import android.content.Intent;
import android.content.res.ColorStateList;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsAddFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsBrowseFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsSearchFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.GoodsStatisticsFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.NeedPredictFragment;
import com.cnrobin.mms_sellpart.function.view.fragments.SetupFragment;
import com.cnrobin.mms_sellpart.login.model.User;
import com.cnrobin.mms_sellpart.util.ActivityCollector;

public class FunctionActivity extends AppCompatActivity {
    private static final String TAG = "FunctionActivity";
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageView buttonHome;
    private User mUser;
    private TextView mTitleTextView;
    private long oldTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("userBundle");
        if (bundle != null) {
            mUser = bundle.getParcelable("user");
        }
        ActivityCollector.addActivity(this);
        mToolbar = findViewById(R.id.toolbar);
        mTitleTextView = findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.checked_color),
                getResources().getColor(R.color.uncheck_color)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);
        View navHeader = mNavigationView.inflateHeaderView(R.layout.nav_header);
        TextView navUserName = navHeader.findViewById(R.id.nav_username);
        TextView navFocus = navHeader.findViewById(R.id.nav_focus);
        navFocus.setText(mUser.getFoucs());
        navUserName.setText(mUser.getUsername());
        navHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userInfoIntent = new Intent(FunctionActivity.this, UserInfoActivity.class);
                userInfoIntent.putExtra("userBundle", bundle);
                startActivity(userInfoIntent);
            }
        });
        buttonHome = findViewById(R.id.home_doge);
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
        mTitleTextView.setText("商品浏览");
        mNavigationView.setCheckedItem(R.id.branwse_goods);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.branwse_goods:
                        replaceFragment(GoodsBrowseFragment.newInstance(new Bundle()));
                        mTitleTextView.setText("商品浏览");
                        break;
                    case R.id.add_goods:
                        replaceFragment(GoodsAddFragment.newInstance(new Bundle()));
                        mTitleTextView.setText("商品添加");

                        break;
                    case R.id.search_goods:
                        replaceFragment(GoodsSearchFragment.newInstance(new Bundle()));
                        mTitleTextView.setText("商品搜索");

                        break;
                    case R.id.statistics_goods:

                        replaceFragment(GoodsStatisticsFragment.newInstance(new Bundle()));
                        mTitleTextView.setText("商品统计");

                        break;
                    case R.id.setup_goods:
                        replaceFragment(SetupFragment.newInstance(new Bundle()));
                        mTitleTextView.setText("设置");
                        break;
                    case R.id.predict_goods:
                        replaceFragment(NeedPredictFragment.newInstance(new Bundle()));
                        mTitleTextView.setText("商品预测");

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
        if (oldTime == 0) {
            oldTime = System.currentTimeMillis();
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();

        } else if (System.currentTimeMillis() - oldTime < 2000) {
            ActivityCollector.removeAll();
        } else {
            oldTime = 0;
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();

        }


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.linear, fragment);
        transaction.commit();
    }
}
