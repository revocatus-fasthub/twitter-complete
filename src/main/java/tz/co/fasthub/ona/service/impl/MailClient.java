package tz.co.fasthub.ona.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.controller.TwitterController;
import tz.co.fasthub.ona.domain.Talent;

/**
 * Created by root on 3/27/17.
 */
@Service
public class MailClient {

    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    private Talent talent;

    @Autowired
    public MailClient(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void prepareAndSend() {
        //TODO implement
        String recipient = "carollacharles@gmail.com";
        String message = "welcome to ONA";
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("publicizeus14@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Sample ONA mail subject");
            messageHelper.setText(message);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            log.error("Uncaught Exception: " + e);
        }

    }

}
