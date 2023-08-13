package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid;
    private String notetile;
    private String nodedescription;
    private Integer userid;

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

    public String getNodedescription() {
        return nodedescription;
    }

    public void setNodedescription(String nodedescription) {
        this.nodedescription = nodedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}

