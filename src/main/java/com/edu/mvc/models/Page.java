package com.edu.mvc.models;

import org.jsoup.nodes.Document;

public class Page {

    private int pageId;
    private int siteId;
    private String url;
    private Document document;

    public int getPageId() {
        return pageId;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getUrl() {
        return url;
    }

    public Document getDocument() {
        return document;
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

    public void setDocument(Document document) {
        this.document = document;
    }

}
