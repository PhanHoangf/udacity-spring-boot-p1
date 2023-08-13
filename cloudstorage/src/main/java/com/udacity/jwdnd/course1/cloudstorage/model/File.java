package com.udacity.jwdnd.course1.cloudstorage.model;

// fileId INT PRIMARY KEY auto_increment,
//    filename VARCHAR,
//    contenttype VARCHAR,
//    filesize VARCHAR,
//    userid INT,
//    filedata BLOB,
//    foreign key (userid) references USERS(userid)

import java.sql.Blob;

public class File {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private Blob filedata;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Blob getFiledata() {
        return filedata;
    }

    public void setFiledata(Blob filedata) {
        this.filedata = filedata;
    }
}
