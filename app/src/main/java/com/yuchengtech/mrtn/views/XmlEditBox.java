package com.yuchengtech.mrtn.views;

import android.content.Context;
import android.text.Html;
import android.text.method.DateTimeKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.utils.xml.IXmlControl;

public class XmlEditBox extends LinearLayout implements IXmlControl {

    TextView label;
    TextView end_label;
    EditText txtBox;

    @SuppressWarnings("deprecation")
    public XmlEditBox(Context context, String labelText, String initialText,
                      boolean readonly, String endlabel) {
        super(context);
        label = new TextView(context);
        if (!readonly) {
            label.setText(Html.fromHtml(labelText
                    + "<font color='red'>*</font>"));
        } else {
            label.setText(labelText);
        }
        label.setTextSize(14);
        label.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        label.setGravity(Gravity.LEFT);
        txtBox = new EditText(context);
        txtBox.setText(initialText);
        txtBox.setTextSize(14);
        txtBox.setGravity(Gravity.RIGHT);
        txtBox.setEnabled(!readonly);
        txtBox.setBackgroundResource(R.drawable.edit_all_border);
        txtBox.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2));

        this.addView(label);
        this.addView(txtBox);
        if (endlabel != null && !endlabel.isEmpty()) {
            end_label = new TextView(context);
            end_label.setText(endlabel);
            end_label.setLayoutParams(new LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            end_label.setGravity(Gravity.RIGHT);
            this.addView(end_label);
        }
    }

    public XmlEditBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public void makeNumeric() {
        DigitsKeyListener dkl = new DigitsKeyListener(true, true);
        txtBox.setKeyListener(dkl);
    }

    public void makeDate() {
        // txtBox.setInputType(InputType.TYPE_CLASS_DATETIME);
        DateTimeKeyListener dkl = new DateTimeKeyListener();
        txtBox.setKeyListener(dkl);
    }

    public void makePWD() {
        txtBox.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
    }

    public String getValue() {
        return txtBox.getText().toString();
    }

    public void setValue(String v) {
        txtBox.setText(v);
    }

    public String getText() {
        return txtBox.getText().toString();
    }

}