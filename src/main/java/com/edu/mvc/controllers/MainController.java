package com.edu.mvc.controllers;

import com.edu.mvc.models.Criterion;
import com.edu.repositories.CriterionRepository;
import com.edu.repositories.SiteRepository;
import com.edu.mvc.models.Site;
import com.edu.services.criteriaCheck.CriteriaCheckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    CriterionRepository criterionRepository;

    static final Logger logger = LogManager.getLogger(MainController.class);

    @RequestMapping(value = "/")
    public ModelAndView index() {
        logger.info("index()");
        ModelAndView mav = new ModelAndView("/index");
        List<Site> sites = siteRepository.getAll();
        mav.addObject("allSites", sites);
        return mav;
    }

    @RequestMapping(value = "/criteria")
    public ModelAndView criteriaIndex() {
        logger.info("criteriaIndex()");
        ModelAndView mav = new ModelAndView("/criterion/view");
        List<Criterion> criteria = criterionRepository.getAll();
        mav.addObject("criteria", criteria);
        return mav;
    }


}
