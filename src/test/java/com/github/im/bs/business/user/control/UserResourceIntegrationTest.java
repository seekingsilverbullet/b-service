/*
 * 3/26/20, 6:37 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = restTemplate.postForEntity("/users", UserFactory.createTestUser(), Long.class).getBody();
        assertNotNull(userId);
    }

    @Test
    void findAllUsers() {
        List<User> users = restTemplate.exchange("/users", HttpMethod.GET, null, getUserType()).getBody();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(userId, users.get(0).getId());
    }

    @Test
    void findUser() {
        User user = restTemplate.getForEntity("/users/{id}", User.class, userId).getBody();

        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    void updateUser() {
        User user = restTemplate.getForEntity("/users/{id}", User.class, userId).getBody();
        assertNotNull(user);
        String newName = "John";
        user.setFirstName(newName);

        restTemplate.put("/users/{id}", getUserEntity(user), userId);

        user = restTemplate.getForEntity("/users/{id}", User.class, userId).getBody();
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(newName, user.getFirstName());
    }

    @Test
    void deleteUser() {
        User user = restTemplate.getForEntity("/users/{id}", User.class, userId).getBody();
        assertNotNull(user);

        restTemplate.delete("/users/{id}", userId);

        ResponseEntity<User> response = restTemplate.getForEntity("/users/{id}", User.class, userId);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private ParameterizedTypeReference<List<User>> getUserType() {
        return new ParameterizedTypeReference<List<User>>() {
        };
    }

    private HttpEntity<User> getUserEntity(User user) {
        return new HttpEntity<>(user);
    }
}
