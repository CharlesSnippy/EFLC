package com.edu.mvc.controllers;

import com.edu.mvc.models.Page;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
import com.edu.services.criteriaCheck.Criteria;
import com.edu.services.criteriaCheck.CriteriaCheckService;
import com.edu.services.parsing.DiffResult;
import com.edu.services.parsing.ParsingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    CriteriaCheckService criteriaCheckService;

    static final Logger logger = LogManager.getLogger(TestController.class);

    @RequestMapping(value = "/test")
    public ModelAndView test() {
        logger.info("test()");

        List<Object> differences = new ArrayList<>();
        List<Object> differencesPos = new ArrayList<>();
        List<Object> differencesNeg = new ArrayList<>();
        List<Object> differencesEq = new ArrayList<>();
        List<Object> singles = new ArrayList<>();

        ModelAndView mav = new ModelAndView("/test/test");

        // TESTS

        String url = "http://sch10spb.ru/sveden/common/";
        String urlBase = "http://sch10spb.ru/";
        singles.add(url);

        Document doc = parsingService.parsePage(url);
        Document docBase = parsingService.parsePage(urlBase);
        singles.add(doc);
        singles.add(docBase);

        Document parsed = comparingService.getDocumentFromDiff(comparingService.getDifferences(doc, docBase), comparingService.TYPE_EQUAL);
        singles.add(parsed);
        Document parsedAdd = comparingService.getDocumentFromDiff(comparingService.getDifferences(doc, docBase), comparingService.TYPE_ADD);
        singles.add(parsedAdd);
        Document parsedNeg = comparingService.getDocumentFromDiff(comparingService.getDifferences(doc, docBase), comparingService.TYPE_DELETE);
        singles.add(parsedNeg);

        differences.add(comparingService.getDifferences(doc, doc));
        differences.add(comparingService.getDifferences(docBase, docBase));
        differences.add(comparingService.getDifferences(doc, docBase));
        differencesEq.add(comparingService.getDifferences(doc, docBase));
        differencesPos.add(comparingService.getDifferences(doc, docBase));
        differencesNeg.add(comparingService.getDifferences(doc, docBase));
//        criteriaCheckService.checkBlockByCriterion(new ArrayList<>(), Criteria.C1);



        // ENDTESTS

        mav.addObject("differences", differences);
        mav.addObject("differencesPos", differencesPos);
        mav.addObject("differencesNeg", differencesNeg);
        mav.addObject("differencesEq", differencesEq);
        mav.addObject("singles", singles);

        return mav;
    }

}
