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
    private final UserService userService;

    @GetMapping
    @ApiOperation(value = "Returns all of existing users", response = User.class, responseContainer = "List")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Returns existing user by id", response = User.class)
    public ResponseEntity<User> findUser(@PathVariable("id") long userId) {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Creates new user")
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        long userId = userService.createUser(user);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    @ApiOperation(value = "Updates the existing user by id")
    public ResponseEntity<?> updateUser(@PathVariable("id") long userId, @RequestBody User user) {
        userService.updateUser(user, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deletes the existing user by id")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
