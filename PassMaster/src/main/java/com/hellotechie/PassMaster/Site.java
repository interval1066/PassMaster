package com.hellotechie.PassMaster;

public class Site {

    // private variables
    public int _id;
    public String _name;
    public String _url;
    public String _user;
    public String _pw;
    public String _desc;
    public String _type;

    public Site() {
    }

    public Site(int id, String name, String url, String user, String pw, String desc, String type) {
	    this._id = id;
    	this._name = name;
	    this._url = url;
        this._user = user;
    	this._pw = pw;
        this._desc = desc;
        this._type = type;
    }

    // constructor
    public Site(String name, String url, String user, String pw, String desc, String type) {
	    this._name = name;
	    this._url = url;
        this._user = user;
	    this._pw = pw;
        this._desc = desc;
        this._type = type;
    }

    // getting ID
    public int getID() {
	return this._id;
    }

    // setting id
    public void setID(int id) {
	this._id = id;
    }

    // getting name
    public String getName() {
	return this._name;
    }

    // setting name
    public void setName(String name) {
	this._name = name;
    }

    // getting phone number
    public String getUrl() {
	return this._url;
    }

    // setting phone number
    public void setUrl(String url) {
	this._url = url;
    }

    public String getUser() {
        return this._user;
    }

    // setting email
    public void setUser(String user) {
        this._user = user;
    }

    // getting email
    public String getPw() {
	return this._pw;
    }

    // setting email
    public void setPw(String pw) {
	this._pw = pw;
    }

    public String getDesc() {
        return this._desc;
    }

    // setting email
    public void setDesc(String desc) {
        this._desc = desc;
    }

    public String getType() {
        return this._type;
    }

    // setting email
    public void setType(String type) {
        this._type = type;
    }
}