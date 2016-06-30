package com.yuchengtech.mrtn.merchant.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.DataType;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.order.GetMcInfo;
import com.yuchengtech.mrtn.merchant.bean.MerchantInfo;
import com.yuchengtech.mrtn.merchant.bean.TerminalInfo;
import com.yuchengtech.mrtn.utils.xml.SaxXmlParser;
import com.yuchengtech.mrtn.utils.xml.XmlField;
import com.yuchengtech.mrtn.utils.xml.XmlForm;
import com.yuchengtech.mrtn.utils.xml.XmlGroup;
import com.yuchengtech.mrtn.views.XmlEditBox;
import com.yuchengtech.mrtn.views.XmlPickMany;
import com.yuchengtech.mrtn.views.XmlPickOne;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 商户信息详细界面
 *
 * @author oyj
 */
public class MerchantInfoActivity extends BaseActivity implements
        OnClickListener {

    SweetAlertDialog pDialog;
    private Button btn_back;
    private TextView txt_title;
    private Button btn_submit;
    private InputStream is = null;
    private LinearLayout lLayout;
    private LinearLayout childlLayout;
    private XmlForm theForm = null;
    private LayoutParams params;
    private McinfoHandler handler = new McinfoHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrtn_atv_order_info);
        ButterKnife.bind(this);
        Long id = (Long) getIntent().getSerializableExtra("id");
        initView();
        initData(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initData(Long id) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.INIT_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        GetMcInfo mcinfo = new GetMcInfo(new IHttpURLs() {
            @Override
            public void handleErrorInfo(String err) {
                Message msg = new Message();
                msg.what = -1;
                msg.getData().putString("err", err);
                handler.sendMessage(msg);
            }

            @Override
            public void despatch(Object o, Object ob) {

            }

            @Override
            public void despatch(Object o) {
                Message msg = new Message();
                msg.what = 10;
                msg.getData().putSerializable("mcinfo", (Serializable) o);
                handler.sendMessage(msg);
            }
        });
        mcinfo.request(id);
    }

    /*
     * 加载xml
     */
    private void LoadXmlUI(MerchantInfo mcInfo) {
        try {
            is = this.openFileInput("task_mcinfo.xml");
            SaxXmlParser parser = new SaxXmlParser(); // 创建SaxBookParser实例
            theForm = parser.parse(is); // 解析输入流
            DisplayForm(mcInfo);
            is.close();
        } catch (FileNotFoundException e1) {
            Log.e("mrtn", e1.getMessage());
        } catch (Exception e) {
            Log.e("mrtn", e.getMessage());
        }

    }

    /*
     * 初努化界面控件
     */
    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.txt_title);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        txt_title.setText("商户详情");
        btn_submit.setVisibility(View.GONE);
        lLayout = (LinearLayout) findViewById(R.id.llayout);
        Button btn_up = (Button) findViewById(R.id.btn_up);
        btn_up.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    /*
     * 控件生成
     */
    private void DisplayForm(MerchantInfo mcInfo) throws Exception {
        lLayout.removeAllViews();
        LinearLayout bg_line;
        for (XmlGroup group : theForm.getGroups()) {
            if (!group.getType().equals("grid")) {
                params = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                for (XmlField field : group.getFields()) {
                    childlLayout = new LinearLayout(this);
                    childlLayout.setGravity(Gravity.LEFT);
                    bg_line = new LinearLayout(this);
                    bg_line.setBackgroundResource(R.color.gray_line);
                    View v = GenerationView(field, mcInfo);
                    if (v != null) {
                        childlLayout.addView(v, params);
                        lLayout.addView(childlLayout);
                        lLayout.addView(bg_line);
                    }
                }
            } else {
                if (mcInfo.getTerms() != null) {
                    for (TerminalInfo item : mcInfo.getTerms()) {
                        childlLayout = new LinearLayout(this);
                        params = new LinearLayout.LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT);
                        childlLayout.setLayoutParams(params);
                        View item_ui = GenerationColumn(item);
                        childlLayout.addView(item_ui, params);
                        lLayout.addView(childlLayout);
                    }
                }

            }
        }
        setTitle(theForm.getFormName());

    }

    private View GenerationColumn(TerminalInfo item) {
        View convertView;
        TextView tv_column_termId;
        TextView tv_column_termAddr;
        TextView tv_column_servicesManName;
        TextView tv_column_salesManName;
        TextView tv_column_hostSerialNo;
        TextView tv_column_termLinknm;
        TextView tv_column_termLinktel;
        convertView = LayoutInflater.from(MerchantInfoActivity.this).inflate(
                R.layout.mrtn_mcinfo__term_list_item, null);
        tv_column_termId = (TextView) convertView
                .findViewById(R.id.tv_column_termId);
        tv_column_termAddr = (TextView) convertView
                .findViewById(R.id.tv_column_termAddr);
        tv_column_servicesManName = (TextView) convertView
                .findViewById(R.id.tv_column_servicesManName);
        tv_column_salesManName = (TextView) convertView
                .findViewById(R.id.tv_column_salesManName);
        tv_column_hostSerialNo = (TextView) convertView
                .findViewById(R.id.tv_column_hostSerialNo);
        tv_column_termLinknm = (TextView) convertView
                .findViewById(R.id.tv_column_termLinknm);
        tv_column_termLinktel = (TextView) convertView
                .findViewById(R.id.tv_column_termLinktel);

        tv_column_termId.setText(item.getTermId());
        tv_column_termAddr.setText(item.getTermAddr());
        tv_column_servicesManName.setText(item.getServicesManName());
        tv_column_salesManName.setText(item.getSalesManName());
        tv_column_hostSerialNo.setText(item.getHostSerialNo());
        tv_column_termLinknm.setText(item.getTermLinknm());
        tv_column_termLinktel.setText(item.getTermLinktel());
        return convertView;
    }

    /*
     * 反射属性值
     */
    private String RefactorProperty(Object order, String fieldname)
            throws Exception {
        String value = "";
        String temp = "";
        Object obj = null;
        if (fieldname.indexOf(".") > 0) {
            temp = fieldname.substring(0, fieldname.indexOf("."));
            fieldname = fieldname.substring(fieldname.indexOf(".") + 1);
            Field field = order.getClass().getDeclaredField(temp);
            field.setAccessible(true);
            obj = (Object) field.get(order);
            return RefactorProperty(obj, fieldname);
        } else {
            Field field = order.getClass().getDeclaredField(fieldname);
            field.setAccessible(true);
            value = String.valueOf(field.get(order));
            return value;
        }

    }

    /*
     * 根据xml生成单个控件
     */
    private View GenerationView(XmlField field, MerchantInfo mcInfo)
            throws Exception {

        switch (field.getType()) {
            case text:
                field.setObj(new XmlEditBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly(), field.getEnd_label()));
                break;
            case pwd:
                field.setObj(new XmlEditBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly(), field.getEnd_label()));
                ((XmlEditBox) field.getObj()).makePWD();
                break;
            case numeric:
                field.setObj(new XmlEditBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly(), field.getEnd_label()));
                ((XmlEditBox) field.getObj()).makeNumeric();
                break;
            case date:
                field.setObj(new XmlEditBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly(), field.getEnd_label()));
                ((XmlEditBox) field.getObj()).makeNumeric();
                break;
            case choice:
                field.setObj(new XmlPickOne(this, field.getLabel(), field
                        .getOptions(), field.getTags(), field.getDefaultStr()));
                break;
            case check:
                field.setObj(new XmlPickMany(this, field.getLabel(), field
                        .getOptions(), field.getTags(), ""));
                break;
            case hidden:

                break;
            default:
                break;
        }
        String value = RefactorProperty(mcInfo, field.getField());
        if (value != null && value.equals("null")) {
            value = "";
        }
        if (field.getObj() != null) {
            if (field.getName().equals("txt_g_mcNature")) {
                value = DataType.mcNatureMp.get(value);
            } else if (field.getName().equals("txt_g_mcType")) {
                value = DataType.sysCode.get(value);
            } else if (field.getName().equals("txt_g_mcLglidtp")) {
                value = DataType.mcLglidtpMap.get(value);
            } else if (field.getName().equals("txt_g_mcStatus")) {
                value = DataType.mcStatusMap.get(value);
            }
            field.getObj().setValue(value);
        }
        return field.getObj() == null ? null : (View) field.getObj();
    }

    interface Notes {
        String INIT_DATA = "初始化数据...";
    }

    private class McinfoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取配置信息
                    pDialog.cancel();
                    MerchantInfo mcinfo = (MerchantInfo) msg.getData()
                            .getSerializable("mcinfo");
                    LoadXmlUI(mcinfo);
                    break;
                case -1: // 失败 - 获取配置信息
                    pDialog.cancel();
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(MerchantInfoActivity.this, msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}