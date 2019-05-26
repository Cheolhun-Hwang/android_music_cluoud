package com.hch.hooney.musiccloudproject.Do;

import java.io.Serializable;

public class TalkData implements Serializable {
    private String userIconUrl;
    private String userName;
    private String createDate;
    private String msg;
    private String userId;

    public TalkData() {
        this.userIconUrl = "None";
        this.userName = "None";
        this.createDate = "None";
        this.msg = "None";
        this.userId = "None";
    }

    public TalkData(String userIconUrl, String userName, String createDate, String msg, String userId) {
        this.userIconUrl = userIconUrl;
        this.userName = userName;
        this.createDate = createDate;
        this.msg = msg;
        this.userId = userId;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
