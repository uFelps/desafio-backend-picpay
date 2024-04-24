package com.picpay.simplificado.services;

import com.picpay.simplificado.dtos.NotificationDTO;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.services.exceptions.NotificationServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) {

        String email = null;
        try {
            email = user.getEmail();
            NotificationDTO notificationRequest = new NotificationDTO(email, message);

            ResponseEntity<String> response = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest, String.class);

            if (!(response.getStatusCode() == HttpStatus.OK)) {
                throw new NotificationServiceException("");
            }
            System.out.println("Notification sent successfully to email: " + email);

        } catch (Exception e) {
            System.out.println("Error on sent message to user: " + email);
        }

    }
}
