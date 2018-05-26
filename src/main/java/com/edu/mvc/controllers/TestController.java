package com.edu.mvc.controllers;

import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.comparing.ComparingService;
import com.edu.services.parsing.ParsingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    static final Logger logger = LogManager.getLogger(TestController.class);

    @RequestMapping(value = "/test")
    public ModelAndView test() {
        logger.info("test()");

        List<Object> differences = new ArrayList<>();
        List<Object> singles = new ArrayList<>();

        ModelAndView mav = new ModelAndView("/test/test");

        // TESTS



        // ENDTESTS

        mav.addObject("differences", differences);
        mav.addObject("singles", singles);

        return mav;
    }

}
