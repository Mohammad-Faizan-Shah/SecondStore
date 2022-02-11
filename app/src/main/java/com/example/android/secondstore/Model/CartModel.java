package com.example.android.secondstore.Model;

public class CartModel {
    String  image, pid, pname, price, posted;

    public CartModel(){

    }

    public CartModel(String image, String pid, String pname, String price, String posted) {
        this.image = image;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.posted =posted;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
