package com.yuchengtech.mrtn.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.MrtnApplication;
import com.yuchengtech.mrtn.base.manager.NetworkManager.CookiesListener;
import com.yuchengtech.mrtn.base.manager.NetworkManager.NetworkListener;
import com.yuchengtech.mrtn.base.manager.UserManager;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.login.bean.LoginRequest;
import com.yuchengtech.mrtn.login.bean.ShiroUser;
import com.yuchengtech.mrtn.main.ui.MainActivity;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private static final String USERNAME = "username";
    private static final String PWD = "p16";

    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.edt_username)
    EditText edt_username;
    @Bind(R.id.edt_p16)
    EditText edt_p16;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);// 注解绑定框架ButterKnife
        preference = PreferenceManager.getDefaultSharedPreferences(this);
        intiView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void intiView() {
        btn_back.setVisibility(View.INVISIBLE);
        edt_p16.setInputType(0x81);
        // 回显用户名
        String username = preference.getString(USERNAME, "");
        edt_username.setText(username);
        String password = preference.getString(PWD, "");
        edt_p16.setText(password);
    }

    /**
     * 登录
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    void login(View view) {
        String username = edt_username.getText().toString().trim();
        String pwd = edt_p16.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        // 保存用户名密码
        preference.edit().putString(USERNAME, username).apply();
        preference.edit().putString(PWD, pwd).apply();
        final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setCancelable(false);
        dialog.setTitleText("登录中...").show();
        LoginRequest userInfo = new LoginRequest(username, pwd);
        MrtnApplication.networkManager.Login(userInfo, new NetworkListener() {
            @Override
            public void onSuccess(String response) {
                LogUtil.e(TAG, "response:  " + response);
                dialog.cancel();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.optBoolean("success")) {
                        Gson gson = new Gson();
                        ShiroUser user = gson.fromJson(obj.getString("data"), ShiroUser.class);
                        UserManager.getInstance().setUserInfo(user);
                        enterMainActivity();
                    } else {
                        String msg = obj.optString("msg");
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(VolleyError error) {
                dialog.cancel();
                LogUtil.e(TAG, "error:  " + error.toString());
                Toast.makeText(LoginActivity.this, "服务器访问拒绝", Toast.LENGTH_SHORT).show();
            }
        }, new CookiesListener() {
            @Override
            public void onSId(String sid) {
                LogUtil.e(TAG, "sid:  " + sid);
                MrtnApplication.sid = sid;
            }
        });
    }

    /**
     * 进入主界面
     */
    void enterMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityWithAnim(loginIntent);
        finish();
    }
}