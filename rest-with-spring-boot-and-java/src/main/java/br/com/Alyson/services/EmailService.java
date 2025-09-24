package br.com.Alyson.services;

import br.com.Alyson.config.EmailConfig;
import br.com.Alyson.data.dto.request.EmailRequestDTO;
import br.com.Alyson.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EmailConfig emailConfigs;

    public void sendSimpleEmail( EmailRequestDTO emailRequest) {
        emailSender.to(emailRequest.getTo()).withSubject(emailRequest.getSubject()).withMessage(emailRequest.getSubject()).send(emailConfigs);
    }
}
