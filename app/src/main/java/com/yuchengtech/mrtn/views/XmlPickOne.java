package com.yuchengtech.mrtn.views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yuchengtech.mrtn.utils.xml.IXmlControl;

public class XmlPickOne extends LinearLayout implements IXmlControl {
    private TextView label;
    private RadioGroup group;
    private LinearLayout layout;

    public XmlPickOne(Context context, String labelText, String options,
                      String tagstr, String defString) {
        super(context);
        layout = new LinearLayout(context);
        label = new TextView(context);
        label.setGravity(Gravity.LEFT);
        label.setTextSize(14);
        label.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        label.setText(Html.fromHtml(labelText + "<font color='red'>*</font>"));
        group = new RadioGroup(context);
        String[] opts = options.split("\\|");
        String[] tags = new String[0];
        if (tagstr != null && !tagstr.isEmpty()) {
            tags = tagstr.split("\\|");
        }
        if (opts.length > 2) {
            group.setOrientation(VERTICAL);
        } else {
            group.setOrientation(HORIZONTAL);
        }
        int i = 0;

        for (String opt : opts) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(opt);
            radioButton.setTextSize(14);
            if (opts.length == tags.length) {
                radioButton.setTag(tags[i]);
            } else {
                radioButton.setTag(opt);
            }
            group.addView(radioButton);
            if (opt.equals(defString)) {
                radioButton.setChecked(true);
            }
            i++;
        }
        layout.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        this.addView(label);
        layout.addView(group);
        this.addView(layout);

    }

    public XmlPickOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getValue() {
        int id = group.getCheckedRadioButtonId();
        if (id == -1) {
            return "";
        }
        RadioButton radio = (RadioButton) group.findViewById(id);
        return (String) radio.getTag();
    }

    @Override
    public void setValue(String v) {
        if (v == null || v.isEmpty()) {
            return;
        }
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton radio = (RadioButton) group.getChildAt(i);
            if (radio.getTag().toString().equals(v)) {
                radio.setChecked(true);
            }
        }

    }

    public String getText() {
        int id = group.getCheckedRadioButtonId();
        if (id == -1) {
            return "";
        }
        RadioButton radio = (RadioButton) group.findViewById(id);
        return (String) radio.getText();
    }

}