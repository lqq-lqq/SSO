package com.sso.main.com.sso.main.domain;

public class User {
    private String username;
    private String password;
    public User(){
        username="";
        password="";
    }
    public User(String u,String p){
        username=u;
        password=p;
    }
    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

