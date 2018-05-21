package com.edu.services.parsing;

import com.edu.mvc.models.Site;
import org.jsoup.nodes.Document;

import java.util.List;

public interface ParsingService {

    char TYPE_EQUAL = '=';
    char TYPE_ADD = '+';
    char TYPE_DELETE = '-';

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

    List<DiffResult> getDifferences(List<String> a, List<String> b);
}
