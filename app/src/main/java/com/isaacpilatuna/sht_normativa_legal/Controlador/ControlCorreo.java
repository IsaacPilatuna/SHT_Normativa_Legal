package com.isaacpilatuna.sht_normativa_legal.Controlador;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ControlCorreo {
        final private String correo="normativa.sht@gmail.com";
        final private String password="LionSHT2019";



    public void enviarCorreo(String textoMensaje, Context context, Button btnEnviarComentario, EditText txtComentario){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Session sesion=null;
         Properties properties = new Properties();
        properties.put("mail.smtp.user", correo);
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        try{
            sesion=Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return  new PasswordAuthentication(correo,password);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context,"Autenticación de correo incorrecta",Toast.LENGTH_LONG).show();
            btnEnviarComentario.setEnabled(true);
            txtComentario.setEnabled(true);

        }

        if(sesion!=null){
            try {
                Message mensaje = new MimeMessage(sesion);
                mensaje.setFrom(new InternetAddress(correo));
                mensaje.setSubject("Comentario de aplicación");
                mensaje.setRecipients(Message.RecipientType.TO,InternetAddress.parse("isapila1996@gmail.com"));
                mensaje.setContent(textoMensaje,"text/html; charset=utf-8");
                Transport.send(mensaje);
                Toast.makeText(context,"Correo enviado exitosamente",Toast.LENGTH_LONG).show();
                btnEnviarComentario.setEnabled(true);
                txtComentario.setEnabled(true);

            } catch (MessagingException e) {

                Toast.makeText(context,"El correo no pudo ser enviado",Toast.LENGTH_LONG).show();
                btnEnviarComentario.setEnabled(true);
                txtComentario.setEnabled(true);
                e.printStackTrace();
            }
        }else{

            Toast.makeText(context,"Sesión nula",Toast.LENGTH_LONG).show();
            btnEnviarComentario.setEnabled(true);
            txtComentario.setEnabled(true);
        }
    }
}
