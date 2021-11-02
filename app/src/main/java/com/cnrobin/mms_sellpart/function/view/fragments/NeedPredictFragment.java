package com.cnrobin.mms_sellpart.function.view.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.adapters.ResultAdapter;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.presenter.NeedPredicePresenterImpl;

import java.util.List;

public class NeedPredictFragment extends Fragment implements FunctionContect.NeedPredictView {
    private RecyclerView mRecyclerView;
    private NeedPredicePresenterImpl presenter;

    @Override
    public void setPresenter(Object presenter) {
        this.presenter = (NeedPredicePresenterImpl) presenter;
    }

    @Override
    public void showNeedPredict(List<ClothInfo> clothInfoList) {
        ResultAdapter adapter = new ResultAdapter(getActivity(), clothInfoList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

    }

    public NeedPredictFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NeedPredictFragment newInstance(Bundle bundle) {
        NeedPredictFragment fragment = new NeedPredictFragment();
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
        return inflater.inflate(R.layout.fragment_need_predict, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setPresenter(new NeedPredicePresenterImpl(this));
        presenter.getNeedPredict();
        mRecyclerView = view.findViewById(R.id.recycler_result);
    }
}
