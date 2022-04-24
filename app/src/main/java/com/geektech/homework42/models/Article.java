package com.geektech.homework42.models;

import java.io.Serializable;

public class Article implements Serializable {

    private String text;
    private Long date;

    public Article(String text, Long date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
