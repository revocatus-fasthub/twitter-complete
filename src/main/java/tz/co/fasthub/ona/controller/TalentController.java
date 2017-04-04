package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.service.TalentService;


/**
 * Created by root on 2/23/17.
 */
@Controller
@ComponentScan
public class TalentController {

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    @Autowired
    TalentService talentService;

    @Autowired
    private JavaMailSender javaMailSender;

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

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
            try{
                sendMail(talent.getEmail(),"ONA PLATFORM","Welcome to ONA");
            }catch (MailException e){
                log.error("email not sent: "+e);
            }
            redirectAttributes.addFlashAttribute("flash.message","Talent was successfully created");//=> Talent: "+talent
            return "redirect:/talents";
        }
    }

    private void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
    //    message.setFrom("publicizeus14@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @RequestMapping(value = "/talent/{talent_id}",method=RequestMethod.DELETE)
    public String deleteTalentById(@PathVariable("talent_id") Long talent_id, RedirectAttributes redirectAttributes){
        try {
            talentService.deleteTalentById(talent_id);
            redirectAttributes.addFlashAttribute("flash.message", "Talent Successfully deleted " + talent_id);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("flash.message", "Failed to delete Talent " + talent_id
                    + " => " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flash.message", "Uncaught Exception: " + e);
        }

        return "redirect:/talents";
    }


    @RequestMapping(value = "/talent/update/{talent_id}", method = RequestMethod.PATCH)
    public String update(@PathVariable("talent_id") Long talent_id, @RequestBody Talent talent, RedirectAttributes redirectAttributes){
        log.info("Updating Talent with id: {}", talent_id);
        Talent currentTalent = talentService.findById(talent_id);

        if (currentTalent == null) {
            log.error("Unable to update. Talent with id {} not found.", talent_id);
            redirectAttributes.addFlashAttribute("flash.message", "Unable to update. Talent with id " + talent_id + " not found.");
            return "redirect:/talents";
        }else{
            try {
           currentTalent.setFname(talent.getFname());
           currentTalent.setLname(talent.getLname());
           currentTalent.setEmail(talent.getEmail());
           talentService.updateTalent(talent);
           redirectAttributes.addFlashAttribute("flash.message","Talent was successfully updated => Talent: "+talent);

           }catch (Exception e){
               redirectAttributes.addFlashAttribute("flash.message","Talent not updated => Talent: "+talent);
               log.error("not updated: " +e);
           }
        }
        return "redirect:/talents";
    }


 /*

    @RequestMapping(value = "/talent/update", method = RequestMethod.POST)
    public String updateTalent(Long talent_id, Talent talent, String fName){
        try {
            talent = talentService.findOne(talent_id);
            talent.setFname(fName);

        }catch (Exception e){
            log.error(e.getMessage());
            return e.getMessage();
        }
    }



  @RequestMapping(value = "/talent/update", method = RequestMethod.GET)
    public String edit(@PathVariable Long talent_id, Model model){
        model.addAttribute("talent", talentService.getProductById(talent_id));
        return "talent/talentForm";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveProduct(Talent talent){

        talentService.saveTalent(talent);

        return "redirect:/talent/" + talent.getTalent_id();
    }



    @RequestMapping(method = RequestMethod.PUT)
    public String updateTalentById(Talent talent, RedirectAttributes redirectAttributes){
   //    talentService.findOne(talent);

        try {
            talentService.createTalent(talent);
            redirectAttributes.addFlashAttribute("flash.message", "Talent Successfully updated " + talent);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("flash.message", "Failed to delete Talent " + talent
                    + " => " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flash.message", "Uncaught Exception: " + e);
        }

        return "redirect:/talents";    }

  */
}