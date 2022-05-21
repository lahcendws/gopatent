package com.wildcodeschool.patent.service;

import com.wildcodeschool.patent.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {

    @Autowired
    private Environment env;

    @Autowired
    private FileReaderService mailMessageResponse;

    @Value("${path.for.mail.message}")
    private String pathForMailMessage;

    @Value("${front.server.name}")
    private String frontServerName;

    @Value("${front.server.port}")
    private String getFrontServerPort;



    /**
     * construct mail with parameters
     * @param subject
     * @param body
     * @param user
     * @return
     */
    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    /**
     * construct mail with the reset token
     * @param token
     * @param user
     * @return
     */
    public SimpleMailMessage constructResetTokenEmail(String token, User user) throws Exception {
        String url = "http://" + frontServerName + ":" + getFrontServerPort + "/update-password?token=" + token;
        String message = mailMessageResponse.readFile(pathForMailMessage);
        return constructEmail("Modifier le mot de passe", message + " \r\n" + url, user);
    }
}
