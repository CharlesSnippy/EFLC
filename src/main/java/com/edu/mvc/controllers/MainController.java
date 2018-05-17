package com.edu.mvc.controllers;

import com.edu.mvc.bean.EducationSite;
import com.edu.mvc.bean.EducationSitePage;
import com.edu.mvc.parsing.ParsingService;
import com.edu.mvc.parsing.ParsingServiceImpl;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    ParsingService parsingService = new ParsingServiceImpl();

    @RequestMapping(value = "/getSite", method = RequestMethod.GET)
    public ModelAndView getSite() {
        ModelAndView mav = new ModelAndView("/index");
        String weblink = "http://sch10spb.ru/";
        mav.addObject("collectDocument", parsingService.collectDocument(weblink));

        Document collectDocument = parsingService.collectDocument(weblink);

        EducationSite educationSite = new EducationSite();
        parsingService.getAllPages(weblink, educationSite);
        List<String> hrefs = new ArrayList<>();
        for (EducationSitePage page : educationSite.getEducationSitePages()) {
            hrefs.add(page.getUrl());
        }
        mav.addObject("hrefs", hrefs);
        mav.addObject("site", educationSite);
        List<Document> sitePages = new ArrayList<>();
        for (EducationSitePage page :
                educationSite.getEducationSitePages()) {
            sitePages.add(page.getPageDocument());

        }
        mav.addObject("sitePages", sitePages);
        return mav;
    }

}
