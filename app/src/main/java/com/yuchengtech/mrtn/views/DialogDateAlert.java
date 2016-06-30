package com.yuchengtech.mrtn.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.yuchengtech.mrtn.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * 日期操作对话框
 *
 * @author lovesych1314
 */
public class DialogDateAlert extends Dialog implements
        android.view.View.OnClickListener {

    private TextView txt_title;
    private TextView txt_content;
    private DatePicker datePicker1;
    private TimePicker timePicker1;
    private TextView txt_sure;
    private TextView txt_canle;

    private onDialogDateAlertData onDissDatas = null;
    private String title = "";
    private String content = "";

    public DialogDateAlert(Activity context, String title, String content) {
        super(context, R.style.Mydialog);
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mrtn_atv_dialog_date_alert);
        setCancelable(false);
        intiView();
    }

    private void intiView() {

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText(title);
        txt_content = (TextView) findViewById(R.id.txt_content);
        txt_content.setText(content);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
        datePicker1.init(year, month, day, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub

            }
        });
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker1.setIs24HourView(true);
        timePicker1.setCurrentHour(h);
        timePicker1.setCurrentMinute(m);
        timePicker1.setOnTimeChangedListener(new OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            }

        });

        txt_canle = (TextView) findViewById(R.id.txt_canle);
        txt_canle.setOnClickListener(this);
        txt_sure = (TextView) findViewById(R.id.txt_sure);
        txt_sure.setOnClickListener(this);
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
                break;
            case R.id.txt_sure:
                dismiss();
                if (getOnDissDatas() != null) {
                    StringBuilder date = new StringBuilder()
                            .append(datePicker1.getYear())
                            .append((datePicker1.getMonth() + 1) < 10 ? "0"
                                    + (datePicker1.getMonth() + 1) : (datePicker1
                                    .getMonth() + 1))
                            .append((datePicker1.getDayOfMonth() < 10) ? "0"
                                    + datePicker1.getDayOfMonth() : datePicker1
                                    .getDayOfMonth())
                            .append((timePicker1.getCurrentHour() < 10) ? "0"
                                    + timePicker1.getCurrentHour() : timePicker1
                                    .getCurrentHour())
                            .append((timePicker1.getCurrentMinute() < 10) ? "0"
                                    + timePicker1.getCurrentMinute() : timePicker1
                                    .getCurrentMinute());
                    getOnDissDatas().setData(date.toString());
                }
                break;
        }

    }

    public onDialogDateAlertData getOnDissDatas() {
        return onDissDatas;
    }

    public void setOnDissDatas(onDialogDateAlertData onDissDatas) {
        this.onDissDatas = onDissDatas;
    }

    public interface onDialogDateAlertData {
        public void setData(String date);
    }

}
