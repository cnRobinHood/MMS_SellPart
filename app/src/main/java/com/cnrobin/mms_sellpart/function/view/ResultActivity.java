package com.cnrobin.mms_sellpart.function.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.function_contect.FunctionContect;
import com.cnrobin.mms_sellpart.function.presenter.ResultPresenterImp;

public class ResultActivity extends AppCompatActivity implements FunctionContect.ResultView, View.OnClickListener {
    private TextView tvName;
    private TextView tvDesc;
    private Button btCut;
    private Button btIns;
    private Button btSubmit;
    private Button btXL;
    private Button btXXL;
    private Button btXXXL;
    private Button btXXXXL;
    private EditText etCount;
    private ResultPresenterImp presenter;
    private ActionBar actionBar;
    private String id;
    private String size;
    private static final String TAG = "ResultActivity";

    public ResultActivity() {
        super();
    }

    @Override
    public void setPresenter(Object presenter) {
        this.presenter = (ResultPresenterImp) presenter;
    }

    @Override
    public void setInfo(int count) {
        etCount.setText(new Integer(count).toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        btCut = (Button) findViewById(R.id.bt_cut);
        btIns = (Button) findViewById(R.id.bt_ins);
        btSubmit = (Button) findViewById(R.id.bt_submit);
        btXL = findViewById(R.id.bt_xl);
        btXXL = findViewById(R.id.bt_xxl);
        btXXXL = findViewById(R.id.bt_xxxl);
        btXXXXL = findViewById(R.id.bt_xxxxl);
        btXL.setOnClickListener(this);
        btXXL.setOnClickListener(this);
        btXXXL.setOnClickListener(this);
        btXXXXL.setOnClickListener(this);

        etCount = (EditText) findViewById(R.id.et_left);
        setPresenter(new ResultPresenterImp(this));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("image");
        String image = bundle.getString("image");
        id = bundle.getString("id");
        Log.d(TAG, "onCreate: " + id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        tvName.setText(id);
        tvDesc.setText("这是个好衣服");
        presenter.getCount(id, "XL");
        ImageView iv_cloth = (ImageView) findViewById(R.id.cloth_img);
        Glide.with(this).load(image).into(iv_cloth);
        btSubmit.setOnClickListener(this);
        btIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(etCount.getText().toString());
                etCount.setText("" + (++temp));
            }
        });
        btCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(etCount.getText().toString());
                etCount.setText("" + (--temp));
            }
        });
        btXL.setEnabled(false);
        btXXL.setEnabled(true);
        btXXXL.setEnabled(true);
        btXXXXL.setEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                presenter.setCount(id, etCount.getText().toString(), size);
                Log.d(TAG, "onClick: " + etCount.getText().toString());
                Toast.makeText(ResultActivity.this, "更新成功了哟", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_xl:
                size = "XL";
                presenter.getCount(id, size);
                btXL.setEnabled(false);
                btXXL.setEnabled(true);
                btXXXL.setEnabled(true);
                btXXXXL.setEnabled(true);
                break;
            case R.id.bt_xxl:
                size = "XXL";
                presenter.getCount(id, size);
                btXL.setEnabled(true);
                btXXL.setEnabled(false);
                btXXXL.setEnabled(true);
                btXXXXL.setEnabled(true);
                break;
            case R.id.bt_xxxl:
                size = "XXXL";
                presenter.getCount(id, size);

                btXL.setEnabled(true);
                btXXL.setEnabled(true);
                btXXXL.setEnabled(false);
                btXXXXL.setEnabled(true);
                break;
            case R.id.bt_xxxxl:
                size = "XXXXL";
                presenter.getCount(id, size);
                btXL.setEnabled(true);
                btXXL.setEnabled(true);
                btXXXL.setEnabled(true);
                btXXXXL.setEnabled(false);
                break;
        }
    }
}
