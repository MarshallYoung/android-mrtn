package com.yuchengtech.mrtn.order.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.lidroid.xutils.BitmapUtils;
import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.DataType;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.UploadUtil;
import com.yuchengtech.mrtn.http.daoimpl.UploadUtil.OnUploadProcessListener;
import com.yuchengtech.mrtn.http.daoimpl.order.GetInitTaskOrder;
import com.yuchengtech.mrtn.http.daoimpl.order.GetTaskOrder;
import com.yuchengtech.mrtn.http.daoimpl.order.PostTaskOrder;
import com.yuchengtech.mrtn.order.adapter.ImageAdapter;
import com.yuchengtech.mrtn.order.bean.OrderInfo;
import com.yuchengtech.mrtn.utils.FileUtil;
import com.yuchengtech.mrtn.utils.URLDrawable;
import com.yuchengtech.mrtn.utils.xml.ControlEnum;
import com.yuchengtech.mrtn.utils.xml.Grid;
import com.yuchengtech.mrtn.utils.xml.GridColumn;
import com.yuchengtech.mrtn.utils.xml.SaxXmlParser;
import com.yuchengtech.mrtn.utils.xml.XmlField;
import com.yuchengtech.mrtn.utils.xml.XmlForm;
import com.yuchengtech.mrtn.utils.xml.XmlGroup;
import com.yuchengtech.mrtn.views.XmlDateBox;
import com.yuchengtech.mrtn.views.XmlEditBox;
import com.yuchengtech.mrtn.views.XmlHtmlBox;
import com.yuchengtech.mrtn.views.XmlImgBox;
import com.yuchengtech.mrtn.views.XmlPickMany;
import com.yuchengtech.mrtn.views.XmlPickOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 订单详情界面
 */
public class OrderInfoActivity extends BaseActivity implements OnClickListener, OnUploadProcessListener {

