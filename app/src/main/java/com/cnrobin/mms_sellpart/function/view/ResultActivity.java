package com.cnrobin.mms_sellpart.function.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ResultActivity extends AppCompatActivity implements FunctionContect.ResultView {
    private TextView tvName;
    private TextView tvDesc;
    private Button btCut;
    private Button btIns;
    private Button btSubmit;
    private EditText etCount;
    private ResultPresenterImp presenter;

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
        etCount = (EditText) findViewById(R.id.et_left);
        setPresenter(new ResultPresenterImp(this));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("image");
        String image = bundle.getString("image");
        final String id = bundle.getString("id");
        tvName.setText(id);
        tvDesc.setText("这是个好衣服");
        presenter.getCount(id);
        ImageView iv_cloth = (ImageView) findViewById(R.id.cloth_img);
        Glide.with(this).load(image).into(iv_cloth);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setCount(id, etCount.getText().toString());
                Toast.makeText(ResultActivity.this, "更新成功了哟", Toast.LENGTH_SHORT).show();
            }
        });
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
    }

}
