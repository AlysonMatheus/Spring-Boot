package br.com.Alyson.services;

import br.com.Alyson.config.EmailConfig;
import br.com.Alyson.data.dto.request.EmailRequestDTO;
import br.com.Alyson.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

// Indica que essa classe é um serviço do Spring (pode ser injetada em outros componentes)
@Service
public class EmailService {

    // Injeta a dependência do componente responsável por montar e enviar os emails
    @Autowired
    private EmailSender emailSender;

    // Injeta a configuração de email (host, porta, usuário, senha, etc.)
    @Autowired
    private EmailConfig emailConfigs;

    /**
     * Método responsável por enviar um email simples (sem anexos).
     * Recebe um objeto DTO com os dados do email.
     */
    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender
                // Define o destinatário do email
                .to(emailRequest.getTo())
                // Define o assunto do email
                .withSubject(emailRequest.getSubject())
                // Define a mensagem do email (aqui está usando o mesmo texto do assunto, talvez queira trocar por getMessage())
                .withMessage(emailRequest.getSubject())
                // Envia o email usando as configurações
                .send(emailConfigs);
    }

    /**
     * Método responsável por enviar um email com anexo.
     * Recebe os dados do email em JSON e um arquivo MultipartFile como anexo.
     */
    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
        File tempFile = null;
        try {
            // Converte o JSON recebido em um objeto EmailRequestDTO
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);

            // Cria um arquivo temporário para salvar o anexo
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());

            // Transfere o conteúdo do MultipartFile para o arquivo temporário
            attachment.transferTo(tempFile);

            // Monta e envia o email com o anexo
            emailSender
                    .to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getSubject())
                    // Anexa o arquivo ao email
                    .attach(tempFile.getAbsolutePath())
                    // Envia o email com as configurações
                    .send(emailConfigs);
        } catch (JsonProcessingException e) {
            // Tratamento de erro caso o JSON do request não consiga ser convertido
            throw new RuntimeException("Error parsing email request JSON", e);
        } catch (IOException e) {
            // Tratamento de erro caso haja problema ao processar o anexo
            throw new RuntimeException("Error processing the attachment ", e);
        } finally {
            // Garante que o arquivo temporário seja excluído após o envio
            if (tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }
}
