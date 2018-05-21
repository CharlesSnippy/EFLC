package com.edu.mvc.controllers;

import com.edu.repositories.SiteRepository;
import com.edu.mvc.models.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    SiteRepository siteRepository;

    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/index");
        List<Site> sites = siteRepository.getAll();
        mav.addObject("allSites", sites);
        return mav;
    }


}
