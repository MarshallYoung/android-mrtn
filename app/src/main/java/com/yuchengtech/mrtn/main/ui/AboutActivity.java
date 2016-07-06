package com.yuchengtech.mrtn.main.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.utils.SystemUtil;

/**
 * 关于界面
 *
 * @author lovesych1314
 */
public class AboutActivity extends BaseActivity implements OnClickListener {

    private Button btn_back;
    private TextView txt_title;
    private TextView txt_ver;// 版本号
    private Button btn_phone;

    private String phoneString = "010-84186989";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mrtn_atv_more_about);
        intiView();
    }

    private void intiView() {
        String html = "<div class='text_content'>	<p style='text-indent: 2em;'>"
                + "北京宇信易诚科技有限公司（以下简称宇信易诚）是中国金融IT行业规模最大、最具影响力的企业之一。"
                + "集团总部位于北京，员工人数超过4500名，已在十余个城市设立了分子公司和代表处，"
                + "为客户构建了全国性的服务网络。</p><p style='text-indent: 2em;'>"
                + "秉持“专注金融 用心至诚”的企业理念，宇信易诚的业务遍及全国各地，业务类型涵盖咨询服务、"
                + "软件产品及实施服务、应用软件开发、运营外包服务、系统集成及增值服务等多个领域，"
                + "始终保持在网络银行、信贷管理、商业智能、呼叫中心、风险管理领域的绝对领先地位（最新IDC报告）。"
                + "同时，在客户关系管理、移动金融、前置前端以及系统增值服务等领域也拥有业界领先的产品并"
                + "保持着强劲的增长势头。十余年服务于中国金融客户的经验，使宇信易诚积累了丰厚的专业知识和技术人才"
                + "资源，并与IBM、HP、ORACLE、Apple、华为等多家国际著名公司结为战略合作伙伴。</p>"
                + "<p style='text-indent: 2em;'>如今，宇信易诚已经为中国人民银行、三大政策性银行、"
                + "五大国有商业银行、13家股份制银行、十余家外资银行、以及100多家区域性商业银行提供了安全、"
                + "灵活、高效，符合行业发展特点的软件产品及行业解决方案。</p>"
                + "<p style='text-indent: 2em;'>公司自2010年起在一直稳居银行业IT解决方案服务商第一名"
                + "（IDC报告），成为行业的领导者。凭借优异的业绩和在金融行业的深厚积淀及对行业的深刻理解，"
                + "宇信易诚连续获得“中国软件和信息服务十大领军企业”、“金软件-银行业软件典范企业”、"
                + "“成就十年·中国软件和信息服务领军企业”、“中国经济·最佳推动力企业”等众多奖项。</p></div>";
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("关于我们");
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(Html.fromHtml(html));
        txt_ver = (TextView) findViewById(R.id.txt_ver);
        txt_ver.setText("版本号:" + SystemUtil.GetAppVersionName(this));
        btn_phone = (Button) findViewById(R.id.btn_phone);
        btn_phone.setOnClickListener(this);
        btn_phone.setText("客服电话：" + phoneString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}