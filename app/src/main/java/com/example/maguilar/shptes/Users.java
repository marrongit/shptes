package com.example.maguilar.shptes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by maguilar on 26/06/2018.
 */

public class Users extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String f_last_name;
    @Required
    private String s_last_name;
    @Required
    private String email;
    @Required
    private String pass;
    private int image;

    public Users(){}
    public Users(String name,String f_last_name,String s_last_name,String email,String pass,int image){
        this.id = MyApplication.UserId.incrementAndGet();
        this.name = name;
        this.f_last_name = f_last_name;
        this.s_last_name = s_last_name;
        this.email = email;
        this.pass = pass;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getF_last_name() {
        return f_last_name;
    }

    public void setF_last_name(String f_last_name) {
        this.f_last_name = f_last_name;
    }

    public String getS_last_name() {
        return s_last_name;
    }

    public void setS_last_name(String s_last_name) {
        this.s_last_name = s_last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }
}
