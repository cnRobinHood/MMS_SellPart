package com.cnrobin.mms_sellpart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.cnrobin.mms_sellpart.login.view.LoginActivity;
import com.cnrobin.mms_sellpart.util.ActivityCollector;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("native-lib");
    }

    private ImageView imageView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ActivityCollector.addActivity(this);
        // Example of a call to a native method
        imageView = (ImageView) findViewById(R.id.img_doge);
        setAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }

    private void setAnimation() {
        RotateAnimation animation = new RotateAnimation
                (0, 360, RELATIVE_TO_SELF, (float) 0.50, RELATIVE_TO_SELF, (float) 0.50);
        animation.setDuration(1500);
        imageView.setAnimation(animation);
        animation.start();


    }

    @Override
    public void onBackPressed() {

    }

    public native String stringFromJNI();


}
