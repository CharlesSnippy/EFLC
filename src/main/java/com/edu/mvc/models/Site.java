package com.edu.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class Site {

    public static int STATE_CREATED = 0;
    public static int STATE_PARSED = 1;

    private int siteId;
    private String url;
    private String title;
    private int state;
    private List<Page> pages = new ArrayList<>();



    public int getSiteId() {
        return siteId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getState() {
        return state;
    }

    public List<Page> getPages() {
        return pages;
    }



    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
