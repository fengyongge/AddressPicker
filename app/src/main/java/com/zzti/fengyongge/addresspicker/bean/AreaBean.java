package com.zzti.fengyongge.addresspicker.bean;

import java.io.Serializable;


public class AreaBean implements Serializable{

    private String area_id;
    private String area_type;
    private String area_name;
    private String area_parent_id;
    private String area_zip;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_type() {
        return area_type;
    }

    public void setArea_type(String area_type) {
        this.area_type = area_type;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_parent_id() {
        return area_parent_id;
    }

    public void setArea_parent_id(String area_parent_id) {
        this.area_parent_id = area_parent_id;
    }

    public String getArea_zip() {
        return area_zip;
    }

    public void setArea_zip(String area_zip) {
        this.area_zip = area_zip;
    }
}
