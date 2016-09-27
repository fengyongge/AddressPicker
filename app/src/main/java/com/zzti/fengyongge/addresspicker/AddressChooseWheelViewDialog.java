package com.zzti.fengyongge.addresspicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zzti.fengyongge.addresspicker.adapter.AbstractWheelTextAdapter;
import com.zzti.fengyongge.addresspicker.bean.AreaBean;
import com.zzti.fengyongge.addresspicker.view.WheelView;
import com.zzti.fengyongge.addresspicker.widget.OnWheelChangedListener;
import com.zzti.fengyongge.addresspicker.widget.OnWheelScrollListener;

import java.util.ArrayList;


public class AddressChooseWheelViewDialog extends Dialog implements View.OnClickListener {
    private WheelView mPriviceWheelView,mCityWheelView,mContoryWheelView;//控件
    private Adapter mContoryAdapter,mPriviceAdapter,mCityAdapter;//adapter
    ArrayList<AreaBean> privicelist = new ArrayList<AreaBean>();
    ArrayList<AreaBean> citylist = new ArrayList<AreaBean>();
    ArrayList<AreaBean> temptcitylist = new ArrayList<AreaBean>();
    ArrayList<AreaBean> contrylist = new ArrayList<AreaBean>();
    ArrayList<AreaBean> tempcontrylist = new ArrayList<AreaBean>();
    private ArrayList<String> arry_city = new ArrayList<String>();
    private ArrayList<String> arry_privice = new ArrayList<String>();
    private ArrayList<String> arry_contry = new ArrayList<String>();
    private int nowContryId = 0;
    private int nowPriviceId = 0;
    private int nowCityId = 0;
    private String nowContryString = "";
    private String nowPriviceString = "";
    private String nowCityString = "";
    private String mPriviceStr;
    private String mContrytStr;
    private String mCityStr;
    //变量
    private Button mSureButton;
    private Dialog mDialog;
    //设置文字最大和最小长度
    private final int MAX_TEXT_SIZE = 16;
    private final int MIN_TEXT_SIZE = 14;
    private Context mContext;
    private DateChooseInterface dateChooseInterface;

    public AddressChooseWheelViewDialog(Context context, ArrayList<AreaBean> privicelist, ArrayList<AreaBean> citylist, ArrayList<AreaBean> contrylist, DateChooseInterface dateChooseInterface) {
        super(context);
        this.mContext = context;
        this.dateChooseInterface = dateChooseInterface;
        mDialog = new Dialog(context, R.style.ActionSheet);
        this.privicelist = privicelist;
        this.citylist = citylist;
        this.contrylist = contrylist;
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        initView();
        initData();
    }


    private void initData() {
        initProvince();
        initListener();
    }

