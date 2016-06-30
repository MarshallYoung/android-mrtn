package com.yuchengtech.mrtn.common;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.views.PopupListView;

import java.util.List;

public class CommonPopupMenu implements OnItemClickListener {

    private Context context;
    private List<CommonStrEntity> list;
    private PopupWindow popupWindow;
    private LookupCallBack callBack = null;

    public CommonPopupMenu(Context context, List<CommonStrEntity> list) {
        this.context = context;
        this.list = list;
        init();
    }

    private void init() {
        View convertView = LayoutInflater.from(context).inflate(
                R.layout.mrtn_atv_common_menu_list, null);
        PopupListView listView = (PopupListView) convertView
                .findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(new CommonPopupMenuAdapter(context, list));

        popupWindow = new PopupWindow(convertView, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

    }

    public void showAsDropDown(View parent) {
        popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public void setCallBack(LookupCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        CommonStrEntity ce = list.get(position);
        if (callBack != null) {
            callBack.setData(ce);
        }
        dismiss();
    }

    public interface LookupCallBack {
        public void setData(CommonStrEntity ce);
    }

}
