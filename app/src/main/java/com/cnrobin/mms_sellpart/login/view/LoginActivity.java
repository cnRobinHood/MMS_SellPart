package com.cnrobin.mms_sellpart.login.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.view.FunctionActivity;
import com.cnrobin.mms_sellpart.login.loginUtils.BalloonRelativeLayout;
import com.cnrobin.mms_sellpart.login.login_contect.LoginConnect;
import com.cnrobin.mms_sellpart.login.model.User;
import com.cnrobin.mms_sellpart.login.presenter.LoginPresenter;
import com.cnrobin.mms_sellpart.util.ActivityCollector;

public class LoginActivity extends AppCompatActivity implements LoginConnect.LoginView, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, View.OnClickListener {
    private static final int TIME = 200;
    Handler mHandler = new Handler();
    private LoginPresenter mPresenter;
    private VideoView mVideoView;
    private EditText username;
    private EditText passWd;
    private ImageButton loginButton;
    private CheckBox remeberPasswd;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private BalloonRelativeLayout mBalloonRelativeLayout;
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                mHandler.postDelayed(this, TIME);
                mBalloonRelativeLayout.addBalloon();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (LoginPresenter) presenter;
    }


    @Override
    public void checkPassWd(User user) {
        String uname = username.getText().toString();
        String pass = passWd.getText().toString();
        if ("".equals(uname) || pass != null || "".equals(pass)) {
            if (uname.equals(user.getUsername()) && pass.equals(user.getPsword())) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                editor = sp.edit();
                if (remeberPasswd.isChecked()) {
                    editor.putString("user", uname);
                    editor.putString("pass", pass);
                    editor.commit();
                } else {
                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.commit();
                }
                Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onClick(View view) {
        mPresenter.getPassCheck(username.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mBalloonRelativeLayout = (BalloonRelativeLayout) findViewById(R.id.balloonRelativeLayout);
        username = (EditText) findViewById(R.id.username_log);
        passWd = (EditText) findViewById(R.id.passwd_log);
        loginButton = (ImageButton) findViewById(R.id.login_button);
        remeberPasswd = (CheckBox) findViewById(R.id.remeber_passwd);
        mPresenter = new LoginPresenter(this);
        loginButton.setOnClickListener(this);
        initVideoView();
        sp = getSharedPreferences("passwd", MODE_PRIVATE);
        if (!"".equals(sp.getString("user", ""))) {
            username.setText(sp.getString("user", ""));
            passWd.setText(sp.getString("pass", ""));
            remeberPasswd.setChecked(true);

        }
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);


    }

    private void checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    private void initVideoView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mqr));
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnCompletionListener(this);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //开始播放
        mVideoView.start();
        mHandler.postDelayed(runnable, TIME);
    }

    //播放结束
    @Override
    public void onCompletion(MediaPlayer mp) {
        //开始播放
        mVideoView.start();
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.removeAll();
    }
}
