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
import com.yuchengtech.mrtn.base.manager.DebugManager;
import com.yuchengtech.mrtn.base.manager.NetworkManager;
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

    @Bind(R.id.btn_back)
    Button btn_back;// 后退
    @Bind(R.id.edt_username)
    EditText edt_username;// 用户名
    @Bind(R.id.edt_password)
    EditText edt_password;// 密码
    private NetworkManager networkManager;
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
        btn_back.setVisibility(View.GONE);
        // 回显用户名密码
        String username = preference.getString(Preferences.USERNAME, "");
        edt_username.setText(username);
        String password = preference.getString(Preferences.PASSWORD, "");
        edt_password.setText(password);
    }

    /**
     * 登录
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    void login(View view) {
        if (DebugManager.Login.LOGIN_MODE_PREVIEW) {// 调试模式
            enterMainActivity();
        } else {// 正常模式
            String username = edt_username.getText().toString().trim();
            String password = edt_password.getText().toString().trim();
            if (username.isEmpty()) {
                Toast.makeText(this, Notes.EMPTYUSERNAME, Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, Notes.EMPTYPASSWORD, Toast.LENGTH_SHORT).show();
                return;
            }
            // 保存用户名密码
            preference.edit().putString(Preferences.USERNAME, username).apply();
            preference.edit().putString(Preferences.PASSWORD, password).apply();
            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText(Notes.LOGINING);
            pDialog.setCancelable(false);
            pDialog.show();
            if (networkManager == null) {// 懒加载
                networkManager = new NetworkManager(this);
            }
            LoginRequest userInfo = new LoginRequest(username, password);
            networkManager.Login(userInfo, new NetworkListener() {
                @Override
                public void onSuccess(String response) {
                    LogUtil.e("==登录成功==", response);
                    pDialog.cancel();
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optBoolean("success")) {
                            Gson gson = new Gson();
                            ShiroUser user = gson.fromJson(
                                    obj.getString("data"), ShiroUser.class);
                            UserManager.getInstance().setUserInfo(user);
                            enterMainActivity();
                        } else {
                            String msg = obj.optString("msg");
                            Toast.makeText(LoginActivity.this, msg,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this,
                                Notes.ERROR_ANALYSIS, Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFail(VolleyError error) {
                    pDialog.cancel();
                    LogUtil.e("==登录报错==", error.toString());
                    Toast.makeText(LoginActivity.this, Notes.ERROR_404,
                            Toast.LENGTH_SHORT).show();
                }
            }, new CookiesListener() {
                @Override
                public void onSId(String sid) {
                    LogUtil.e("==cookies中的sid==", sid);
                    MrtnApplication.sid = sid;
                }
            });
        }
    }

    /**
     * 进入主界面
     */
    void enterMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        finish();
    }

    interface Preferences {
        String USERNAME = "username";// 帐号
        String PASSWORD = "password";// 密码
    }

    interface Notes {
        String LOGINING = " 登录中...";
        String EMPTYUSERNAME = "请输入用户名";
        String EMPTYPASSWORD = "请输入密码";
        String ERROR_ANALYSIS = "解析错误";
        String ERROR_404 = "服务器访问拒绝";
    }
}