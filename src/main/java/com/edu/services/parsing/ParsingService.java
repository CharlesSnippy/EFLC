package com.edu.services.parsing;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public interface ParsingService {

    /**
     * Parses content of title tag from Document
     *
     * @param page html Document
     * @return page title
     */
    String parseTitle(Document page);

    /**
     * Parses site by url
     *
     * @param url source link
     * @return page in Document format
     */
    Document parsePage(String url);

    /**
     * Parses base page of site, collects its links and pages, available by this links
     *
     * @param site initiated Site
     */
    void parseSite(Site site);

    /**
     * Collects all links from page Document
     *
     * @param page source Document
     * @return all links on page
     */
    List<String> parseLinks(Document page);

    /**
     * Adds cutDocuments to site pages, cutting by content of Source page
     *
     * @param site   site to modify
     * @param source source page of cutting
     */
    void cutPages(Site site, Page source);
}
