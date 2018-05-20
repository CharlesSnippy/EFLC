package com.edu.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class Site {

    private int siteId;
    private String url;
    private List<Page> pages = new ArrayList<>();

    public int getSiteId() {
        return siteId;
    }

    public String getUrl() {
        return url;
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

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
