package net.ammal.elasticsearch.javaapitest.controller;

import net.ammal.elasticsearch.javaapitest.model.ResponseJson;
import net.ammal.elasticsearch.javaapitest.model.SearchResponseJson;
import net.ammal.elasticsearch.javaapitest.model.UserInput;
import net.ammal.elasticsearch.javaapitest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseJson addUser(@RequestBody UserInput input) {
        return userService.createUser(input);
    }

    @PostMapping("/addUsersBulk")
    public ResponseJson addUsersBulk(@RequestBody List<UserInput> users) {
        return userService.createUsersBulk(users);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseJson getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseJson deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/getAllUsers")
    public SearchResponseJson getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUsersWithPermission/{permissionName}")
    public SearchResponseJson getUsersWithPermission(@PathVariable String permissionName) {
        return userService.getAllUsersWithSpecifiedPermission(permissionName);
    }
}
