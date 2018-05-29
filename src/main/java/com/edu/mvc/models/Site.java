package com.edu.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Site {

    public static int STATE_CREATED = 0;
    public static int STATE_PARSED = 1;
    public static int STATE_CUT_DONE = 2;

    public static List<String> STATE_STRING = Arrays.asList("Добавлен", "Загружен", "Страницы обработаны");
    public static List<String> STATE_CLASS = Arrays.asList("badge-info", "badge-warning", "badge-success");

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
