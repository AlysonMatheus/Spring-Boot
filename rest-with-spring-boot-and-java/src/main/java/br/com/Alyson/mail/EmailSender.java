package br.com.Alyson.mail;

import br.com.Alyson.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Component // Define a classe como um componente gerenciado pelo Spring
public class EmailSender implements Serializable { // Permite serialização do objeto (caso seja necessário em cache, fila etc.)
    Logger logger = LoggerFactory.getLogger(EmailSender.class); // Logger para registrar informações e erros

    // Dependências e atributos da classe
    private final JavaMailSender mailSender; // Serviço de envio de e-mails do Spring
    private String to; // String contendo os destinatários originais (separados por ;)
    private String subject; // Assunto do e-mail
    private String body; // Corpo da mensagem
    private ArrayList<InternetAddress> recipients = new ArrayList<>(); // Lista de destinatários formatada
    private File attachment; // Arquivo de anexo, se houver

    // Construtor que recebe o JavaMailSender injetado pelo Spring
    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Define os destinatários do e-mail (pode ser vários separados por ;)
    public EmailSender to(String to) {
        this.to = to;
        this.recipients = getRecipients(to); // Converte String em lista de InternetAddress
        return this; // Retorna o próprio objeto (padrão builder)
    }

    // Define o assunto do e-mail
    public EmailSender withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    // Define o corpo da mensagem (suporta HTML)
    public EmailSender withMessage(String body) {
        this.body = body;
        return  this;
    }

    // Adiciona um arquivo em anexo ao e-mail
    public EmailSender Attach(String fileDir) {
        this.attachment = new File(fileDir); // Cria o objeto File a partir do caminho recebido
        return this;
    }

    // Método responsável por enviar o e-mail
    public void send(EmailConfig config) {
        MimeMessage message = mailSender.createMimeMessage(); // Cria um objeto MimeMessage (suporta texto/anexos/HTML)
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = suporte a anexo
            helper.setFrom(config.getUsername()); // Define remetente (vem da configuração do sistema)
            helper.setTo(recipients.toArray(new InternetAddress[0])); // Converte lista de destinatários em array
            helper.setSubject(subject); // Define o assunto
            helper.setText(body, true); // Define corpo da mensagem (true = permite HTML)

            // Caso exista anexo, adiciona ao e-mail
            if(attachment != null){
                helper.addAttachment(attachment.getName(),attachment);
            }

            mailSender.send(message); // Envia o e-mail efetivamente

            // Loga a ação (⚠ aqui o %s não vai funcionar corretamente com logger, deveria ser String.format)
            logger.info("email sent to %s with the subject '%s'%n",to,subject);

            reset(); // Limpa os atributos para evitar reuso indevido
        } catch (MessagingException e) {
            // Se algo der errado, lança exceção em tempo de execução
            throw new RuntimeException("Error sending the email",e);
        }
    }

    // Reseta os atributos após envio
    private void reset() {
        this.to = null;
        this.subject = null;
        this.body = null;
        this.recipients = null; // ⚠ Melhor seria new ArrayList<>(), pois null pode gerar NullPointerException
        this.attachment = null;
    }

    // Converte a String de destinatários em uma lista de InternetAddress
    // Exemplo de entrada: "email@gmail.com; email2@gmail.com; email3@gmail.com;"
    private ArrayList<InternetAddress> getRecipients(String to) {
        String toWithoutSpaces = to.replaceAll("\\s", ""); // Remove espaços em branco
        StringTokenizer tok = new StringTokenizer(toWithoutSpaces, ";"); // Divide por ";"
        ArrayList<InternetAddress> recipientsList = new ArrayList<>();
        while (tok.hasMoreElements()) {
            try {
                recipientsList.add(new InternetAddress(tok.nextElement().toString())); // Cria InternetAddress para cada e-mail
            } catch (AddressException e) {
                throw new RuntimeException(e); // Se e-mail for inválido, lança exceção
            }
        }
        return recipientsList; // Retorna a lista de destinatários
    }
}
