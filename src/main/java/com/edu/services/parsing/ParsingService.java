package com.edu.services.parsing;

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

    List<String> ELEMENTS_BLACKLIST = Arrays.asList("script", "meta", "link", "img");

    String parseTitle(Document page);

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

    List<String> parseLinks(Document page);
}
