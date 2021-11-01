package com.example.publicohelpline;

import java.util.HashMap;

public class NewsFeedHelperClass {
    String news_title,news_desc,image_url,dateTime;

    public NewsFeedHelperClass() {
    }

    public NewsFeedHelperClass(String news_title, String news_desc, String image_url,String dateTime) {
        this.news_title = news_title;
        this.news_desc = news_desc;
        this.image_url = image_url;
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
