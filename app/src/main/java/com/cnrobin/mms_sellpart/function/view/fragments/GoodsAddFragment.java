package com.cnrobin.mms_sellpart.function.view.fragments;


import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.entity.ClothInfo;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.presenter.BrowsePersenterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsAddFragment extends Fragment implements FunctionContect.BrowseView {
    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "GoodsAddFragment";
    private String imagePath;
    private String kind = "风衣";
    private String rating = "2.5";
    private ImageButton mImageButton;
    private ImageView goodsImg;
    private RatingBar mRatingBar;
    private EditText goodName;
    private EditText goodCount;
    private Spinner kindSpinner;
    private BrowsePersenterImp persenter;
    private ClothInfo mClothInfo = new ClothInfo();

    public GoodsAddFragment() {

    }

    public static GoodsAddFragment newInstance(Bundle bundle) {
        GoodsAddFragment fragment = new GoodsAddFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mImageButton = view.findViewById(R.id.ib_upload);
        mRatingBar = view.findViewById(R.id.rb_good);
        goodName = view.findViewById(R.id.et_name);
        goodCount = view.findViewById(R.id.et_count);
        kindSpinner = view.findViewById(R.id.sp_kind);
        setPresenter(new BrowsePersenterImp(this));
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = goodName.getText().toString();
                String dsrc = "这是个好好地东西";
                String count = goodCount.getText().toString();
                if (imagePath != null && !"".equals(imagePath)) {
                    mClothInfo.setImages("http://123.207.8.147:8080/GoodsSystemServe/clothimages/" + imagePath.substring(imagePath.lastIndexOf("/") + 1));
                    mClothInfo.setKinds(kind);
                    mClothInfo.setID(name);
                    mClothInfo.setLining(dsrc);
                    mClothInfo.setSeason("秋季");
                    mClothInfo.setStars(rating);
                    mClothInfo.setSize("XLL");
                    persenter.setClothInfo(mClothInfo, imagePath);
                    persenter.setCount(name, count);
                    Log.d(TAG, "onClick: ");
                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                    goodCount.setText("");
                    goodName.setText("");

                }

            }
        });
        goodsImg = view.findViewById(R.id.iv_good);
        goodsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        initSpinner(kindSpinner);
        initRatingBar(mRatingBar);
    }

    private void initRatingBar(RatingBar ratingBar) {
        ratingBar.setIsIndicator(false);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = Float.toString(v);

            }
        });


    }

    private void initSpinner(Spinner spinner) {
        final List<String> kinds = new ArrayList<>();
        kinds.add("风衣");
        kinds.add("半身裙");
        kinds.add("长裤");
        kinds.add("衬衣");
        kinds.add("连衣裙");
        kinds.add("西装");
        kinds.add("针织衫");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, kinds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kind = kinds.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods_add, container, false);
    }


    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"), FILE_SELECT_CODE);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getContext(), "亲，木有文件管理器啊-_-!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            handleImageOnKitKat(data);
        } else {
            Toast.makeText(getContext(), "亲，你取消了文件选择-_-!!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(getContext(), uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    //Log.d(TAG, uri.toString());
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    //Log.d(TAG, uri.toString());
                    Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //Log.d(TAG, "content: " + uri.toString());
                imagePath = getImagePath(uri, null);
            }
        }


    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void setPresenter(Object presenter) {
        this.persenter = (BrowsePersenterImp) presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void loadMoreData(List<ClothInfo> list) {

    }

    @Override
    public void showCloth(List<ClothInfo> clothInfos) {

    }
}
