package com.cnrobin.mms_sellpart.login.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cnrobin.mms_sellpart.R;
import com.cnrobin.mms_sellpart.login.model.User;

/**
 * Created by cnrobin on 17-12-11.
 * Just Enjoy It!!!
 */

public class RegisterFragment extends DialogFragment {
    private EditText username;
    private EditText passwd;
    private EditText focus;
    private EditText phoneNum;
    private Button registerButton;
    private Button resittingButton;

    public static RegisterFragment getInstance() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        focus = view.findViewById(R.id.register_focus);
        phoneNum = view.findViewById(R.id.register_phoneNum);
        username = view.findViewById(R.id.register_uname);
        passwd = view.findViewById(R.id.register_passwd);
        registerButton = view.findViewById(R.id.register_button);
        resittingButton = view.findViewById(R.id.resitting_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).setUserInfo();
            }
        });
    }

    public User getUser() {
        User user = new User();
        user.setFoucs(focus.getText().toString());
        user.setPhoneNum(phoneNum.getText().toString());
        user.setPsword(passwd.getText().toString());
        user.setUsername(username.getText().toString());
        return user;
    }
}
