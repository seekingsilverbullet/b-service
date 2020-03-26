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

import static com.github.im.bs.business.util.Constants.*;

@RestController
@RequestMapping(path = "/users", produces = APPLICATION_JSON)
@RequiredArgsConstructor
@Api(value = USER_HANDLER)
public class UserResource {
    private final UserService userService;

    @GetMapping
    @ApiOperation(value = ALL_USERS_DESCRIPTION, response = User.class, responseContainer = LIST)
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = FIND_USER_DESCRIPTION, response = User.class)
    public ResponseEntity<User> findUser(@PathVariable("id") long userId) {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(consumes = APPLICATION_JSON)
    @ApiOperation(value = CREATE_USER_DESCRIPTION)
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        long userId = userService.createUser(user);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON)
    @ApiOperation(value = UPDATE_USER_DESCRIPTION)
    public ResponseEntity<?> updateUser(@PathVariable("id") long userId, @RequestBody User user) {
        userService.updateUser(user, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = DELETE_USER_DESCRIPTION)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
