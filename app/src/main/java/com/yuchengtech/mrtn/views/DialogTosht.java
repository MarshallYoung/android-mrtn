package com.yuchengtech.mrtn.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;

/**
 * 提示语对话框
 *
 * @author lovesych1314
 */
public class DialogTosht extends Dialog implements
        android.view.View.OnClickListener {

    private TextView txt_title;
    private LinearLayout lay_canle;
    private TextView txt_canle;
    private TextView txt_content;
//	private Activity context;

    private onDissToshtData onDissDatas = null;
    private String title = "";
    private String content = "";

    public DialogTosht(Activity context, String title, String content) {
        super(context, R.style.Mydialog);
//		this.context = context;
        this.title = title;
        this.content = content;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mrtn_atv_dialog_tosht);
        setCancelable(false);
        intiView();
    }

    private void intiView() {

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText(title);

        txt_content = (TextView) findViewById(R.id.txt_content);
        txt_content.setText(content);

        lay_canle = (LinearLayout) findViewById(R.id.lay_canle);
        lay_canle.setOnClickListener(this);
        txt_canle = (TextView) findViewById(R.id.txt_canle);
        txt_canle.setOnClickListener(this);

    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_canle:
            case R.id.txt_canle:
                dismiss();
                if (onDissDatas != null) {
                    onDissDatas.setData("");
                }
                break;

        }

    }

    public onDissToshtData getOnDissDatas() {
        return onDissDatas;
    }

    public void setOnDissDatas(onDissToshtData onDissDatas) {
        this.onDissDatas = onDissDatas;
    }

    public interface onDissToshtData {
        public void setData(String name);
    }

}
