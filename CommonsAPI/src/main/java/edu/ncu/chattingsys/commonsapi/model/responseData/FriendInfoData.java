package edu.ncu.chattingsys.commonsapi.model.responseData;

import java.util.Date;

public class FriendInfoData {

    private Integer uid;
    private String uname;
    private Character sex;
    private Date register_date;
    private String mail;
    private String group;
    private Boolean zone_permission;
    private Boolean isOnline;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getZone_permission() {
        return zone_permission;
    }

    public void setZone_permission(Boolean zone_permission) {
        this.zone_permission = zone_permission;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
