package com.cnrobin.mms_sellpart.function.function_contect;

import com.cnrobin.mms_sellpart.BasePresenter;
import com.cnrobin.mms_sellpart.BaseView;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;

import java.util.List;

/**
 * Created by cnrobin on 17-10-21.
 * Just Enjoy It!!!
 */

public interface FunctionContect {
    interface BrowsePresenter extends BasePresenter {
        void getClothByKind(String kind, String type);

        void setClothInfo(ClothInfo clothInfo, String filePath);

    }

    interface ResultPresenter extends BasePresenter {
        void setInfo(int count);

        void setCount(String id, String count);
    }

    interface ResultView extends BaseView {
        void setInfo(int count);
    }

    interface BrowseView extends BaseView {
        void showProgressBar();

        void stopProgressBar();

        void loadMoreData(List<ClothInfo> list);

        void showCloth(List<ClothInfo> clothInfos);
    }

    interface SearchResultView extends BaseView {
        void showResult(List<ClothInfo> clothInfos);
    }

    interface ModelContectPersenter {
        List<ClothInfo> success();

        void fail();
    }
}
