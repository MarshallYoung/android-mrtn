package com.yuchengtech.mrtn.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.http.daoimpl.UploadUtil;
import com.yuchengtech.mrtn.utils.DialogListener;
import com.yuchengtech.mrtn.utils.xml.IXmlControl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlImgBox extends LinearLayout implements IXmlControl {

    // 上传文件响应
    protected static final int UPLOAD_FILE_DONE = 2;
    TextView label;
    private ImageView imgBox;
    private boolean display;
    private Handler filehandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_FILE_DONE:
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(msg.obj));
                        String success = obj.optString("success");
                        if (success.equals("00")) {
                            String file_id = obj.optString("file_id");
                            getImgBox().setTag(file_id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private OnClickListener signListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            WritePadDialog writeTabletDialog = new WritePadDialog(
                    v.getContext(), new DialogListener() {
                public void refreshActivity(Object object) {
                    if (object != null) {
                        String sign_dir = Environment
                                .getExternalStorageDirectory()
                                + File.separator;
                        Bitmap mSignBitmap = (Bitmap) object;
                        String sign = createFile(mSignBitmap);
                        getImgBox().setImageBitmap(mSignBitmap);
                        //getImgBox().setTag(sign);
                        UploadUtil uploadUtil = UploadUtil
                                .getInstance();
                        uploadUtil.setHandler(filehandler);
                        uploadUtil.setOnUploadProcessListener(null);
                        Map<String, String> params = new HashMap<String, String>();
                        uploadUtil.uploadFile(sign_dir + sign, "pic",
                                params);
                    }
                }
            }, dm.widthPixels, dm.heightPixels);

            writeTabletDialog.show();
        }
    };

    @SuppressWarnings("deprecation")
    public XmlImgBox(Context context, String labelText, String initialText,
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
        setImgBox(new ImageView(context));
        getImgBox().setLayoutParams(new LayoutParams(0, 200, 2));
        getImgBox().setOnClickListener(signListener);
        getImgBox().setImageResource(R.drawable.img_empty);
        this.addView(label);
        this.addView(getImgBox());
    }
    public XmlImgBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private String createFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        String _path = null;
        String _filename = null;
        try {
            String sign_dir = Environment.getExternalStorageDirectory()
                    + File.separator;
            _filename = System.currentTimeMillis() + ".png";
            _path = sign_dir + _filename;
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _filename;
    }

    public String getValue() {
        return getImgBox().getTag().toString();
    }

    public void setValue(String v) {

        getImgBox().setTag(v);
    }

    public String getText() {
        return getImgBox().getTag().toString();
    }

    public void display(boolean b) {
        setDisplay(b);
        getImgBox().setClickable(b);
    }

    public ImageView getImgBox() {
        return imgBox;
    }

    public void setImgBox(ImageView imgBox) {
        this.imgBox = imgBox;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

}