package com.cnrobin.mms_sellpart.function.presenter;

import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.model.NeedPredictModel;

import java.util.List;

/**
 * Created by cnrobin on 17-12-19.
 * Just Enjoy It!!!
 */

public class NeedPredicePresenterImpl implements FunctionContect.NeedPredictPresenter {
    private NeedPredictModel needPredictModel;
    private FunctionContect.NeedPredictView needPredictView;

    public NeedPredicePresenterImpl(FunctionContect.NeedPredictView needPredictView) {
        this.needPredictView = needPredictView;
        needPredictModel = new NeedPredictModel(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getNeedPredict() {
        needPredictModel.getNeedPredict();
    }

    @Override
    public void showNeedPredict(List<ClothInfo> clothInfoList) {
        needPredictView.showNeedPredict(clothInfoList);
    }
}
