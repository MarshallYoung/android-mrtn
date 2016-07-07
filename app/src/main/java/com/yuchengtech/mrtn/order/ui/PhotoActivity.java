package com.yuchengtech.mrtn.order.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.MrtnApplication;
import com.yuchengtech.mrtn.base.manager.NetworkManager;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.views.ZoomImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PhotoActivity extends BaseActivity {

    private static final String TAG = "PhotoActivity";

    @Bind(R.id.ziv)
    ZoomImageView imageView;
    SweetAlertDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setCancelable(false);
        dialog.setTitleText("载入图片...").show();
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("img_path");
        if (imagePath != null) {
            MrtnApplication.networkManager.loadImage(imagePath, new NetworkManager.NetworkListener() {
                @Override
                public void onSuccess(Object response) {
                    Bitmap bitmap = (Bitmap) response;
                    imageView.setImageBitmap(bitmap);
                    dismissDialog();
                }

                @Override
                public void onFail(VolleyError error) {
                    LogUtil.e(TAG, error.toString());
                    dismissDialog();
                }
            });
        }
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}
