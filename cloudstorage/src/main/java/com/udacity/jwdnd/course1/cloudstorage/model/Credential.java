package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer credentialid;
    private String url;
    private String username;
    private String salt;
    private String password_credential;
    private Integer userid;

    public Credential(
            Integer credentialid,
            String url,
            String username,
            String salt,
            String password,
            Integer userid
    ) {
        this.credentialid = null;
        this.url = url;
        this.username = username;
        this.salt = salt;
        this.password_credential = password;
        this.userid = userid;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword_credential() {
        return password_credential;
    }

    public void setPassword_credential(String password_credential) {
        this.password_credential = password_credential;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
