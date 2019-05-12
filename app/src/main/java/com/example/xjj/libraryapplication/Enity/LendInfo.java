package com.example.xjj.libraryapplication.Enity;

/**
 * 创建人：郑晓辉
 * 创建日期：2019/5/10
 * 描述：
 */
public class LendInfo {
    public String bookname;
    public String userid;
    public String lenddate;
    public String state;
    public String returndate;
    public LendInfo(){

    }
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLenddate() {
        return lenddate;
    }

    public void setLenddate(String lenddate) {
        this.lenddate = lenddate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }
}
