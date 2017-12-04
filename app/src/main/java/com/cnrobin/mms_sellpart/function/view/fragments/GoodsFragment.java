package com.cnrobin.mms_sellpart.function.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.adapters.GoodsAdapter;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.presenter.BrowsePersenterImp;

import java.util.List;

public class GoodsFragment extends Fragment implements FunctionContect.BrowseView {
    private static final String TAG = "GoodsFragment";
    private int count = 0;
    private ProgressBar progressBar;
    private Bundle bundle;
    private List<ClothInfo> list;
    private boolean isLoading = false;
    private GoodsAdapter mAdapter;
    private RecyclerView recyclerView;
    private BrowsePersenterImp persenter;

    public GoodsFragment() {
        setPresenter(new BrowsePersenterImp(this));
    }


    public static GoodsFragment newInstance(Bundle bundle) {
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void loadMoreData(List<ClothInfo> list) {
        mAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void setPresenter(Object presenter) {
        this.persenter = (BrowsePersenterImp) presenter;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCloth(List<ClothInfo> clothInfos) {
        this.list = clothInfos;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new GoodsAdapter(getContext(), list, bundle);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoading && !recyclerView.canScrollVertically(1)) {
                    persenter.loadMoreData();
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        progressBar = view.findViewById(R.id.progressbar);
        bundle = getArguments();
        initData();
    }

    private void initData() {
        if (bundle != null) {
            persenter.getClothByKind(bundle.getString("kind"), "0");
            Log.d(TAG, "initData: " + count);
            count++;
        }

    }
}
