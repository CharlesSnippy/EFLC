package com.edu.mvc.parsing;

import com.edu.mvc.bean.EducationSite;
import org.jsoup.nodes.Document;

public interface ParsingService {

    /**
     * Get site by url
     *
     * @param url
     * @return
     */
    Document collectDocument(String url);

    /**
     * Get all links and HTML documents
     *
     * @param url
     * @param educationSite
     */
    void getAllPages(String url, EducationSite educationSite);
}
