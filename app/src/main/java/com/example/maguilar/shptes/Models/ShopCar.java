package com.example.maguilar.shptes.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by maguilar on 12/07/2018.
 */

public class ShopCar extends RealmObject{
    @PrimaryKey
    private int id_user;
    private int id_pto;
    private int id_cat;
    private int id_subcat;

    public ShopCar(){}

    public ShopCar(int id_user,int id_pto,int id_cat,int id_subcat){
        this.id_user = id_user;
        this.id_pto = id_pto;
        this.id_cat = id_cat;
        this.id_subcat = id_subcat;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_pto() {
        return id_pto;
    }

    public int getId_cat() {
        return id_cat;
    }

    public int getId_subcat() {
        return id_subcat;
    }
}
