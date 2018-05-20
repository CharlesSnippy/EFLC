package com.edu.mvc.controllers;

import com.edu.mvc.models.Page;
import com.edu.repositories.SiteRepository;
import com.edu.mvc.models.Site;
import com.edu.services.parsing.ParsingService;
import com.edu.services.parsing.ParsingServiceImpl;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    SiteRepository siteRepository;

    ParsingService parsingService = new ParsingServiceImpl();

    @RequestMapping(value = "/getSite2", method = RequestMethod.GET)
    public ModelAndView getSite2() {
        ModelAndView mav = new ModelAndView("/index");
        String weblink = "http://sch10spb.ru/";
        mav.addObject("collectDocument", parsingService.parsePage(weblink));

        Document collectDocument = parsingService.parsePage(weblink);

        Site site = new Site();
        site.setUrl(weblink);
        parsingService.parseSite(site);
        List<String> hrefs = new ArrayList<>();
        for (Page page : site.getPages()) {
            hrefs.add(page.getUrl());
        }
        mav.addObject("hrefs", hrefs);
        mav.addObject("site", site);
        List<Document> sitePages = new ArrayList<>();
        for (Page page :
                site.getPages()) {
            sitePages.add(page.getDocument());

        }
        mav.addObject("sitePages", sitePages);
        return mav;
    }

    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("allSites", siteRepository.getAll());
        return mav;
    }


}
