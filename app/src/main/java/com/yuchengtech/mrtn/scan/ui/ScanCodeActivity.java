package com.yuchengtech.mrtn.scan.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.Zxing.CaptureActivity;
import com.yuchengtech.mrtn.base.manager.UserManager;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.bean.CfgMachine;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.GetMachineBySerialNo;
import com.yuchengtech.mrtn.http.daoimpl.PostMachine;
import com.yuchengtech.mrtn.login.bean.ShiroUser;
import com.yuchengtech.mrtn.utils.ThreeDes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 扫码
 */
public class ScanCodeActivity extends BaseActivity implements OnClickListener {

    interface Notes {
        String UPLOAD_DATA = "上传数据...";
        String LOADING_SUCCESS = "信息初始化成功";
    }

    SweetAlertDialog pDialog;

    public MyLocationListener mMyLocationListener = new MyLocationListener();
    int CODE_TYPE = -1; // 标示 (1一维码、 2、二维码 3、其他码)

    EditText txt_mcId;
    EditText txt_termId;
    EditText barcode1;
    TextView barcode2;
    TextView txt_MH_TYPE;
    TextView txt_MH_MODEL;
    TextView txt_MH_FACTORY;
    TextView txt_MH_Lat;// 纬度
    TextView txt_MH_Long;// 经度
    TextView txt_MH_addr;
    long Id;
    boolean data_exists = false;
    /**
     * 是否为手动输入一维条码
     */
    String is_manual = "1";
    String error_info = "";
    public LocationClient mLocationClient;
    private Button btn_back;
    private MachineHandler handler = new MachineHandler();
    private String tempcoor = "bd09ll";
    private LocationMode tempMode = LocationMode.Hight_Accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scancode);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        txt_MH_TYPE = (TextView) findViewById(R.id.txt_MH_TYPE);
        txt_MH_MODEL = (TextView) findViewById(R.id.txt_MH_MODEL);
        txt_MH_FACTORY = (TextView) findViewById(R.id.txt_MH_FACTORY);
        txt_MH_Lat = (TextView) findViewById(R.id.txt_MH_Lat);
        txt_MH_Long = (TextView) findViewById(R.id.txt_MH_Long);
        txt_MH_addr = (TextView) findViewById(R.id.txt_MH_addr);
        barcode1 = (EditText) findViewById(R.id.txt_barcode1);
        barcode2 = (TextView) findViewById(R.id.txt_barcode2);
        txt_mcId = (EditText) findViewById(R.id.txt_mcId);
        txt_termId = (EditText) findViewById(R.id.txt_termId);

        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationClient.registerLocationListener(mMyLocationListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanCodeActivity.this,
                        CaptureActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidData()) {
                    PostData();
                } else {
                    if (error_info == null || error_info.equals(""))
                        error_info = "数据验证失败！";
                    Toast.makeText(ScanCodeActivity.this, error_info, Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMachineData();
            }
        });
    }

    /***
     * Stop location service
     */

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent data) {
        super.onActivityResult(arg0, arg1, data);

        /**
         * 拿到解析完成的字符串 //标示 (1一维码、 2、二维码 3、其他码)
         */
        try {
            if (data != null) {
                CODE_TYPE = data.getIntExtra("code_type", -1);
                switch (CODE_TYPE) {
                    case 1:
                        barcode1.setText(data.getStringExtra("result"));
                        loadMachineData();
                        is_manual = "0";
                        break;
                    case 2:
                    /* barcode2.setText(data.getStringExtra("result")); */
                        ThreeDes threeDes = new ThreeDes();
                        barcode2.setText(threeDes.decryptModeStr("", data.getStringExtra("result")));
                        break;
                    default:
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMachineData() {
        GetMachineBySerialNo getMachinebySerialNo = new GetMachineBySerialNo(
                new IHttpURLs() {
                    @Override
                    public void despatch(Object o) {
                        Message msg = new Message();
                        msg.what = 10;
                        msg.getData().putSerializable("machine",
                                (Serializable) o);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void despatch(Object o, Object ob) {

                    }

                    @Override
                    public void handleErrorInfo(String err) {
                        Message msg = new Message();
                        msg.what = -1;
                        msg.getData().putString("err", err);
                        handler.sendMessage(msg);
                    }

                }, this);
        getMachinebySerialNo.request(barcode1.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right);
                break;

            default:
                break;
        }

    }

    private boolean ValidData() {
        if (barcode1.getText().toString().isEmpty()) {
            error_info = "SN码不能为空！";
            return false;
        }
        if (barcode2.getText().toString().isEmpty()) {
            error_info = "二维码不能为空！";
            return false;
        }
        if (txt_mcId.getText().toString().isEmpty()) {
            error_info = "商户编号不能为空！";
            return false;
        }
        if (txt_termId.getText().toString().isEmpty()) {
            error_info = "终端编号不能为空！";
            return false;
        }
        if (!data_exists)
            return false;
        return true;
    }

    // 提交数据
    private void PostData() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.UPLOAD_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(Id));
        map.put("mhId", barcode2.getText().toString());
        map.put("hostSerialNo", barcode1.getText().toString());
        map.put("mhLat", txt_MH_Lat.getText().toString());
        map.put("mhLong", txt_MH_Long.getText().toString());
        map.put("mhAddr", txt_MH_addr.getText().toString());
        map.put("mcId", txt_mcId.getText().toString());
        map.put("termId", txt_termId.getText().toString());
        map.put("isManual", is_manual);
        ShiroUser user = UserManager.getInstance().getUserInfo();
        if (user != null) {
            map.put("operator", user.userId);
        }

        PostMachine post_machine = new PostMachine(new IHttpURLs() {
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
                msg.what = 20;
                msg.getData().putString("info", o.toString());
                handler.sendMessage(msg);

            }
        });
        post_machine.request(map);
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_scan_code,
                    container, false);
            return rootView;
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                txt_MH_Lat.setText(location.getLatitude() + "");
                txt_MH_Long.setText(location.getLongitude() + "");
                txt_MH_addr.setText(location.getAddrStr());
                mLocationClient.unRegisterLocationListener(mMyLocationListener);
                mLocationClient.stop(); // 停止定位服务
            }
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            LogUtil.e("百度定位", sb.toString());

        }

    }

    @SuppressLint("HandlerLeak")
    private class MachineHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取机具信息
                    CfgMachine machine = (CfgMachine) msg.getData()
                            .getSerializable("machine");
                    if (machine != null) {
                        if (machine.getMhId() == null
                                || machine.getMhId().isEmpty()) {
                            txt_MH_TYPE.setText(machine.getMhType());
                            txt_MH_MODEL.setText(machine.getMhModel());
                            txt_MH_FACTORY.setText(machine.getMhFactory());
                            Id = machine.getId();
                            data_exists = true;
                            // 关闭软键盘
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(ScanCodeActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            Toast.makeText(ScanCodeActivity.this, Notes.LOADING_SUCCESS, Toast.LENGTH_SHORT).show();

                        } else {
                            txt_MH_TYPE.setText("");
                            txt_MH_MODEL.setText("");
                            txt_MH_FACTORY.setText("");
                            data_exists = false;
                            error_info = "机具序列号已关联，不能重复关联！";
                            Toast.makeText(ScanCodeActivity.this, error_info, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        txt_MH_TYPE.setText("");
                        txt_MH_MODEL.setText("");
                        txt_MH_FACTORY.setText("");
                        data_exists = false;
                        error_info = "机具序列号不存在！";
                        Toast.makeText(ScanCodeActivity.this, error_info, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 20: // 关联成功 -
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.cancel();
                    }
                    String info = msg.getData().getString("info");
                    Toast.makeText(ScanCodeActivity.this, info, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1: // 失败
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.cancel();
                    }
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(ScanCodeActivity.this, msgs, Toast.LENGTH_SHORT).show();
                    data_exists = false;
                    break;
            }
        }
    }
}