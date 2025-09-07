package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.DTO.UserDTO;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.service.BirthdayService;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.MyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BirthdayService birthdayService;

    @GetMapping("/getAll")
    public List<UserDTO> getAll(){
        List<User> users = userService.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(userService.toUserDto(user));
        }
        return userDTOList;
    }
    @GetMapping("/getAllByDepartment")
    public List<UserDTO> getAllByDepartment(@AuthenticationPrincipal User user){
        List<User> users = userService.getByDepartment(user.getDepartment().getId());
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user1 : users) {
            userDTOList.add(userService.toUserDto(user1));
        }
        return userDTOList;
    }
    @GetMapping("/getAllByNotBirthday")
    public List<UserDTO> getAllByNotBirthdayByDepartment(@AuthenticationPrincipal User user){
        List<User> users = userService.getAllByNotBirthdayByDepartment(user.getDepartment().getId());
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user1 : users) {
            userDTOList.add(userService.toUserDto(user1));
        }
        return userDTOList;
    }

    @GetMapping("/get/{id}")
    public UserDTO get(@PathVariable("id") Long userId ){
        User user = userService.getById(userId);
        return userService.toUserDto(user);
    }

    @DeleteMapping("/delete/all")
    public void deleteAll(){
        userService.deleteAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAll(
            @PathVariable(name = "id") Long userId
    ){
        userService.deleteById(userId);
    }
}
