package com.edu.mvc.controllers;

import com.edu.repositories.SiteRepository;
import com.edu.services.parsing.ParsingService;
import com.edu.services.parsing.ParsingServiceImpl;
import com.edu.mvc.models.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteController {

    @Autowired
    SiteRepository siteRepository;

    ParsingService parsingService = new ParsingServiceImpl();

    @RequestMapping(value = "/site/all")
    public ModelAndView siteAll() {
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("allSites", siteRepository.getAll());
        return mav;
    }

    @RequestMapping(value = "/site/create/confirm")
    public ModelAndView siteCreationConfirm(@ModelAttribute("newSite") Site newSite) {
        //TODO entered data validation
        ModelAndView mav = new ModelAndView("redirect:/");
        siteRepository.create(newSite);
        return mav;
    }

    @RequestMapping(value = "/site/create", method = RequestMethod.GET)
    public ModelAndView getSite() {
        return new ModelAndView("/site/create", "newSite", new Site());
    }

    @RequestMapping(value = "/site/delete/{siteId}", method = RequestMethod.GET)
    public ModelAndView siteDelete(@PathVariable("siteId") int siteId) {
        siteRepository.delete(siteId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/site/parse/{siteId}", method = RequestMethod.GET)
    public ModelAndView siteParse(@PathVariable("siteId") int siteId) {
        Site site = siteRepository.getById(siteId);
        parsingService.parseSite(site);
        return new ModelAndView("redirect:/");
    }

}
