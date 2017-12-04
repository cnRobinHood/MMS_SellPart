package com.cnrobin.mms_sellpart.function.presenter;

import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.model.ModelInterfaceImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnrobin on 17-10-21.
 * Just Enjoy It!!!
 */

public class BrowsePersenterImp implements FunctionContect.BrowsePresenter {
    private ArrayList<ClothInfo> clothInfos = new ArrayList<>();
    private FunctionContect.BrowseView browseView;
    private FunctionContect.ModelContectPersenter contectPersenter;
    private ModelInterfaceImp model;
    private Integer pages = 1;
    private String kind;
    private FunctionContect.SearchResultView resultView;

    public BrowsePersenterImp(FunctionContect.BrowseView browseView) {
        this.browseView = browseView;
        this.model = new ModelInterfaceImp(this);
    }

    public BrowsePersenterImp(FunctionContect.SearchResultView resultView) {
        this.resultView = resultView;
        this.model = new ModelInterfaceImp(this);
    }

    public void setSearchResult(List<ClothInfo> clothInfos) {
        resultView.showResult(clothInfos);
    }

    @Override
    public void start() {

    }

    @Override
    public void getClothByKind(String kind, String type) {
        this.kind = kind;
        if (type.equals("0")) {
            browseView.showProgressBar();

        }
        model.getclothInfoByKind(kind, pages.toString(), type);
    }

    @Override
    public void setClothInfo(ClothInfo clothInfo, String filePath) {
        model.addclothInfo(clothInfo);
        model.addImg(filePath);
    }

    public void setContectPersenter(FunctionContect.ModelContectPersenter contectPersenter) {
        this.contectPersenter = contectPersenter;
        if (pages == 1) {
            showCloth();
        } else {
            clothInfos.addAll(contectPersenter.success());
            browseView.loadMoreData(clothInfos);
        }

    }

    public void loadMoreData() {
        pages++;
        model.getclothInfoByKind(kind, pages.toString(), "0");
    }

    public void showCloth() {
        clothInfos.addAll(contectPersenter.success());
        browseView.showCloth(clothInfos);
        browseView.stopProgressBar();
    }

    public void setCount(String id, String count) {
        model.setCount(id, count);
    }
}
