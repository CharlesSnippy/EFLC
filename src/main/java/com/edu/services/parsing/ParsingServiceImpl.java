package com.edu.services.parsing;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ParsingServiceImpl implements ParsingService {

    private static String LINK_ATTRIBUTE_KEY = "abs:href";

    @Override
    public Document parsePage(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    public void parseSite(Site site) {
        getAllPages(site.getUrl(), site);
    }

    private void getAllPages(String url, Site site) {
        // TODO: check for content type
        Page newPage = new Page();
        newPage.setUrl(url);
        newPage.setDocument(parsePage(url));
        List<Page> pages = site.getPages();
        pages.add(newPage);
        site.setPages(pages);

        if (newPage.getDocument() != null) {
            Elements links = newPage.getDocument().select("a[href]");
            for (Element link : links) {
                if (link.hasAttr(LINK_ATTRIBUTE_KEY) && (link.attr(LINK_ATTRIBUTE_KEY).startsWith("/") || link.attr(LINK_ATTRIBUTE_KEY).startsWith(newPage.getDocument().baseUri()))) {
                    boolean addLink = true;
                    for (Page page : pages) {
                        if (page.getUrl().equals(link.attr(LINK_ATTRIBUTE_KEY))) {
                            addLink = false;
                            break;
                        }
                    }
                    if (addLink) {
                        getAllPages(link.attr(LINK_ATTRIBUTE_KEY), site);
                    }
                }
            }
        }
    }
}
