package com.example.dognutrition;

public class ArticleClass {
    private String dataTitle;
    private int dataDesc;
    private int dataImage;

    public ArticleClass(String dataTitle, int dataDesc, int dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public int getDataDesc() {
        return dataDesc;
    }

    public int getDataImage() {
        return dataImage;
    }
}

