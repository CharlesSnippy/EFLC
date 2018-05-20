package com.edu.services.parsing;

import com.edu.mvc.models.Site;
import org.jsoup.nodes.Document;

public interface ParsingService {

    /**
     * Get site by url
     *
     * @param url
     * @return
     */
    Document parsePage(String url);

    /**
     * Get all links and HTML documents
     *
     * @param site
     */
    void parseSite(Site site);

}
