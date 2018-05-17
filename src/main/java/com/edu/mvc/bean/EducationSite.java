package com.edu.mvc.bean;

import java.util.ArrayList;
import java.util.List;

public class EducationSite {

    private String url;
    private List<EducationSitePage> educationSitePages = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public List<EducationSitePage> getEducationSitePages() {
        return educationSitePages;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEducationSitePages(List<EducationSitePage> educationSitePages) {
        this.educationSitePages = educationSitePages;
    }
}
