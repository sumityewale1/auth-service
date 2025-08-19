package com.ecommerce.auth_service.client;

import com.ecommerce.auth_service.model.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceBaseUrl = "http://user-service"; // or actual URL, e.g. http://localhost:8081

    @Autowired
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserView getUserByEmail(String email) {
        String url = userServiceBaseUrl + "/users/findByEmail?email={email}";
        System.out.println(url);
        try {
            ResponseEntity<UserView> response = restTemplate.getForEntity(url, UserView.class, email);
            System.out.println(response);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                // handle non-2xx status codes
                return null;
            }
        } catch (Exception e) {
            // log exception and handle errors
            e.printStackTrace();
            return null;
        }
    }
}
