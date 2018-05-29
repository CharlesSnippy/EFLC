package com.edu.mvc.models;

import org.jsoup.nodes.Document;

public class Page {

    private int pageId;
    private int siteId;
    private String url;
    private String title;
    private Document document;
    private Document cutDocument;


    public int getPageId() {
        return pageId;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Document getDocument() {
        return document;
    }

    public Document getCutDocument() {
        return cutDocument;
    }


    public void setPageId(int pageId) {
        this.pageId = pageId;
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

    public void setDocument(Document document) {
        this.document = document;
    }

    public void setCutDocument(Document cutDocument) {
        this.cutDocument = cutDocument;
    }

}
