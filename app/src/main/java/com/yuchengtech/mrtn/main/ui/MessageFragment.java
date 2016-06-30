package com.yuchengtech.mrtn.main.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月20日 下午5:59:03
 */
public class MessageFragment extends Fragment {

    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.txt_title)
    TextView txt_title;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_messge,
                    container, false);
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
        intiView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void intiView() {
        btn_back.setVisibility(View.GONE);
        txt_title.setText("消息");
    }
}