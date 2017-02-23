package tz.co.fasthub.ona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.service.TalentService;


/**
 * Created by root on 2/23/17.
 */
public class TalentController {

    @Autowired
    TalentService talentService;

    @RequestMapping(value = "/talents")
    public String showUsers(Model model, Pageable pageable) {
        final Page<Talent> page = talentService.findTalentPage(pageable);
        model.addAttribute("page", page);
        return "/talent/listtalents";
    }

    @RequestMapping(value = "/talent/addTalent", method = RequestMethod.POST)
    public String registration(@ModelAttribute("addTalentForm") Talent talent, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.message","Talent was Not Created  => error details: "+bindingResult.getFieldError().toString());
            return  "redirect:/talents";
        }else {
            talentService.createTalent(talent);
            redirectAttributes.addFlashAttribute("flash.message","Talent was successfully created => Talent: "+talent);

            return "redirect:/talents";
        }


    }

}
