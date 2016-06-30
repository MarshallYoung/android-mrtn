package com.yuchengtech.mrtn.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表对话框
 *
 * @author lovesych1314
 */
public class DialogList extends Dialog implements
        android.view.View.OnClickListener, OnItemClickListener {

    private TextView txt_title;
    private ListView lv_content;
    private LinearLayout lay_canle;
    private TextView txt_canle;
    private Activity context;

    private onDissData onDissDatas = null;
    private List<String> lvNames = new ArrayList<String>();
    private List<String> lvIds = new ArrayList<String>();
    private String title = "";
    private LvApdate apdate = null;

    public DialogList(Activity context, String title, List<String> names,
                      List<String> ids) {
        super(context, R.style.Mydialog);
        this.context = context;
        this.title = title;
        this.lvNames = names;
        this.lvIds = ids;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mrtn_atv_dialog_pay_type);
        setCancelable(false);
        intiView();
    }

    private void intiView() {

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText(title);

        apdate = new LvApdate();
        lv_content = (ListView) findViewById(R.id.lv_content);
        lv_content.setAdapter(apdate);
        lv_content.setOnItemClickListener(this);

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
                if (onDissDatas != null) {
                    onDissDatas.canle();
                }
                dismiss();
                break;

        }

    }

    public onDissData getOnDissDatas() {
        return onDissDatas;
    }

    public void setOnDissDatas(onDissData onDissDatas) {
        this.onDissDatas = onDissDatas;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        try {
            String name = lvNames.get(arg2);
            String id = lvIds.get(arg2);

            if (onDissDatas != null) {
                onDissDatas.setData(name, id);

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        dismiss();
    }

    public interface onDissData {
        public void setData(String name, String id);

        public void canle();

    }

    private class LvApdate extends BaseAdapter {


        @Override
        public int getCount() {

            return lvNames.size();
        }

        @Override
        public Object getItem(int arg0) {

            return arg0;
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            final HolderView holderView;
            final String name = lvNames.get(position);
            if (convertView == null) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.mrtn_atv_dialog_lv_tiem, null);

                holderView.txt_name = (TextView) convertView
                        .findViewById(R.id.txt_name);

                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }

            holderView.txt_name.setText(name);

            return convertView;
        }

    }

    private class HolderView {

        public TextView txt_name;

    }
}
