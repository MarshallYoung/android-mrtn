package com.yuchengtech.mrtn.order.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.views.ZoomImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoActivity extends BaseActivity {

    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.txt_title)
    TextView txt_title;
    @Bind(R.id.ziv)
    ZoomImageView imageView;
    private BitmapUtils bitmapUtils;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        txt_title.setText("图片预览");
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bitmapUtils = new BitmapUtils(this);
        Intent intent = getIntent();
        String img_path = intent.getStringExtra("img_path");
        bitmapUtils.display(imageView, img_path);
    }
}
