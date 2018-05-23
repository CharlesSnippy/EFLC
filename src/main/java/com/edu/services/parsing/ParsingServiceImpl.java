package com.edu.services.parsing;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingServiceImpl implements ParsingService {

    static final Logger logger = LogManager.getLogger(ParsingServiceImpl.class);

    private static String LINK_ATTRIBUTE_KEY = "abs:href";

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
        getAllPages(site.getUrl(), site);
    }

    @Override
    public List<DiffResult> getDifferences(List<String> a, List<String> b) {
        logger.info("getDifferences:");
        List<DiffResult> results = new ArrayList<>();
        int[][] m = prepareMatrix(a, b);
        getDiff(m, a, b, b.size(), a.size(), results);
        return results;
    }

    private void getAllPages(String url, Site site) {
        // TODO: check for content type
        Page newPage = new Page();
        newPage.setUrl(url);
        newPage.setDocument(parsePage(url));
        List<Page> pages = site.getPages();
        if(newPage.getDocument() != null) {
            pages.add(newPage);
        }
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

    private int[][] prepareMatrix(List<String> a, List<String> b) {
        int[][] m = new int[a.size() + 1][b.size() + 1];

        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                m[y][x] = 0;
            }
        }

        for (int y = 1; y < m.length; y++) {
            for (int x = 1; x < m[y].length; x++) {
                if (a.get(y - 1).equals(b.get(x - 1))) {
                    m[y][x] = 1 + m[y - 1][x - 1];
                } else {
                    m[y][x] = Math.max(m[y - 1][x], m[y][x - 1]);
                }
            }
        }
        return m;
    }

    private void getDiff(int[][] m, List<String> a, List<String> b, int x, int y, List<DiffResult> result) {
        if ((x > 0) && (y > 0) && (a.get(y - 1).equals(b.get(x - 1)))) {
            getDiff(m, a, b, x - 1, y - 1, result);
            DiffResult diffResult = new DiffResult();
            diffResult.setFirstIndex(String.valueOf(x));
            diffResult.setSecondIndex(String.valueOf(y));
            diffResult.setRow(a.get(y - 1));
            diffResult.setType(TYPE_EQUAL);
            result.add(diffResult);
        } else {
            if ((x > 0) && ((y == 0) || (m[y][x - 1] >= m[y - 1][x]))) {
                getDiff(m, a, b, x - 1, y, result);
                DiffResult diffResult = new DiffResult();
                diffResult.setFirstIndex(String.valueOf(x));
                diffResult.setSecondIndex("");
                diffResult.setRow(b.get(x - 1));
                diffResult.setType(TYPE_ADD);
                result.add(diffResult);
            } else {
                if ((y > 0) && ((x == 0) || m[y][x - 1] < m[y - 1][x])) {
                    getDiff(m, a, b, x, y - 1, result);
                    DiffResult diffResult = new DiffResult();
                    diffResult.setFirstIndex("");
                    diffResult.setSecondIndex(String.valueOf(y));
                    diffResult.setRow(a.get(y - 1));
                    diffResult.setType(TYPE_DELETE);
                    result.add(diffResult);
                }
            }
        }
    }
}
