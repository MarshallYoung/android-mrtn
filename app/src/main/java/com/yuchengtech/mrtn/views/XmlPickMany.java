package com.yuchengtech.mrtn.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengtech.mrtn.utils.xml.IXmlControl;

import java.util.Arrays;
import java.util.List;

public class XmlPickMany extends LinearLayout implements IXmlControl {
    String tag = XmlPickMany.class.getName();
    TextView label;
    EditText text;
    String[] opts;
    String[] values;
    String[] tags;
    boolean[] init;
    private AlertDialog mDialog;

    public XmlPickMany(Context context, String labelText, String options,
                       String tagstr, String valuesstr) {
        super(context);
        label = new TextView(context);
        label.setText(Html.fromHtml(labelText + "<font color='red'>*</font>"));
        label.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        label.setGravity(Gravity.LEFT);
        label.setTextSize(14);
        text = new EditText(context);
        opts = options.split("\\|");
        values = valuesstr.split("\\,");
        init = new boolean[opts.length];
        tags = new String[0];
        String temp = "";
        if (tagstr != null && !tagstr.isEmpty()) {
            tags = tagstr.replace(" ", "").split("\\|");
            initArrayBool(tags, values);
            text.setTag(valuesstr);
            for (int i = 0; i < init.length; i++) {
                if (init[i]) {
                    if (temp.equals("")) {
                        temp = opts[i];
                    } else {
                        temp += "," + opts[i];
                    }
                }
            }
            text.setText(temp);
        } else {
            initArrayBool(opts, values);
            text.setTag(valuesstr);
            text.setText(valuesstr);
        }
        text.setTextSize(14);
        text.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        text.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                OpenDialog(v);
            }

        });
        this.addView(label);
        this.addView(text);
    }

    private void initArrayBool(String[] strs, String[] v) {
        List<String> vList = Arrays.asList(v);
        for (int i = 0; i < strs.length; i++) {
            if (vList.contains(strs[i])) {
                init[i] = true;
            } else {
                init[i] = false;
            }
        }

    }

    private void OpenDialog(View v) {
        mDialog = new AlertDialog.Builder(v.getContext())
                .setTitle("选择项")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMultiChoiceItems(opts, init,
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {

                            }
                        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String temp = "";
                        String temp_v = "";
                        for (int i = 0; i < init.length; i++) {
                            if (init[i]) {
                                if (temp.equals("")) {
                                    temp = opts[i];
                                    if (tags != null && tags.length > 0) {
                                        temp_v = tags[i];
                                    }
                                } else {
                                    temp += "," + opts[i];
                                    if (tags != null && tags.length > 0) {
                                        temp_v += "," + tags[i];
                                    }
                                }
                            }
                        }
                        text.setText(temp);
                        text.setTag(temp_v);
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).create();
        mDialog.show();
    }

    public String getValue() {
        return (String) text.getTag().toString();
    }

    @Override
    public void setValue(String v) {
        // TODO Auto-generated method stub
        values = v.replace(" ", "").split("\\,");
        initArrayBool(tags, values);
        String temp = "";
        for (int i = 0; i < init.length; i++) {
            if (init[i]) {
                if (temp.equals("")) {
                    temp = opts[i];
                } else {
                    temp += "," + opts[i];
                }
            }
        }
        text.setText(temp);
        text.setTag(v);
    }

    public String getText() {
        return (String) text.getText().toString();
    }
}
