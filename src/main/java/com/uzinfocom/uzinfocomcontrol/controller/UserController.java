package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.MyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/get/{id}")
    public List<User> get(){
        return userService.getAll();
    }

    @GetMapping("/delete/all")
    public void deleteAll(){
        userService.deleteAll();
    }
}
