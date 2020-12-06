package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class RecuperacionClaveServicio {

    @Autowired
    private final UsuarioRepository usuarioRepositorio;

    private Session session;
    private  final Properties properties = new Properties();

    public Integer realizarRecuperacionClave (String correo) {
        Usuario usuarioactual = usuarioRepositorio.findByCorreo(correo);
        properties.put("mail.smtp.host", "SMTP.Office365.com");
        //properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port",587);
        properties.put("mail.smtp.mail.sender","javalimos2@outlook.com");
        properties.put("mail.smtp.user", "javalimos2@outlook.com");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            message.setSubject("Recuperacion de clave");
            message.setText("Su clave es: "+ usuarioactual.getClave());
            Transport t = (Transport) session.getTransport("smtp");
            t.connect((String)properties.get("mail.smtp.user"), "JavaIngesoft");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        }catch (MessagingException e){
            System.out.println(e);
        }
        return usuarioactual.getId();
    }
}