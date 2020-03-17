/*
 * 3/15/20, 12:50 PM
 * ivan
 */

package com.github.im.bs.business.user.boundary;

import com.github.im.bs.business.user.control.UserService;
import com.github.im.bs.business.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@RequiredArgsConstructor
@Api(value = "User Handler")
public class UserResource {
    private final UserService service;

    @GetMapping
    @ApiOperation(value = "Returns all of existing users", response = User.class, responseContainer = "List")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Returns existing user by id", response = User.class)
    public ResponseEntity<User> getUser(@PathVariable("id") long userId) {
        User user = service.getUser(userId);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Creates new user")
    public ResponseEntity<Long> create(@RequestBody User user) {
        long userId = service.create(user);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    @ApiOperation(value = "Updates the existing user by id")
    public ResponseEntity<?> update(@PathVariable("id") long userId, @RequestBody User user) {
        return service.update(user, userId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deletes the existing user by id")
    public ResponseEntity<?> delete(@PathVariable("id") long userId) {
        return service.delete(userId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
