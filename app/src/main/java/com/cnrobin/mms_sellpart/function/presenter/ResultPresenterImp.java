package com.cnrobin.mms_sellpart.function.presenter;

import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.model.ResultModel;

/**
 * Created by cnrobin on 17-11-22.
 * Just Enjoy It!!!
 */

public class ResultPresenterImp implements FunctionContect.ResultPresenter {
    private FunctionContect.ResultView resultView;
    private ResultModel resultModel = new ResultModel(this);

    public ResultPresenterImp(FunctionContect.ResultView resultView) {
        this.resultView = resultView;
    }

    @Override
    public void start() {

    }

    public void getCount(String id) {
        resultModel.getCount(id);
    }

    @Override
    public void setInfo(int count) {
        resultView.setInfo(count);
    }

    @Override
    public void setCount(String id, String count) {
        resultModel.setCount(id, count);

    }
}
