package com.cnrobin.mms_sellpart.function.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.adapters.MyViewPagerAdapter;

import java.util.ArrayList;


public class GoodsBrowseFragment extends Fragment {
    ArrayList<Fragment> fragments;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] kinds = {"风衣", "西装", "衬衣", "针织衫", "连衣裙", "半身裙", "长裤"};


    public GoodsBrowseFragment() {
        // Required empty public constructor
    }


    public static GoodsBrowseFragment newInstance(Bundle bundle) {
        GoodsBrowseFragment fragment = new GoodsBrowseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_browse, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        initData();
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, kinds);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(7);
        tabLayout.setupWithViewPager(viewPager);

    }

    void initData() {
        for (int i = 0; i < 7; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("kind", kinds[i]);
            fragments.add(GoodsFragment.newInstance(bundle));
        }


    }

}
