package com.example.lixinge.lab3;

public class goods {
    private String fl;
    private String gn;
    private String gp;
    public goods(String firstLetter, String goodsName, String goodsPrice){
        this.fl = firstLetter;
        this.gn = goodsName;
        this.gp = goodsPrice;
    }

    public String getFirstLetter(){
        return this.fl;
    }

    public String getGoodsName(){
        return this.gn;
    }

    public String getGoodsPrice() {
        return gp;
    }
}
