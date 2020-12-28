package com.peter.redditclonedemo.redditclonedemo.api.service;

import com.peter.redditclonedemo.redditclonedemo.api.exceptions.SpringRedditDemoException;
import com.peter.redditclonedemo.redditclonedemo.api.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
//creates instance of logger object and injects into class
@Slf4j
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    //Run this code asynchronously because hitting up external mail server is costly and takes time (>1000ms)
    //Running asynchronously allows user to get their response immediately and the email will have actually been sent by the
    //time they check for it
    @Async
    public void sendMail(NotificationEmail notificationEmail) throws SpringRedditDemoException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("peter@redditclone.demo");
            messageHelper.setTo(notificationEmail.getRecipientAddress());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            //logs possible because of @Slf4j
            log.info("Account activation email successfully sent.");
        } catch (MailException e) {
            log.info("Account activation email send failed.");
            throw new SpringRedditDemoException("Account activation email send failed.");
        }
    }
}
