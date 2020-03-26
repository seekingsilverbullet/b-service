/*
 * 3/26/20, 3:48 PM
 * ivan
 */

package com.github.im.bs.business.user.control;

import com.github.im.bs.business.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private User testUser;
    @InjectMocks
    private UserService userService;

    private static final long TEST_ID = 1L;

    @Test
    void findAllUsers_success() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(testUser));

        List<User> allUsers = userService.findAllUsers();

        assertFalse(allUsers.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAllUsers_empty() {
        List<User> allUsers = userService.findAllUsers();

        assertTrue(allUsers.isEmpty());
    }

    @Test
    void findUser_success() {
        when(userRepository.findUserById(anyLong())).thenReturn(testUser);

        User foundUser = userService.findUser(TEST_ID);

        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findUserById(anyLong());
    }

    @Test
    void findUser_not_found() {
        boolean caught = false;
        try {
            userService.findUser(TEST_ID);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertEquals("404 NOT_FOUND \"User not found\"", e.getMessage());
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(testUser.getId()).thenReturn(TEST_ID);

        long userId = userService.createUser(testUser);

        assertEquals(TEST_ID, userId);
        verify(testUser, times(1)).setCreatedAt(any(LocalDateTime.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser() {
        when(userRepository.findUserById(anyLong())).thenReturn(testUser);

        userService.updateUser(testUser, TEST_ID);

        verify(testUser, times(1)).getFirstName();
        verify(testUser, times(1)).getLastName();
        verify(testUser, times(1)).getUserType();
        verify(testUser, times(0)).getId();
        verify(testUser, times(0)).getCreatedAt();
        verify(userRepository, times(1)).findUserById(anyLong());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void deleteUser() {
        when(userRepository.findUserById(anyLong())).thenReturn(testUser);

        userService.deleteUser(TEST_ID);

        verify(userRepository, times(1)).findUserById(anyLong());
        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void findCreatedUsersByPeriod() {
        LocalDateTime now = LocalDateTime.now();
        when(userRepository.findCreatedUsersByPeriod(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(testUser));

        List<User> foundUser = userService.findCreatedUsersByPeriod(now, now);

        assertEquals(testUser, foundUser.get(0));
        verify(userRepository, times(1))
                .findCreatedUsersByPeriod(any(LocalDateTime.class), any(LocalDateTime.class));
    }
}