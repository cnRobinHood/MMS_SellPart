package com.cnrobin.mms_sellpart.function.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.functionUtils.StatisticsView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsStatisticsFragment extends Fragment {
    private StatisticsView statisticsView;
    private String[] bottomStr = new String[5];
    private float[] floats = {50, 65, 70, 80, 90};

    public GoodsStatisticsFragment() {
    }

    public static GoodsStatisticsFragment newInstance(Bundle args) {
        GoodsStatisticsFragment fragment = new GoodsStatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statisticsView = view.findViewById(R.id.statistics);
        init();
        statisticsView.setBottomStr(bottomStr);
        statisticsView.setValues(floats);
    }

    private void init() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        bottomStr[4] = (month + 1) + "月" + day + "日";
        for (int i = 3; i >= 0; i--) {
            cal.add(Calendar.DATE, -1);
            bottomStr[i] = (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DATE) + "日";
        }
    }
}
