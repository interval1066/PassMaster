package com.hellotechie.passmaster;

/**
 * Created by bwana on 10/26/15.
 */
public class Site {
    private int id;
    private int type;
    private String name;
    private String url;
    private String user;
    private String pw;
    private String desc;

    public Site()
    {
    }

    public Site(String name, String url, String user, String pw, String desc)
    {
        this.name=name;
        this.url=url;
        this.user=user;
        this.pw=pw;
        this.desc=desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPW(String pw) {
        this.pw = pw;
    }

    public void setDesc(String desc) { this.desc = desc; }

    public int getType() { return type; }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPW() {
        return pw;
    }

    public String getDesc() {
        return desc;
    }
}
