package com.edu.services.parsing;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParsingServiceImpl implements ParsingService {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ComparingService comparingService;

    static final Logger logger = LogManager.getLogger(ParsingServiceImpl.class);
    private static String LINK_ATTRIBUTE_KEY = "abs:href";

    @Override
    public String parseTitle(Document page) {
        return page.selectFirst("title") != null ? page.selectFirst("title").text() : page.location();
    }

    @Override
    public Document parsePage(String url) {
        logger.info("parsePage({})", url);
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
        logger.info("parseSite({})", site.getUrl());
//        TODO expand parsing not only by getting links from first page

        //Nullify site

        site.setState(Site.STATE_CREATED);
        siteRepository.update(site);

        for (Page pageToDelete :
                pageRepository.getPagesBySiteId(site.getSiteId())) {
            pageRepository.delete(pageToDelete.getPageId());
        }

        //Parse base page

        Page mainPage = makeParse(site.getUrl(), site);

        List<String> toParse = new ArrayList<>();
        addNewLink(site.getUrl(), toParse);
        addNewLinks(getValidLinks(site.getUrl(), parseLinks(mainPage.getDocument())), toParse);
        List<String> parsed = new ArrayList<>();
        parsed.add(site.getUrl());

        //Parse by links

        for (String urlToParse : toParse) {
            if (!parsed.contains(urlToParse)) {
                Page parsingPage = makeParse(urlToParse, site);
//                if(parsingPage != null) {
//                    List<DiffResult> differences = comparingService.getDifferences(basePage.getDocument(), parsingPage.getDocument());
//                    parsingPage.setDocument(comparingService.getDocumentFromDiff(differences, comparingService.TYPE_ADD));
//                    pageRepository.update(parsingPage);
//                    /* uncomment to parse all pages*/
////                    addNewLinks(getValidLinks(site.getUrl(), parseLinks(parsingPage.getDocument())), toParse);
//                }
                parsed.add(urlToParse);
            }
        }
//        Page basePage = mainPage;
//        float maxEqualIndex = 0;
//        List<Page> pages = pageRepository.getPagesBySiteId(site.getSiteId());
//        for (Page pretender : pages) {
//            float count = 0;
//            for (Page matching : pages) {
//                List<DiffResult> diffs = comparingService.getDifferences(pretender.getDocument(), matching.getDocument());
//                for (DiffResult diff : diffs) {
//                    if(diff.getType() == comparingService.TYPE_EQUAL) {
//                        count++;
//                    }
//                }
//            }
//            count = count/pages.size();
//            if(maxEqualIndex < count) {
//                maxEqualIndex = count;
//                basePage = pretender;
//            }
//            logger.debug("pretender={}, count={}, maxEqual={}, basePage={}", pretender.getPageId(), count, maxEqualIndex, basePage.getPageId());
//        }
//        for(Page pageToCut: pages) {
//            List<DiffResult> differences = comparingService.getDifferences(basePage.getDocument(), pageToCut.getDocument());
//            pageToCut.setDocument(comparingService.getDocumentFromDiff(differences, comparingService.TYPE_ADD));
//            pageRepository.update(pageToCut);
//        }

//        mainPage.setDocument(comparingService.getDocumentFromDiff(differences, comparingService.TYPE_ADD));
//        pageRepository.update(basePage);
    }

    @Override
    public List<String> parseLinks(Document page) {
        logger.info("parseLinks({})", page.baseUri());
        List<String> links = new ArrayList<>();
        Elements selected = page.select("a[href]");
        for (Element link : selected) {
            links.add(link.attr(LINK_ATTRIBUTE_KEY));
        }
        return links;
    }

    @Override
    public void cutPages(Site site, Page source) {
        List<Page> sitePages = site.getPages();
        for (Page page :
                sitePages) {
            logger.info("cutPages() {} {}",source.getUrl(), page.getUrl());
            page.setCutDocument(
                    comparingService.getDocumentFromDiff(
                            comparingService.getDifferences(
                                    source.getDocument(),
                                    page.getDocument()
                            ), comparingService.TYPE_ADD
                    )
            );
            pageRepository.update(page);
        }
        site.setState(Site.STATE_CUT_DONE);
        siteRepository.update(site);
    }

    /*
        supporting methods
     */

    /**
     * Filters links list by links from base url domain
     *
     * @param baseUrl source url
     * @param urlList collection of links
     * @return filtered list of links
     */
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

    /**
     * Tries to parse page and saves it on success
     *
     * @param urlToParse source to parse
     * @param site       parent site
     * @return new page, saved in DB
     */
    private Page makeParse(String urlToParse, Site site) {
        Document parsedDocument = parsePage(urlToParse);
        Page parsingPage = null;
        if (parsedDocument != null) {
            parsingPage = new Page();
            parsingPage.setTitle(parseTitle(parsedDocument));
            parsingPage.setSiteId(site.getSiteId());
            parsingPage.setUrl(urlToParse);
            Elements restricted = parsedDocument.select("script, meta, link, style");
            for (Element removingElement :
                    restricted) {
                removingElement.remove();
            }
            parsingPage.setDocument(parsedDocument);
            pageRepository.create(parsingPage);
        }
        return parsingPage;
    }

    /**
     * Adds links from one list to another, if last does not contain it
     *
     * @param from source list
     * @param to   destination list
     */
    private void addNewLinks(List<String> from, List<String> to) {
        for (String link :
                from) {
            if (!to.contains(link)) {
                to.add(link);
            }
        }
    }

    /**
     * Adds link to list, if last does not contain it
     *
     * @param link link
     * @param to   links list
     */
    private void addNewLink(String link, List<String> to) {
        if (!to.contains(link)) {
            to.add(link);
        }
    }

}
