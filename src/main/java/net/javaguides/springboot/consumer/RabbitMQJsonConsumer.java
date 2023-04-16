package net.javaguides.springboot.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.dto.User;
import net.javaguides.springboot.enumuration.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(User user) {
        LOGGER.info(String.format("Received JSON message -> %s", user.toString()));

        user.setDateSent(new Date()); // burada tarih bilgisi atanıyor
        user.setStatus(UserStatus.PASSIVE); // burada enum değeri atanıyor


        long unixTime = user.getDateSent().getTime();
        Date date = new Date(unixTime);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(date);


        // User objesini JSON formatına dönüştürme işlemi
        ObjectMapper mapper = new ObjectMapper();
        String userJson = "";
        try {
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while converting User object to JSON", e);
            return;
        }

        // E-posta gönderme işlemi
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("bagdatmutlu@gmail.com");
        message.setSubject("Yeni mesaj alındı!");
//        message.setText("Yeni bir JSON mesajı alındı: " + userJson);
//        message.setText("Yeni bir JSON mesajı alındı: " + userJson+ "\n\nGönderilme zamanı: " + formattedDate);
        message.setText("Yeni bir JSON mesajı alındı: " + userJson.replace("\"dateSent\":" + user.getDateSent().getTime() + ",", "") + "\n\nGönderilme zamanı: " + formattedDate);

        message.setReplyTo(emailFrom);
        javaMailSender.send(message);
    }

}
