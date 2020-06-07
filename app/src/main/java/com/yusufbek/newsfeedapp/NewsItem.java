package com.yusufbek.newsfeedapp;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private String mPublishedDate;
    private String mSection;
    private String mTitle;
    private String mUrl;

    public NewsItem(String publishedDate, String section, String title, String url) {
        mPublishedDate = publishedDate;
        mSection = section;
        mTitle = title;
        mUrl = url;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}