package com.yuchengtech.mrtn.main.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 带tabbar的主界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月20日 下午5:57:34
 */
public class MainActivity extends FragmentActivity {

    @Bind(android.R.id.tabhost)
    FragmentTabHost tabHost;
    private LayoutInflater inflater;

    private Class<?> classes[] = {HomepageFragment.class, MessageFragment.class, MoreFragment.class};
    private int selectors[] = {R.drawable.selector_tab_index, R.drawable.selector_tab_message, R.drawable.selector_tab_update};
    private String titles[] = {"主页", "消息", "更新"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initView();
        tabHost.setCurrentTab(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        tabHost.setup(this, getSupportFragmentManager(), R.id.framecontent);
        int count = classes.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = tabHost.newTabSpec(titles[i]).setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, classes[i], null);
        }
    }

    /**
     * 给tabitem设置图标和标题
     *
     * @param index 坐标
     * @return 带图标和文字的tabitem
     */
    @SuppressLint("InflateParams")
    private View getTabItemView(int index) {
        View root = inflater.inflate(R.layout.item_tab, null);
        ImageView imageView = (ImageView) root.findViewById(R.id.tab_image);
        imageView.setImageResource(selectors[index]);
        TextView textView = (TextView) root.findViewById(R.id.tab_text);
        textView.setText(titles[index]);
        return root;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}