package com.template.eazypos.controller;

import com.template.eazypos.dto.LoginRequest;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.User;
import com.template.eazypos.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public CommonResponse<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseHelper.ok( userService.login(loginRequest));
    }
    @PostMapping("/add")
    public CommonResponse<User> addUser(@RequestBody User user){
        return ResponseHelper.ok( userService.addUser(user));
    }
    @GetMapping("/{id}")
    public CommonResponse <User> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( userService.get(id  ));
    }
    @GetMapping
    public CommonResponse<List<User>> getAll(){
        return ResponseHelper.ok( userService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<User> put(@PathVariable("id") Long id , @RequestBody User user){
        return ResponseHelper.ok( userService.edit(id, user));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( userService.delete(id));
    }
}
