package com.example.maguilar.shptes;

/**
 * Created by maguilar on 10/07/2018.
 */

public class Shirts {

    //properties
    private String size, genero,colores,title,desc;
    private int image;


    public Shirts(){}

    public Shirts(String size, String genero, String colores, int image,String title,String desc){
        this.size = size;
        this.genero = genero;
        this.colores = colores;
        this.image = image;
        this.title = title;
        this.desc = desc;
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
}
