package com.edu.mvc.controllers;

import com.edu.mvc.models.Page;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
import com.edu.services.parsing.ParsingService;
import com.edu.services.parsing.ParsingServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class TestController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ParsingService parsingService;

    @Autowired
    ComparingService comparingService;

    @RequestMapping(value = "/test")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView("/test/test");

        String url = "http://sch10spb.ru/";
        mav.addObject("url", url);

        Document pageCode = parsingService.parsePage(url);
        mav.addObject("pageCode", pageCode);

        Cleaner cleaner = new Cleaner(Whitelist.basic());
        Document pageCodeCleanup = cleaner.clean(pageCode);
//        Document pageCodeCleanup = parsingService.parsePage(url);
//        parsingService.cleanupPage(pageCodeCleanup, parsingService.ELEMENTS_BLACKLIST, false);
        mav.addObject("pageCodeCleanup", pageCodeCleanup);

        String title = parsingService.parseTitle(pageCode);
        mav.addObject("title", title);

        Page page = new Page();
        page.setDocument(parsingService.parsePage(url));
        page.setTitle(parsingService.parseTitle(page.getDocument()));
        page.setSiteId(1);
        pageRepository.create(page);
        page = pageRepository.getById(page.getPageId());
        mav.addObject("titleParsed", page.getTitle());

//        pageCodeCleanup.getAllElements()
//        mav.addObject("textOnly",);

        mav.addObject("compareResult", comparingService.getDifferences(
                Arrays.asList(pageCode.outerHtml().split("\n")),
                Arrays.asList(pageCodeCleanup.outerHtml().split("\n"))
        ));

        return mav;
    }

}
