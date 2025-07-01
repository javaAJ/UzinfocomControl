package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.DTO.UserDTO;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.MyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getAll")
    public List<UserDTO> getAll(){
        List<User> users = userService.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(userService.toUserDto(user));
        }
        return userDTOList;
    }

    @GetMapping("/get/{id}")
    public List<UserDTO> get(){
        List<User> users = userService.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(userService.toUserDto(user));
        }
        return userDTOList;
    }

    @GetMapping("/delete/all")
    public void deleteAll(){
        userService.deleteAll();
    }
}
