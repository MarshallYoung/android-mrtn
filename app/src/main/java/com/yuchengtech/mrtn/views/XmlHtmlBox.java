package com.yuchengtech.mrtn.views;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengtech.mrtn.utils.xml.IXmlControl;

public class XmlHtmlBox extends LinearLayout implements IXmlControl {

    TextView label;
    TextView txtBox;

    @SuppressWarnings("deprecation")
    public XmlHtmlBox(Context context, String labelText, String initialText,
                      boolean readonly) {
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
        txtBox = new TextView(context);
        txtBox.setText(Html.fromHtml(initialText));
        txtBox.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
        txtBox.setTextSize(14);
        txtBox.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        this.addView(label);
        this.addView(txtBox);
    }

    public XmlHtmlBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public void makeNumeric() {
        DigitsKeyListener dkl = new DigitsKeyListener(true, true);
        txtBox.setKeyListener(dkl);
    }

    public void makePWD() {
        txtBox.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
    }

    public String getValue() {
        return Html.toHtml((Spanned) txtBox.getText());
    }

    public void setValue(String v) {
        txtBox.setText(Html.fromHtml(v));
    }

    public String getText() {
        return Html.toHtml((Spanned) txtBox.getText());
    }

    public Spanned getSpanned() {
        return (Spanned) txtBox.getText();
    }

    public void setSpanned(Spanned spanned) {
        txtBox.setText(spanned);
    }

    public TextView GetTextView() {
        return txtBox;
    }

}