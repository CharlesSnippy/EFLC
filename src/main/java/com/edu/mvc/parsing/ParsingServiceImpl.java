package com.edu.mvc.parsing;

import com.edu.mvc.bean.EducationSite;
import com.edu.mvc.bean.EducationSitePage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ParsingServiceImpl implements ParsingService {

    static String LINK_ATTRIBUTE_KEY = "abs:href";
    @Override
    public Document collectDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public void getAllPages(String url, EducationSite educationSite) {
        // TODO: check for content type
        EducationSitePage newPage = new EducationSitePage(url, collectDocument(url));
        List<EducationSitePage> educationSitePages = educationSite.getEducationSitePages();
        educationSitePages.add(newPage);
        educationSite.setEducationSitePages(educationSitePages);

        if (newPage.getPageDocument() != null) {
            Elements links = newPage.getPageDocument().select("a[href]");
            for (Element link : links) {
                if (link.hasAttr(LINK_ATTRIBUTE_KEY) && (link.attr(LINK_ATTRIBUTE_KEY).startsWith("/") || link.attr(LINK_ATTRIBUTE_KEY).startsWith(newPage.getPageDocument().baseUri()))) {
                    boolean addLink = true;
                    for (EducationSitePage educationSitePage : educationSitePages) {
                        if (educationSitePage.getUrl().equals(link.attr(LINK_ATTRIBUTE_KEY))) {
                            addLink = false;
                            break;
                        }
                    }
                    if (addLink) {
                        getAllPages(link.attr(LINK_ATTRIBUTE_KEY), educationSite);
                    }
                }
            }
        }
    }
}
