package com.edu.mvc.bean;

import org.jsoup.nodes.Document;

public class EducationSitePage {

    private String url;
    private Document pageDocument;

    public EducationSitePage() {
        this.url = null;
        this.pageDocument = null;
    }

    public EducationSitePage(String url, Document pageDocument) {
        this.url = url;
        this.pageDocument = pageDocument;
    }

    public Document getPageDocument() {
        return pageDocument;
    }

    public String getUrl() {
        return url;
    }

    public void setPageDocument(Document pageDocument) {
        this.pageDocument = pageDocument;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
