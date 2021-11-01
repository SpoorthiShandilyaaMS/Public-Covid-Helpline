package com.example.publicohelpline;

import java.util.HashMap;
import java.util.Map;

public class NewsHelperClass {
    String news_title,news_desc,admin_constituency,image_url,dateTime;


    public NewsHelperClass() {
    }

    public NewsHelperClass(String news_title, String news_desc, String admin_constituency, String image_url,String dateTime) {
        this.news_title = news_title;
        this.news_desc = news_desc;
        this.admin_constituency = admin_constituency;
        this.image_url = image_url;
        this.dateTime = dateTime;
    }

    public NewsHelperClass(String image_url) {
        this.image_url = image_url;
    }

    public NewsHelperClass(String news_title, String news_desc, String admin_constituency) {
        this.news_title = news_title;
        this.news_desc = news_desc;
        this.admin_constituency = admin_constituency;
        this.dateTime = dateTime;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_desc() {
        return news_desc;
    }

    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    public String getAdmin_constituency() {
        return admin_constituency;
    }

    public void setAdmin_constituency(String admin_constituency) {
        this.admin_constituency = admin_constituency;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
