package com.yuchengtech.mrtn.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.views.DialogAlert;
import com.yuchengtech.mrtn.views.DialogAlert.onDialogAlertData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更多
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月20日 下午5:59:13
 */
public class MoreFragment extends Fragment {

    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.txt_title)
    TextView txt_title;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_more, container,
                    false);
            ButterKnife.bind(this, fragmentView);
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        btn_back.setVisibility(View.GONE);
        txt_title.setText("更多");
    }

    @OnClick({R.id.ll_user_info, R.id.ll_about_us, R.id.ll_check_update,
            R.id.btn_exit})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_user_info:// 用户信息
                Intent userInfoIntent = new Intent(getActivity(),
                        UserInfoActivity.class);
                startActivity(userInfoIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.ll_about_us:// 关于我们
                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.ll_check_update:// 检查更新
                Toast.makeText(getActivity(), "当前为最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_exit:
                DialogAlert dialogAlert = new DialogAlert(getActivity(), "温馨提示",
                        "您确定要退出软件吗？");
                dialogAlert.setOnDissDatas(new onDialogAlertData() {
                    @Override
                    public void setData(String name) {
                        getActivity().finish();
                    }
                });
                dialogAlert.show();
                break;
        }
    }
}