package com.cnrobin.mms_sellpart.function.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.adapters.ResultAdapter;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.presenter.BrowsePersenterImp;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements FunctionContect.SearchResultView {
    private RecyclerView mRecyclerView;
    private Bundle keyword;
    private BrowsePersenterImp persenter;

    @Override
    public void setPresenter(Object presenter) {
        this.persenter = (BrowsePersenterImp) presenter;
    }

    @Override
    public void showResult(List<ClothInfo> clothInfos) {
        ResultAdapter adapter = new ResultAdapter(this, clothInfos);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setPresenter(new BrowsePersenterImp(this));
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_result);
        Intent intent = getIntent();
        keyword = intent.getBundleExtra("keyWord");
        persenter.getClothByKind(keyword.getString("keyWord"), "1");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
