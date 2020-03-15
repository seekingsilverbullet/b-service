/*
 * 3/15/20, 12:50 PM
 * ivan
 */

package com.github.im.bs.business.boundary;

import com.github.im.bs.business.control.UserService;
import com.github.im.bs.business.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@RequiredArgsConstructor
public class UserResource {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
}
