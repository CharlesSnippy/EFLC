package com.edu.mvc.controllers;

import com.edu.mvc.models.Page;
import com.edu.mvc.models.Site;
import com.edu.repositories.PageRepository;
import com.edu.repositories.SiteRepository;
import com.edu.services.parsing.ParsingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SiteController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ParsingService parsingService;

    static final Logger logger = LogManager.getLogger(SiteController.class);

    @RequestMapping(value = "/site/all")
    public ModelAndView siteAll() {
        logger.info("siteAll()");
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("allSites", siteRepository.getAll());
        return mav;
    }

    @RequestMapping(value = "/site/create/confirm")
    public ModelAndView siteCreationConfirm(@ModelAttribute("newSite") Site newSite) {
        logger.info("siteCreationConfirm({})", newSite);
        //TODO entered data validation
        ModelAndView mav = new ModelAndView("redirect:/");
        newSite.setState(Site.STATE_CREATED);
        siteRepository.create(newSite);
        return mav;
    }

    @RequestMapping(value = "/site/create", method = RequestMethod.GET)
    public ModelAndView getSite() {
        logger.info("getSite()");
        return new ModelAndView("/site/create", "newSite", new Site());
    }

    @RequestMapping(value = "/site/delete/{siteId}", method = RequestMethod.GET)
    public ModelAndView siteDelete(@PathVariable("siteId") int siteId) {
        logger.info("siteDelete({})", siteId);
        List<Page> pages = pageRepository.getPagesBySiteId(siteId);
        for (Page page :
                pages) {
            pageRepository.delete(page.getPageId());
        }
        siteRepository.delete(siteId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/site/parse/{siteId}", method = RequestMethod.GET)
    public ModelAndView siteParse(@PathVariable("siteId") int siteId) {
        logger.info("siteParse({})", siteId);
        Site site = siteRepository.getById(siteId);
        parsingService.parseSite(site);
        site.setState(Site.STATE_PARSED);
        siteRepository.update(site);
        return new ModelAndView("redirect:/site/" + siteId);
    }

    @RequestMapping(value = "/site/{siteId}", method = RequestMethod.GET)
    public ModelAndView siteView(@PathVariable("siteId") int siteId) {
        logger.info("siteView({})", siteId);
        ModelAndView mav = new ModelAndView("site/view");
        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        mav.addObject("site", site);
        return mav;
    }

    @RequestMapping(value = "/site/cut/{siteId}", method = RequestMethod.GET)
    public ModelAndView siteCut(@PathVariable("siteId") int siteId) {
        logger.info("siteCut({})", siteId);
        ModelAndView mav = new ModelAndView("/site/cut");
        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        mav.addObject("site", site);
        return mav;
    }

    @RequestMapping(value = "/site/cut/{siteId}/{pageId}", method = RequestMethod.GET)
    public ModelAndView siteCutByPageId(@PathVariable("siteId") int siteId, @PathVariable("pageId") int pageId) {
        logger.info("siteCutByPageId({})", siteId);
        ModelAndView mav = new ModelAndView("redirect:/site/" + siteId);
        Site site = siteRepository.getById(siteId);
        site.setPages(pageRepository.getPagesBySiteId(siteId));
        Page source = pageRepository.getById(pageId);
        parsingService.cutPages(site, source);
        return mav;
    }

}
