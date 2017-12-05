package com.soc.matthewhaynes.sqliteapp;

/**
 * Created by matthew.haynes on 11/14/2017.
 */

public class GetInfo {

    private String id;
    private String subject;
    private String clas;
    private String section;
    private String title;
    private String date;
    private String day;
    private String time;
    private String bulding;
    private String population;
    private String wait;
    private String professor;
    private String credit;
    private String location;

    public GetInfo(String id, String subject, String clas, String section) {
        this.id = id;
        this.subject = subject;
        this.clas = clas;
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
