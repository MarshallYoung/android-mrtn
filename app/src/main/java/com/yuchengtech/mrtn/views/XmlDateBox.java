package com.yuchengtech.mrtn.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Html;
import android.text.InputType;
import android.text.method.DateTimeKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.utils.xml.IXmlControl;

import java.util.Calendar;

public class XmlDateBox extends LinearLayout implements IXmlControl {

    private final Calendar cal = Calendar.getInstance();
    TextView label;
    EditText txtBox;
    Button btn;
    ImageView btn_img;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDateDisplay();
        }
    };
    private boolean display = false;

    @SuppressWarnings("deprecation")
    public XmlDateBox(Context context, String labelText, String initialText,
                      boolean readonly) {
        super(context);
        label = new TextView(context);

        label.setText(Html.fromHtml(labelText + "<font color='red'>*</font>"));
        label.setTextSize(14);
        label.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        label.setGravity(Gravity.LEFT);
        txtBox = new EditText(context);
        txtBox.setText(initialText);
        txtBox.setTextSize(14);
        txtBox.setEnabled(false);
        txtBox.setGravity(Gravity.RIGHT);
        txtBox.setBackgroundResource(R.drawable.edit_all_border);
        txtBox.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 3));
        btn_img = new ImageView(context);
        btn_img.setPadding(0, 10, 20, 0);
        btn_img.setImageResource(R.drawable.icon_arrow_right);
        LayoutParams param1 = new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        param1.gravity = Gravity.LEFT;
        btn_img.setLayoutParams(param1);

		/*
		 * btn = new Button(context); btn.setText("选择日期");
		 * btn.setLayoutParams(new LayoutParams(0,
		 * ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		 */
        this.addView(label);
        this.addView(txtBox);
        this.addView(btn_img);
        btn_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), mDateSetListener, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                        .get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        setDateTime();
    }
    public XmlDateBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay();
    }

    private void updateDateDisplay() {
        txtBox.setText(new StringBuilder().append(mYear)
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    public void makeNumeric() {
        txtBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void makeDate() {
        DateTimeKeyListener dkl = new DateTimeKeyListener();
        txtBox.setKeyListener(dkl);
    }

    public void makePWD() {
        txtBox.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
    }

    public void display() {
        setDisplay(true);
        btn_img.setVisibility(View.GONE);
        label.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        txtBox.setLayoutParams(new LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 2));
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

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

}