package com.cnrobin.mms_sellpart.function.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.view.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsSearchFragment extends Fragment {
    private ImageButton mImageButton;

    public GoodsSearchFragment() {
        // Required empty public constructor
    }

    public static GoodsSearchFragment newInstance(Bundle bundle) {
        GoodsSearchFragment fragment = new GoodsSearchFragment();
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
        return inflater.inflate(R.layout.fragment_goods_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageButton = view.findViewById(R.id.ib_search);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

}