    private static final String TAG = "OrderInfoActivity";
    // 上传文件响应
    protected static final int UPLOAD_FILE_DONE = 2;
    private static final int TAKE_PICTURE = 1;
    // 上传初始化
    private static final int UPLOAD_INIT_PROCESS = 4;
    // 上传中
    private static final int UPLOAD_IN_PROCESS = 5;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    public List<String> drr = new ArrayList<String>();
    SweetAlertDialog pDialog;
    @Bind(R.id.btn_back)
    Button btn_back;// 后退
    @Bind(R.id.txt_title)
    TextView txt_title;// 标题
    @Bind(R.id.btn_submit)
    Button btn_submit;// 提交
    @Bind(R.id.llayout)
    LinearLayout lLayout;// 订单信息
    @Bind(R.id.imagesgrid)
    GridView mGridView;// 照片组
    @Bind(R.id.btn_up)
    Button btn_up;// 添加图片,上传图片
    BitmapUtils bitmapUtils;
    LocationClient mLocationClient;
    private LinearLayout childlLayout;
    private String taskId;
    private String taskType;
    private OrderInfo taskOrder;
    private String mcId;
    private String termId;
    private String taskStatus;
    private XmlForm theForm = null;
    private LayoutParams params;
    private OrderInfoHandler handler = new OrderInfoHandler();
    private GetTaskOrder getTaskOrder;
    private String imagesStr = "";
    private ImageAdapter mImageAdapter;
    private InputStream is = null;
    private OrderLocationListener LocationListener = new OrderLocationListener();
    private String str_lat = "";
    private String str_long = "";
    private String str_addr = "";
    private String path;
    private Uri photoUri;
    private Handler filehandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_INIT_PROCESS:
                    break;
                case UPLOAD_IN_PROCESS:
                    break;
                case UPLOAD_FILE_DONE:
                    pDialog.cancel();
                    btn_submit.setClickable(true);// 图片上传成完后，开启提交按钮
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(msg.obj));
                        String success = obj.optString("success");
                        if (success.equals("00")) {
                            String file_id = obj.optString("file_id");
                            if (theForm.getImageList().isEmpty())
                                theForm.setImageList(theForm.getImageList()
                                        + file_id);
                            else
                                theForm.setImageList(theForm.getImageList() + "|"
                                        + file_id);
                            Toast.makeText(OrderInfoActivity.this, Notes.IMAGE_UPLOAD_SUCCESS, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("上传图片", e.toString());
                        mImageAdapter.backSpace();// 回退 删除最近添加的一张图片
                        mImageAdapter.notifyDataSetChanged();
                        Toast.makeText(OrderInfoActivity.this, Notes.IMAGE_UPLOAD_FAILED, Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mrtn_atv_order_info);
        ButterKnife.bind(this);// 注解绑定控件框架
        bitmapUtils = new BitmapUtils(this);
        taskId = (String) getIntent().getSerializableExtra("taskId");
        taskStatus = (String) getIntent().getSerializableExtra("taskStatus");
        taskType = (String) getIntent().getSerializableExtra("taskType");
        mcId = (String) getIntent().getSerializableExtra("mcId");
        termId = (String) getIntent().getSerializableExtra("mcTermId");
        initView();
        if (taskId == null || taskId.isEmpty()) {
            LoadInitData(mcId, termId);
        } else {
            LoadData(taskId);
            // 初始化取经纬度相关对象
            mLocationClient = new LocationClient(getApplicationContext());
            mLocationClient.registerLocationListener(LocationListener); // 注册监听函数
            InitLocation();
            mLocationClient.start();
        }
    }

    private void LoadImageList() {
        String[] images;
        if (imagesStr != null && !imagesStr.isEmpty()) {
            images = imagesStr.split("\\|");
            String path = getTaskOrder.URL + "resources/upload/task/";
            for (int i = 0; i < images.length; i++) {
                images[i] = path + images[i];
            }
        } else {
            images = null;
        }
        mImageAdapter = new ImageAdapter(this, mGridView, images);
        mGridView.setAdapter(mImageAdapter);
    }

    private void LoadXmlUI(OrderInfo order) {
        try {
            taskType = order.getTaskType();// 生成任务单类型
            is = this.openFileInput("task_" + taskType + ".xml");
            SaxXmlParser parser = new SaxXmlParser(); // 创建SaxBookParser实例
            theForm = parser.parse(is); // 解析输入流
            DisplayForm(order);
            is.close();
        } catch (FileNotFoundException e1) {
            Log.e("mrtn", e1.getMessage());
        } catch (Exception e) {
            Log.e("mrtn", e.getMessage());
        }

    }

    private void initView() {
        btn_back.setOnClickListener(this);
        btn_up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imagePath = view.getTag().toString();
                LogUtil.e(TAG, "image path:  " + imagePath);
                Intent intent = new Intent(OrderInfoActivity.this, PhotoActivity.class);
                intent.putExtra("img_path", imagePath);
                startActivity(intent);
            }
        });
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = android.os.Environment
                    .getExternalStorageDirectory().getPath() + "/ycmrtn/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {// 创建图片文件夹
                    fileDir.mkdirs();
                }
                file = new File(sdcardPathDir + "image.jpg");
            }
            if (file != null) {
                path = file.getPath();
                photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == -1) {// 拍照
                    startPhotoZoom(photoUri);
                }
                break;
            case CUT_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    btn_submit.setClickable(false);
                    mImageAdapter.setItem(drr.get(drr.size() - 1));
                    mImageAdapter.notifyDataSetChanged();
                    toUploadFile(drr.get(drr.size() - 1));
                }
                break;
        }

    }

    private void startPhotoZoom(Uri uri) {
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String address = sDateFormat.format(new java.util.Date());
            if (!FileUtil.isFileExist("")) {
                FileUtil.createSDDir("");
            }
            drr.add(FileUtil.SDPATH + address + ".jpg");
            Uri imageUri = Uri.parse("file:///" + FileUtil.SDPATH + address + ".jpg");
            final Intent intent = new Intent("com.android.camera.action.CROP");

            // 鐓х墖URL鍦板潃
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 480);
            intent.putExtra("outputY", 480);
            // 杈撳嚭璺緞
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 杈撳嚭鏍煎紡
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            // 涓嶅惎鐢ㄤ汉鑴歌瘑鍒�
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 上传图片
    private void toUploadFile(String filepath) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.UPLOAD_IMAGE);
        pDialog.setCancelable(false);
        pDialog.show();
        String fileKey = "pic";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this); // 设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();
        uploadUtil.uploadFile(filepath, fileKey, params);
    }

    // 加载数据
    private void LoadData(String taskId) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.LOADING_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        getTaskOrder = new GetTaskOrder(new IHttpURLs() {
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
                msg.getData().putSerializable("order", (Serializable) o);
                handler.sendMessage(msg);
            }
        });
        getTaskOrder.request(taskId);
    }

    // 新增工单加载初始化数据
    private void LoadInitData(String mcId, String termId) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.LOADING_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        GetInitTaskOrder getTaskOrder = new GetInitTaskOrder(new IHttpURLs() {
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
                msg.getData().putSerializable("order", (Serializable) o);
                handler.sendMessage(msg);
            }
        });
        getTaskOrder.request(mcId, termId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_submit:
                Map<String, String> map = theForm.FormValid();
                if (map.isEmpty()) {
                    Map<String, String> map1 = theForm.getFormMapData();
                    map1.put("attachment", theForm.getImageList());// 上传附件
                    if (map1.get("taskType") != null
                            && map1.get("taskType").equals("002")) {
                        map1.put("taskVisitDetail.newLat", str_lat);
                        map1.put("taskVisitDetail.newLong", str_long);
                        map1.put("taskVisitDetail.newAddr", str_addr);
                    }
                    PostData(map1);
                } else {
                    Iterator<String> iter = map.keySet().iterator();
                    String key = "";
                    String value = "";
                    String msg = "";
                    while (iter.hasNext()) {
                        key = iter.next();
                        value = map.get(key);
                        msg += key + ":" + value + "<br/>";
                    }
                    Toast.makeText(OrderInfoActivity.this, Html.fromHtml(msg), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    // 提交数据
    private void PostData(Map<String, String> map) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.UPLOAD_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        PostTaskOrder postTaskOrder = new PostTaskOrder(new IHttpURLs() {
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
                msg.what = 30;
                msg.getData().putString("info", o.toString());
                handler.sendMessage(msg);
            }
        });
        postTaskOrder.request(map);
    }

    // 生成界面
    private void DisplayForm(OrderInfo order) throws Exception {
        lLayout.removeAllViews();
        LinearLayout bg_line;
        String title = DataType.taskOrderTypeCode.get(taskType);
        if (taskStatus.equals("2")) {
            txt_title.setText("办结  " + title + "任务单");
            btn_submit.setVisibility(View.GONE);
            btn_up.setVisibility(View.GONE);
        } else if (taskStatus.equals("1")) {
            txt_title.setText("待办  " + title + "任务单");
            btn_submit.setOnClickListener(this);
        } else {
            txt_title.setText("创建  " + title + "任务单");
            btn_submit.setText("创建");
            btn_submit.setOnClickListener(this);
            btn_up.setVisibility(View.GONE);
        }
        for (XmlGroup group : theForm.getGroups()) {
            if (!group.getType().equals("grid")) {
                params = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                for (XmlField field : group.getFields()) {
                    childlLayout = new LinearLayout(this);
                    childlLayout.setGravity(Gravity.LEFT);
                    bg_line = new LinearLayout(this);
                    bg_line.setBackgroundResource(R.color.gray_line);
                    View v = GenerationView(field, order);
                    if (v != null) {
                        childlLayout.addView(v, params);
                        lLayout.addView(childlLayout);
                        lLayout.addView(bg_line);
                    }
                }
            } else {
                childlLayout = new LinearLayout(this);
                params = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, 250);
                childlLayout.setLayoutParams(params);
                bg_line = new LinearLayout(this);
                bg_line.setBackgroundResource(R.color.gray_line);
                View grid = GenerationColumn(((Grid) group).getColumns(), order);
                if (grid != null) {
                    childlLayout.addView(grid, params);
                    lLayout.addView(childlLayout);
                    lLayout.addView(bg_line);
                }
            }
        }
        setTitle(theForm.getFormName());
        // 加载图片列表部分
        if (order.getAttachment() != null && !order.getAttachment().isEmpty()) {
            imagesStr = order.getAttachment();
        }
        LoadImageList();

    }

    // 生成列
    private View GenerationColumn(Vector<GridColumn> columns, OrderInfo order) {
        ListView listView = new ListView(this);
        ListView.LayoutParams listParams = new ListView.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(listParams);
        return listView;
    }

    // 获取属性值
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
            if (obj == null) {
                return "";
            }
            return RefactorProperty(obj, fieldname);
        } else {
            Field field = order.getClass().getDeclaredField(fieldname);
            field.setAccessible(true);
            value = String.valueOf(field.get(order));
            return value;
        }

    }

    // 生成控件
    private View GenerationView(XmlField field, OrderInfo order)
            throws Exception {
        if (taskStatus.equals("0") && field.isRequired()) {
            // 新增时，如果字段标记必填是 只读属性失效
            field.setReadonly(false);
        }

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
                field.setObj(new XmlDateBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly()));
                ((XmlDateBox) field.getObj()).makeDate();
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
            case txt_img:
                field.setObj(new XmlImgBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly()));
                break;
            case txt_html:
                field.setObj(new XmlHtmlBox(this, field.getLabel(), field
                        .getDefaultStr(), field.isReadonly()));
                break;
            default:
                break;
        }
        String value = RefactorProperty(order, field.getField());

        if (value != null && value.equals("null")) {
            value = "";
        }
        if (field.getType() == ControlEnum.hidden) {
            field.setValue(value);
        }
        if (field.getObj() != null) {
            if (field.getType() != ControlEnum.txt_html) {
                field.getObj().setValue(value);
                if (field.getType() == ControlEnum.txt_img && value != null
                        && !value.isEmpty()) {
                    XmlImgBox imgbox = ((XmlImgBox) field.getObj());
                    String path = getTaskOrder.URL + "resources/upload/task/";
                    bitmapUtils.display(imgbox.getImgBox(), path + value);
                }
            } else {
                XmlHtmlBox htmlbox = ((XmlHtmlBox) field.getObj());
                Spanned spanned = Html.fromHtml(value, new MyImageGetter(this,
                        htmlbox.GetTextView()), null);
                htmlbox.setSpanned(spanned);
            }
        }
        if (field.getObj() != null) {
            if (field.getName().equals("img_g_sign")) {
                if (taskStatus.equals("1")) {
                    ((XmlImgBox) field.getObj()).display(true);
                } else {
                    ((XmlImgBox) field.getObj()).display(false);
                }

            } else if (field.getName().equals("date_g_handleTime")) {
                if ((taskStatus.equals("1") || taskStatus.equals("2"))) {
                    ((XmlDateBox) field.getObj()).display();
                }
            }
        }
        return field.getObj() == null ? null : (View) field.getObj();
    }

    public OrderInfo getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(OrderInfo taskOrder) {
        this.taskOrder = taskOrder;
    }

    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        filehandler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        filehandler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        filehandler.sendMessage(msg);
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    interface Notes {
        String LOADING_DATA = "初始化数据...";
        String UPLOAD_IMAGE = "上传图片...";
        String UPLOAD_DATA = "上传信息...";
        String IMAGE_UPLOAD_SUCCESS = "图片上传成功";
        String IMAGE_UPLOAD_FAILED = "图片上传失败";
    }

    // html textview 图文显示 接口处理
    public class MyImageGetter implements ImageGetter {
        URLDrawable drawable;
        private Context context;
        private TextView tv;

        public MyImageGetter(Context context, TextView tv) {
            this.context = context;
            this.tv = tv;
        }

        @Override
        public Drawable getDrawable(String source) {
            Resources res = context.getResources();
            drawable = new URLDrawable(res.getDrawable(R.drawable.img_default));
            new GetImageTask(drawable, this.tv).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR, source);
            return drawable;
        }
    }

    class GetImageTask extends AsyncTask<String, Void, Bitmap> {
        URLDrawable drawable = null;
        private TextView tv;
        private ImageView iv;
        private volatile boolean running = true;

        public GetImageTask(URLDrawable drawable, TextView tv) {
            this.drawable = drawable;
            this.tv = tv;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            URL myFileUrl;
            Bitmap bitmap = null;
            InputStream is;
            HttpURLConnection conn = null;
            if (running) {
                try {
                    myFileUrl = new URL(params[0]);
                    conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(6 * 1000);
                    conn.setUseCaches(false);
                    conn.connect();
                    is = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = false;
                    SoftReference<Bitmap> softref = new SoftReference<Bitmap>(
                            BitmapFactory.decodeStream(is, null, options));
                    bitmap = softref.get();
                    is.close();
                } catch (FileNotFoundException fe) {
                    return null;
                } catch (IOException e) {
                    return null;
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
            return bitmap;
        }

        @Override
        protected void onCancelled() {
            running = false;
            super.onCancelled();

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                if (tv != null) {
                    drawable.setDrawable(new BitmapDrawable(result));
                    tv.setText(tv.getText());
                } else if (iv != null) {
                    iv.setImageBitmap(result);
                }
            }
            super.onPostExecute(result);
        }

    }

    private class OrderInfoHandler extends Handler {
        @SuppressWarnings({"deprecation"})
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取配置信息
                    LogUtil.e("查询任务详情", "获取配置信息成功");
                    pDialog.cancel();
                    setTaskOrder((OrderInfo) msg.getData().getSerializable("order"));
                    LoadXmlUI(taskOrder);
                    break;
                case 20:
                    showDialog(1);
                    break;
                case 30:
                    pDialog.cancel();
                    String info = msg.getData().getString("info");
                    Toast.makeText(OrderInfoActivity.this, info, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1: // 失败 - 获取配置信息
                    pDialog.cancel();
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(OrderInfoActivity.this, msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public class OrderLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                str_lat = location.getLatitude() + "";
                str_long = location.getLongitude() + "";
                str_addr = location.getAddrStr();
                mLocationClient.unRegisterLocationListener(LocationListener);
                mLocationClient.stop(); // 停止定位服务
            }

        }

    }
}
