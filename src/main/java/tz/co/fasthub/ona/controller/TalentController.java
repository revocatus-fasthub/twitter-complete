package tz.co.fasthub.ona.controller;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.service.TalentService;
import tz.co.fasthub.ona.service.impl.MailContentBuilder;

import javax.mail.MessagingException;
import java.util.Locale;


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

    @Autowired
    private MailContentBuilder mailContentBuilder;

    Locale locale;
    private MailSender mailSender;
    // private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    //  public void setTemplateMessage(SimpleMailMessage templateMessage) {
    //  this.templateMessage = templateMessage;
    // }

    @RequestMapping(value = "/talents")
    public String showUsers(Model model, Pageable pageable) {
        final Page<Talent> page = talentService.findTalentPage(pageable);
        model.addAttribute("page", page);
        return "/talent/listtalents";
    }

    @RequestMapping(value = "/talent/addTalent", method = RequestMethod.POST)
    public String registration(@ModelAttribute("addTalentForm") Talent talent, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws MessagingException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.message", "Talent was Not Created  => error details: " + bindingResult.getFieldError().toString());
            return "redirect:/talents";
        } else {
            talentService.createTalent(talent);
            sendMail(talent.getEmail(), "Welcome to ONA Platform", "ONA-Social Media Management Tool");
            redirectAttributes.addFlashAttribute("flash.message", "Successful!!!");//=> Talent: "+talent
            return "redirect:/talents";
        }
    }
    private void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @RequestMapping(value = "/talent/{talent_id}", method = RequestMethod.DELETE)
    public String deleteTalentById(@PathVariable("talent_id") Long talent_id, RedirectAttributes redirectAttributes) {
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

    @RequestMapping(value = "/showTalent/{talent_id}", method = RequestMethod.GET)
    public String showTalent(@PathVariable("talent_id") Long talent_id, Talent talent, Model model,RedirectAttributes redirectAttributes){
        Talent currentTalent = talentService.findById(talent.getTalent_id());
        if (currentTalent == null) {
            log.error("Unable to update. Talent with id {} not found.", talent_id);
            redirectAttributes.addFlashAttribute("flash.message", "Unable to update. Talent with id " + talent_id + " not found.");
            return "/talent/talentForm";
        }else {
            model.addAttribute("talent", talentService.getTalentbyId(talent_id));
        }
        return "talent/talentForm";
    }
}