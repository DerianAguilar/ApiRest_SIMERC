package com.fesc.SIMERC.Services.Impl;

import com.fesc.SIMERC.Entities.Usuario;
import com.fesc.SIMERC.Repositories.UsuarioRepository;
import com.fesc.SIMERC.Services.EmailService;
import com.fesc.SIMERC.Shared.EnviarEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void enviarEmail(EnviarEmailDTO emailDTO) {

        Usuario user = usuarioRepository.findByEmail(emailDTO.getCorreo());


        try {
            // Configuración de propiedades para el envío de correo
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Datos de autenticación
            final String username = user.getEmail();
            final String password = user.getPasswordEmail();

            // Creación de la sesión de correo
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(username, password);
                }
            });

            // Creación del mensaje
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(username));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDTO.getEmail()));
            mensaje.setSubject(emailDTO.getAsunto());
            mensaje.setText(emailDTO.getDescripcion());

            // Envío del correo electrónico
            Transport.send(mensaje);

            System.out.println("Correo electrónico enviado correctamente.");
        } catch (Exception e) {
            // Manejar cualquier excepción relacionada con el envío del correo electrónico
            e.printStackTrace();
        }

    }


}
