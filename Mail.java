import java.util.*;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import java.io.File;


/**
 * @author Pablo Rodriguez
 * @version 1.12.2020
 */
public class Mail
{
    private String usuario, password;
    private Session session;

    /**
     * Constructor for objects of class Mail
     */
    public Mail()
    {
       //Si es un correo de Gmail, hay que activar la opción de aplicaciones poco seguras
        usuario = "your_email";
       password =  "your_password";
       
       //Propiedades de servidor
       Properties props = new Properties();
       props.put("mail.smtp.host", "smtp.gmail.com");
       props.put("mail.smtp.user", usuario);
       props.put("mail.smtp.password", password);
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.starttls.enable", "true");       
       props.put("mail.smtp.port", "587");
       
       //Autenticacion con el servidor
       Authenticator auth = new Authenticator()
       {
          protected PasswordAuthentication getPasswordAuthentication()
          {
            return new PasswordAuthentication(usuario, password);
          } 
       };
       
       //Inicio de sesion
       session = Session.getInstance(props, auth);
    }
    
    /**
     * Metodo de envio de correo
     */
    public boolean enviarMail (String destinatario)
    {
        boolean resultado = false;
        if (destinatario != "")
        {
            try 
            {
                //Creacion de correo
                Message mensaje = new MimeMessage(session);
                mensaje.setFrom(new InternetAddress(usuario));
                mensaje.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destinatario));
                mensaje.setSubject("Reporte de Entrada - Salida");
            
                //Cuerpo del correo
                BodyPart texto = new MimeBodyPart();
                texto.setText("Sirvase encontrar adjunto el reporte de marcas.\n\nSaludos coordiales!");
                MimeMultipart multiParte = new MimeMultipart();
                BodyPart adjunto = new MimeBodyPart();
                //Obtener ruta del archivo
                File doc = new File(System.getProperty("user.home", "Documents"));
                String path = doc.getPath() + "\\Documents\\Reporte Marcas - SINAC.pdf";
                DataSource ruta = new FileDataSource(path); //Ruta de archivo adjunto, reporte
                adjunto.setDataHandler(new DataHandler(ruta));
                adjunto.setFileName("Reporte Marcas Sinac.pdf");
            
                //Anadir adjunto y mensaje al correo
                multiParte.addBodyPart(adjunto);
                multiParte.addBodyPart(texto);
                mensaje.setContent(multiParte);
            
                //Envio de correo
                Transport.send(mensaje);
                resultado = true;
            }
            catch (MessagingException e)
            {
                throw new RuntimeException(e);
            }
        }   
        return resultado;
    }
}