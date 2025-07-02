package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.DTO.MessageDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.service.DepartmentService;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.MyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/send")
public class MessageController {
    @Autowired
    private MyBot myBot;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @PostMapping("/message/department/{id}")
    public void sendMessageByDepartment(
            @PathVariable(name = "id") Long departmentId,
            @RequestBody() MessageDTO messageDTO
    ){
        if (messageDTO == null || messageDTO.getMessage() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message data dont full");
        }

        Department department = departmentService.getById(departmentId);

        for (User user : department.getUsers()) {
            myBot.exec(new SendMessage(user.getId()+"",messageDTO.getMessage()));
        }


    }
    @PostMapping("/message")
    public void sendMessage(
            @RequestBody() MessageDTO messageDTO
    ){
        if (messageDTO == null || messageDTO.getId() == null || messageDTO.getMessage() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message data dont full");
        }
        myBot.exec(new SendMessage(messageDTO.getId(),messageDTO.getMessage()));
    }

    @PostMapping("/photo")
    public void sendPhoto(
            @RequestParam("file") MultipartFile file
    ){
        try {
            InputFile inputFile = new InputFile(file.getInputStream(), file.getOriginalFilename());
                myBot.execute(new SendPhoto("6409116156",inputFile));
        } catch (IOException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/video")
    public void sendVideo(
            @RequestParam("file") MultipartFile file
    ){
        try {
            InputFile inputFile = new InputFile(file.getInputStream(), file.getOriginalFilename());
            myBot.execute(new SendVideo("6409116156",inputFile));
        } catch (IOException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/document")
    public void sendDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("command") String command
    ){
        try {
            InputFile inputFile = new InputFile(file.getInputStream(), file.getOriginalFilename());
            myBot.execute(new SendDocument("6409116156",inputFile));
        } catch (IOException | TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 10 17 * * *") // Каждый день в 9:00
    public void checkBirthdaysAndSendWishes() {
        List<User> usersBirthday = userService.checkBirthdaysAndSendWishes();
        for (User user : usersBirthday) {
            myBot.sendWishes(user);
        }
    }
}
