package com.btcc.wsm.util;

import com.btcc.wsm.constant.MailConstant;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by siva on 24/10/2016.
 */
public class EmailUtil {


    final static Logger logger =  LogManager.getLogger(EmailUtil.class);

    public static void sendEmail(HashMap<String,String> emailMap) throws Exception {

        logger.info("EmailUtil :sendEmail  Started"+emailMap);

        InputStream inputStream = EmailUtil.class.getClassLoader().getResourceAsStream("EmailTemplate.html");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;

        StringBuilder responseData = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                responseData.append(line);
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        // load your HTML email template
        String htmlEmailTemplate = responseData.toString();



        // replacing the transaction details in HTML
        htmlEmailTemplate = htmlEmailTemplate.replace("##amount##",emailMap.get(MailConstant.AMOUNT));
        htmlEmailTemplate = htmlEmailTemplate.replace("##workshopname##",emailMap.get(MailConstant.AMOUNT));
        htmlEmailTemplate = htmlEmailTemplate.replace("##dateTime##",emailMap.get(MailConstant.DATETIME));


        String host = emailMap.get(MailConstant.HOSTNAME);
        String from = emailMap.get(MailConstant.FROMEMAIL);
        String to = emailMap.get(MailConstant.TOEMAIL);
        String port = emailMap.get(MailConstant.PORT);
        String protocol = emailMap.get(MailConstant.PROTOCOL);

        HtmlEmail email = new HtmlEmail();

        email.setHtmlMsg(htmlEmailTemplate);
        email.setSubject(MailConstant.SUBJECT);
        email.setHostName(host);
        email.setSmtpPort(Integer.parseInt(port));
        email.addTo(to);
        email.setFrom(from);
        email.send();
        logger.info("Email Sent Successfully to "+emailMap.get(MailConstant.TOEMAIL));
        logger.info("Exit From EmailServiceImpl.sendEmail");
    }

}
