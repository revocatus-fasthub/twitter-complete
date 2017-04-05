package tz.co.fasthub.ona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import tz.co.fasthub.ona.service.impl.MailContentBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
@Service
public class EmailService{

    private JavaMailSender javaMailSender;
    private MailContentBuilder mailContentBuilder;

    @Autowired
    public EmailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();

    public void sendMailWithInline(final String recipientName, final String recipientEmail, final String imageResourceName,
                                   final Locale locale)
            throws MessagingException {

    // Prepare the evaluation context
    final Context ctx = new Context();
            ctx.setVariable("name", recipientName);
            ctx.setVariable("subscriptionDate", new Date());
            ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
            ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

    // Prepare message using a Spring helper
    final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
    final MimeMessageHelper message =
            new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
            message.setSubject("Example HTML email with inline image");
            //    message.setFrom("devfasthub@gmail.com");
            message.setTo(recipientEmail);

    // Create the HTML body using Thymeleaf
    final String htmlContent = this.templateEngine.process("mailTemplate", ctx);
            message.setText(htmlContent, true); // true = isHtml

    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
   // final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
     //       message.addInline(imageResourceName, imageSource, imageContentType);

            // Send mail
            this.javaMailSender.send(mimeMessage);

    }

}