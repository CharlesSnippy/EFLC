package com.edu.services.parsing;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingServiceImpl implements ParsingService {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    static final Logger logger = LogManager.getLogger(ParsingServiceImpl.class);

    private static String LINK_ATTRIBUTE_KEY = "abs:href";

    @Override
    public String parseTitle(Document page) {
        return page.selectFirst("title").text();
    }

    @Override
    public Document parsePage(String url) {
        logger.info("parsePage:" + url);
        Document doc = null;
        try {
            try {
                Connection.Response res = Jsoup.connect(url).timeout(10 * 1000).execute();
                String contentType = res.contentType();
                if (contentType.contains("text/")) {
                    doc = Jsoup.connect(url).get();
                    //TODO check for documents like PDF
                }
            } catch (UnsupportedMimeTypeException typeEx) {
                logger.error(typeEx.getMessage() + " " + typeEx.getMimeType() + " at " + typeEx.getUrl());
            } catch (HttpStatusException statusException) {
                logger.error(statusException.getMessage() + " " + statusException.getStatusCode() + " at " + statusException.getUrl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    public void parseSite(Site site) {
        logger.info("parseSite:" + site.getUrl());
//        TODO expand parsing not only by getting links from first page

        //Nullify site

        site.setState(Site.STATE_CREATED);
        siteRepository.update(site);

        for (Page pageToDelete :
                pageRepository.getPagesBySiteId(site.getSiteId())) {
            pageRepository.delete(pageToDelete.getPageId());
        }

        //Parse base page

        Page basePage = makeParse(site.getUrl(), site);

        List<String> toParse = new ArrayList<>();
        addNewLink(site.getUrl(), toParse);
        addNewLinks(getValidLinks(site.getUrl(), parseLinks(basePage.getDocument())), toParse);
        List<String> parsed = new ArrayList<>();
        parsed.add(site.getUrl());

        //Parse by links

        for (String urlToParse : toParse) {
            if (!parsed.contains(urlToParse)) {
                Page parsingPage = makeParse(urlToParse, site);
                parsed.add(urlToParse);
                /* uncomment to parse all pages*/
//            addNewLinks(getValidLinks(site.getUrl(), parseLinks(parsingPage.getDocument())), toParse);
            }
        }
    }

    private Page makeParse(String urlToParse, Site site) {
        Document parsedDocument = parsePage(urlToParse);
        Page parsingPage = new Page();
        if (parsedDocument != null) {
            parsingPage.setTitle(parseTitle(parsedDocument));
            Cleaner cleaner = new Cleaner(Whitelist.basic());
            parsedDocument = cleaner.clean(parsedDocument);
            parsingPage.setSiteId(site.getSiteId());
            parsingPage.setUrl(urlToParse);
            parsingPage.setDocument(parsedDocument);
            pageRepository.create(parsingPage);
        }
        return parsingPage;
    }

    private void addNewLinks(List<String> from, List<String> to) {
        for (String link :
                from) {
            if (!to.contains(link)) {
                to.add(link);
            }
        }
    }

    private void addNewLink(String link, List<String> to) {
        if (!to.contains(link)) {
            to.add(link);
        }
    }

    @Override
    public List<String> parseLinks(Document page) {
        List<String> links = new ArrayList<>();
        Elements selected = page.select("a[href]");
        for (Element link : selected) {
            links.add(link.attr(LINK_ATTRIBUTE_KEY));
        }
        return links;
    }

    private List<String> getValidLinks(String baseUrl, List<String> urlList) {
        List<String> validLinks = new ArrayList<>();
        for (String url :
                urlList) {
            if (url.startsWith("/") || url.startsWith(baseUrl)) {
                validLinks.add(url);
            }
        }
        return validLinks;
    }

}