    /**
     * 初始化滚动监听事件
     */
    private void initListener() {
        mPriviceWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mPriviceAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mPriviceAdapter);
                mPriviceStr = arry_privice.get(wheel.getCurrentItem()) + "";
            }
        });

        mPriviceWheelView.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }
            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mPriviceAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mPriviceAdapter);
                nowPriviceString = privicelist.get(wheel.getCurrentItem()).getArea_id();
                initCity();


            }
        });

        mContoryWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mContoryAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mContoryAdapter);
                mContrytStr = arry_contry.get(wheel.getCurrentItem());


            }
        });

        mContoryWheelView.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (tempcontrylist.size() == 0) {
                    String currentText = " ";
                    setTextViewStyle(currentText, mContoryAdapter);

                } else {

                    String currentText = (String) mContoryAdapter.getItemText(wheel.getCurrentItem());
                    setTextViewStyle(currentText, mContoryAdapter);

                }
            }
        });


        mCityWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mCityAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mCityAdapter);
                mCityStr = arry_city.get(wheel.getCurrentItem());


            }
        });

        mCityWheelView.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mCityAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mCityAdapter);
                nowCityString = temptcitylist.get(wheel.getCurrentItem()).getArea_id();
                initContry();
            }
        });

    }




    /**
     * 数据初始化
     */

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_address_choose, null);
        mDialog.setContentView(view);

        mPriviceWheelView = (WheelView) view.findViewById(R.id.privice_wv);
        mContoryWheelView = (WheelView) view.findViewById(R.id.contory_wv);
        mCityWheelView = (WheelView) view.findViewById(R.id.city_wv);

        mSureButton = (Button) view.findViewById(R.id.sure_btn);

        mSureButton.setOnClickListener(this);
    }

    private void initProvince() {

        arry_privice.clear();
        for (int i = 0; i < privicelist.size(); i++) {
            arry_privice.add(privicelist.get(i).getArea_name());

        }
        nowPriviceId = 0;
        nowPriviceString = privicelist.get(0).getArea_id();
        mPriviceAdapter = new Adapter(mContext, arry_privice, nowPriviceId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mPriviceWheelView.setVisibleItems(5);
        mPriviceWheelView.setViewAdapter(mPriviceAdapter);
        mPriviceWheelView.setCurrentItem(nowPriviceId);

        mPriviceStr = arry_privice.get(nowPriviceId);
        initCity();
    }


    private void initCity() {

        arry_city.clear();
        temptcitylist.clear();
        for (int i = 0; i < citylist.size(); i++) {
            if (nowPriviceString.equals(citylist.get(i).getArea_parent_id())) {
                arry_city.add(citylist.get(i).getArea_name());
                temptcitylist.add(citylist.get(i));

            }
        }
        nowCityId = 0;
        nowCityString = temptcitylist.get(0).getArea_id();

        mCityAdapter = new Adapter(mContext, arry_city, nowCityId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mCityWheelView.setVisibleItems(5);
        mCityWheelView.setViewAdapter(mCityAdapter);
        mCityWheelView.setCurrentItem(nowCityId);
        mCityStr = arry_city.get(nowCityId);
        initContry();
    }

    private void initContry() {
        arry_contry.clear();
        tempcontrylist.clear();

        for (int i = 0; i < contrylist.size(); i++) {
            if (nowCityString.equals(contrylist.get(i).getArea_parent_id())) {
                arry_contry.add(contrylist.get(i).getArea_name());
                tempcontrylist.add(contrylist.get(i));
            }
            nowContryId = 0;
        }


        mContoryAdapter = new Adapter(mContext, arry_contry, nowContryId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mContoryWheelView.setVisibleItems(5);
        mContoryWheelView.setViewAdapter(mContoryAdapter);
        mContoryWheelView.setCurrentItem(nowContryId);
        if (arry_contry.size() == 0) {
            mContrytStr = "  ";
        } else {
            mContrytStr = arry_contry.get(nowContryId);
        }

    }




    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewStyle(String curriteItemText, Adapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(MAX_TEXT_SIZE);
                textvew.setTextColor(mContext.getResources().getColor(R.color.max_color));
            } else {
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(mContext.getResources().getColor(R.color.min_color));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn:
                String mPriviceStrCode = "";
                String mCityStrCode = "";
                String mContrytStrCode = "";
                for (int a = 0; a < privicelist.size(); a++) {
                    if (mPriviceStr.equals(privicelist.get(a).getArea_name())) {
                        mPriviceStrCode = privicelist.get(a).getArea_id();
                    }
                }
                for (int c = 0; c < temptcitylist.size(); c++) {

                    if (mCityStr.equals(temptcitylist.get(c).getArea_name())) {
                        mCityStrCode = temptcitylist.get(c).getArea_id();
                    }
                }
                if (" ".equals(mContrytStr)) {
                    mContrytStrCode = " ";
                } else {
                    for (int f = 0; f < tempcontrylist.size(); f++) {
                        if (mContrytStr.equals(tempcontrylist.get(f).getArea_name())) {
                            mContrytStrCode = tempcontrylist.get(f).getArea_id();
                        }
                    }
                }
                dateChooseInterface.getAddress(mPriviceStr, mCityStr, mContrytStr, mPriviceStrCode, mCityStrCode, mContrytStrCode);
                dismissDialog();
                break;

            default:
                break;
        }
    }








    /**
     * 滚轮的adapter
     */
    private class Adapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected Adapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, R.id.tempValue, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index) + "";
            return str;
        }
    }


    public interface DateChooseInterface {
        void getAddress(String p, String c, String con, String pcode, String cCode, String conCode);
    }


    /**
     * 弹起对话框
     */
    public void showChooseDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }

        if (null == mContext || ((Activity) mContext).isFinishing()) {
            return;
        }

        if (null != mDialog) {
            mDialog.show();
            return;
        }

        if (null == mDialog) {
            return;
        }
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }


    /**
     * 取消对话框
     */
    private void dismissDialog() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        if (null == mDialog || !mDialog.isShowing() || null == mContext
                || ((Activity) mContext).isFinishing()) {
            return;
        }
        mDialog.dismiss();
        this.dismiss();
    }



}
