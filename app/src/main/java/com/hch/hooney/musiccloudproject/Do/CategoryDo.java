package com.hch.hooney.musiccloudproject.Do;

public class CategoryDo {
    private String c_id;
    private String c_title;
    private String c_auth;
    private String c_image_url;

    public CategoryDo() {
        this.c_id = "None";
        this.c_title="None";
        this.c_auth = "None";
        this.c_image_url = "None";
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_title() {
        return c_title;
    }

    public void setC_title(String c_title) {
        this.c_title = c_title;
    }

    public String getC_auth() {
        return c_auth;
    }

    public void setC_auth(String c_auth) {
        this.c_auth = c_auth;
    }

    public String getC_image_url() {
        return c_image_url;
    }

    public void setC_image_url(String c_image_url) {
        this.c_image_url = c_image_url;
    }
}
