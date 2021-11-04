package com.cnrobin.mms_sellpart.function.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.function.entity.SearchHistory;
import com.cnrobin.mms_sellpart.function.functionUtils.FlowLayout;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private EditText mEdittext;
    private TextView tv_cancel;
    private FlowLayout mFlowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_search);
        mEdittext = (EditText) findViewById(R.id.et_search);
        tv_cancel = (TextView) findViewById(R.id.et_cancel);
        mFlowlayout = (FlowLayout) findViewById(R.id.flowLayout);
        init();
        mEdittext.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) mEdittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(mEdittext, 0);
                           }

                       },
                500);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
        mEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("keyWord", mEdittext.getText().toString());
                    SearchHistory history = new SearchHistory();
                    history.setKeyWord(mEdittext.getText().toString());
                    history.save();
                    Log.d(TAG, "onEditorAction: ");
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("keyWord", bundle);
                    startActivity(intent);
                    return false;
                }
                return true;
            }
        });
    }

    private void init() {
        List<SearchHistory> histories = DataSupport.findAll(SearchHistory.class);
        String[] strings = new String[histories.size()];
        for (int i = 0; i < histories.size(); i++) {
            strings[i] = histories.get(i).getKeyWord();
        }

        Random random = new Random();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < strings.length; i++) {
            final TextView textView = new TextView(this);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setText(strings[i]);
            textView.setTextSize(20);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    mEdittext.setText(textView.getText());
                    bundle.putString("keyWord", mEdittext.getText().toString());
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("keyWord", bundle);
                    startActivity(intent);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                GradientDrawable normalDrawable = new GradientDrawable();
                normalDrawable.setShape(GradientDrawable.RECTANGLE);
                int a = 255;
                int r = 50 + random.nextInt(150);
                int g = 50 + random.nextInt(150);
                int b = 50 + random.nextInt(150);
                normalDrawable.setColor(Color.argb(a, r, g, b));
                GradientDrawable pressedDrawable = new GradientDrawable();
                pressedDrawable.setShape(GradientDrawable.RECTANGLE);
                pressedDrawable.setColor(Color.GRAY);
                StateListDrawable drawable = new StateListDrawable();
                drawable.addState(new int[]{android.R.attr.onClick}, pressedDrawable);
                drawable.addState(new int[]{}, normalDrawable);
                textView.setBackground(drawable);
            }

            mFlowlayout.addView(textView, layoutParams);
        }
//        LinearLayout linearLayout = new LinearLayout(this);
//        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp1.setMargins(DensityUtil.dip2px(this, 5), 0, 0, DensityUtil.dip2px(this, 70));
//        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        TextView textView = new TextView(this);
//        textView.setText("");
//        textView.setBackgroundResource(R.drawable.et_search);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER_VERTICAL;
//        linearLayout.addView(textView, params);
//        layout.addView(linearLayout, lp1);
    }

}
