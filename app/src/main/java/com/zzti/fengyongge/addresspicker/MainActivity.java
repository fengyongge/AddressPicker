package com.zzti.fengyongge.addresspicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zzti.fengyongge.addresspicker.bean.AreaBean;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv_choose;
    ArrayList<AreaBean> privice = new ArrayList<AreaBean>();
    ArrayList<AreaBean> city = new ArrayList<AreaBean>();
    ArrayList<AreaBean> contry = new ArrayList<AreaBean>();
    String selprivice = "";
    String selcity = "";
    String selcontry = "";
    String selprivicecode = "";
    String selcitycode = "";
    String selcontrycode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_choose = (TextView) findViewById(R.id.tv_choose);
        loadMore();
        tv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddressChooseWheelViewDialog endDateChooseDialog = new AddressChooseWheelViewDialog(MainActivity.this, privice, city, contry,
                        new AddressChooseWheelViewDialog.DateChooseInterface() {
                            public void getAddress(String p, String c, String con, String pcode, String cCode, String conCode) {
                                Toast.makeText(MainActivity.this, p+pcode+c+cCode+con+conCode, Toast.LENGTH_SHORT).show();
                                selprivice = p;
                                selcity = c;
                                selcontry = con;
                                selprivicecode = pcode;
                                selcitycode = cCode;
                                selcontrycode = conCode;

                            }
                        });

                endDateChooseDialog.showChooseDialog();
            }
        });
    }


    public void loadMore(){

        AreaBean areaBean = new AreaBean();
        areaBean.setArea_id("110000");
        areaBean.setArea_name("北京");
        areaBean.setArea_type("2");
        areaBean.setArea_parent_id("0");
        privice.add(areaBean);

        AreaBean areaBean2 = new AreaBean();
        areaBean2.setArea_id("110100");
        areaBean2.setArea_name("北京市");
        areaBean2.setArea_type("3");
        areaBean2.setArea_parent_id("110000");
        city.add(areaBean2);

        AreaBean areaBean4 = new AreaBean();
        areaBean4.setArea_id("110101");
        areaBean4.setArea_name("东城区");
        areaBean4.setArea_type("4");
        areaBean4.setArea_parent_id("110100");

        AreaBean areaBean5 = new AreaBean();
        areaBean5.setArea_id("110103");
        areaBean5.setArea_name("崇文区");
        areaBean5.setArea_type("4");
        areaBean5.setArea_parent_id("110100");

        contry.add(areaBean4);
        contry.add(areaBean5);
    }
}
