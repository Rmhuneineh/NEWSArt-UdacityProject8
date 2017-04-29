package com.example.android.newsart;

/**
 * Created by rmhuneineh on 20/03/2017.
 */

public class Article {
    private String mSection;
    private String mTitle;
    private String mUrl;

    public Article(String section, String title, String url){
        mSection = section;
        mTitle = title;
        mUrl = url;
    }

    public String getSection() { return mSection; }
    public String getTitle() { return mTitle; }
    public String getUrl() { return mUrl; }
}
