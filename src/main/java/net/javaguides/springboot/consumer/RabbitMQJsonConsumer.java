package net.javaguides.springboot.consumer;

import net.javaguides.springboot.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(User user){
        LOGGER.info(String.format("Received JSON message -> %s", user.toString()));

        // E-posta gönderme işlemi
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("bagdatmutlu@gmail.com");
        message.setSubject("Yeni mesaj alındı!");
        message.setText("Yeni bir JSON mesajı alındı: " + user.toString());
        message.setReplyTo(emailFrom);
        javaMailSender.send(message);
    }


//    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
//    public void consumeJsonMessage(User user){
//        LOGGER.info(String.format("Received JSON message -> %s", user.toString()));
//    }
}
