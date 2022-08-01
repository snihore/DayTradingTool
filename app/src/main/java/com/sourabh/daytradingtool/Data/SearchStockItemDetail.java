package com.sourabh.daytradingtool.Data;

public class SearchStockItemDetail {

    private String title;
    private String fullName;

    public SearchStockItemDetail(String title, String fullName) {
        this.title = title;
        this.fullName = fullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
