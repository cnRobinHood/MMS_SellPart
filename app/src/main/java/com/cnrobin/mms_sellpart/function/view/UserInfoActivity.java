package com.cnrobin.mms_sellpart.function.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.login.login_contect.LoginConnect;
import com.cnrobin.mms_sellpart.login.model.User;
import com.cnrobin.mms_sellpart.login.presenter.LoginPresenter;

public class UserInfoActivity extends AppCompatActivity implements LoginConnect.LoginView {
    private ActionBar actionBar;
    private TextView mTextViewSaveUserInfo;
    private EditText mEditTextUserName;
    private EditText mEditTextPhoneNum;
    private EditText mEditTextFocus;
    private User mUser;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        mLoginPresenter = new LoginPresenter(this);
        Bundle bundle = getIntent().getBundleExtra("userBundle");
        mUser = bundle.getParcelable("user");
        mEditTextUserName = findViewById(R.id.et_username);
        mEditTextPhoneNum = findViewById(R.id.et_phone);
        mEditTextFocus = findViewById(R.id.et_focus);
        mTextViewSaveUserInfo = findViewById(R.id.tv_save);
        mEditTextUserName.setText(mUser.getUsername());
        mEditTextPhoneNum.setText(mUser.getPhoneNum());
        mEditTextFocus.setText(mUser.getFoucs());
        mTextViewSaveUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setUsername(mEditTextUserName.getText().toString());
                mUser.setPhoneNum(mEditTextPhoneNum.getText().toString());
                mUser.setFoucs(mEditTextFocus.getText().toString());
                mLoginPresenter.setUserCount(mUser);
            }
        });

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
    public void setPresenter(Object presenter) {

    }

    @Override
    public void checkPassWd(User user) {

    }
}
