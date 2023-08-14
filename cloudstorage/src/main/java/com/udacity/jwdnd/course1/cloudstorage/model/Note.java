package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid;
    private String notetile;
    private String notedescription;
    private Integer userid;

    public Note(
            Integer noteid,
            String notetile,
            String nodedescription,
            Integer userid
    ) {
        this.noteid = noteid;
        this.notetile = notetile;
        this.notedescription = nodedescription;
        this.userid = userid;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getNotetile() {
        return notetile;
    }

    public void setNotetile(String notetile) {
        this.notetile = notetile;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}

