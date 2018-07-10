package com.example.maguilar.shptes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by maguilar on 10/07/2018.
 */

public class Polos extends RealmObject{

    //properties
    @PrimaryKey
    private int id;
    @Required
    private String size, genero,colores,title,desc;
    private int image;
    private int subCategoria;


    public Polos(){}

    public Polos(String size, String genero, String colores, int image,String title,String desc,int subCategoria){
        this.id = MyApplication.ShirtId.incrementAndGet();
        this.size = size;
        this.genero = genero;
        this.colores = colores;
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.subCategoria = subCategoria;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getColores() {
        return colores;
    }

    public void setColores(String colores) {
        this.colores = colores;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(int subCategoria) {
        this.subCategoria = subCategoria;
    }
}
