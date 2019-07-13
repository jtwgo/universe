package com.jtw.main.unified.entity;

import java.text.SimpleDateFormat;

public class Token
{
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String ip;

    private String username;

    private long createDate;

    private String uuid;

    public Token(String ip, String username, String uuid) {
        this.ip = ip;
        this.username = username;
        this.uuid = uuid;
        this.createDate = System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {

        return new StringBuilder().append("username=").append(username)
                .append(",").append("uuid=").append(uuid)
                .append(",").append("createDate=").append(createDate)
                .append(",").append("ip=").append(ip).toString();
    }

    public void refresh()
    {
        this.createDate = System.currentTimeMillis();
    }
}
