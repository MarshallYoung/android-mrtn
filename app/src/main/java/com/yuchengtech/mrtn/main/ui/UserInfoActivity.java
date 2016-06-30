package com.yuchengtech.mrtn.main.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.manager.UserManager;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.login.bean.ShiroUser;

/**
 * 个人基本资料
 *
 * @author lovesych1314
 */
public class UserInfoActivity extends BaseActivity implements OnClickListener {

    private Button btn_back;
    private TextView txt_title;
    private ShiroUser info = null;
    private TextView txt_emp_name;
    private TextView txt_empEmail;
    private TextView txt_empTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        intiView();
    }

    private void intiView() {
        info = UserManager.getInstance().getUserInfo();
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("基本资料");
        txt_emp_name = (TextView) findViewById(R.id.txt_emp_name);
        txt_emp_name.setText(info.userName);
        txt_empEmail = (TextView) findViewById(R.id.txt_empEmail);
        txt_empEmail.setText("");
        txt_empTel = (TextView) findViewById(R.id.txt_empTel);
        txt_empTel.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}