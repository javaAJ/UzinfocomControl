package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.BotMessage;
import com.uzinfocom.uzinfocomcontrol.model.DTO.MessageDTO;
import com.uzinfocom.uzinfocomcontrol.model.DTO.SendMessageDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import com.uzinfocom.uzinfocomcontrol.service.BirthdayService;
import com.uzinfocom.uzinfocomcontrol.service.BotMessageService;
import com.uzinfocom.uzinfocomcontrol.service.DepartmentService;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import com.uzinfocom.uzinfocomcontrol.telegramBot.MyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MyBot myBot;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private BirthdayService birthdayService;
    @Autowired
    private UserService userService;
    @Autowired
    private BotMessageService botMessageService;

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> create(@AuthenticationPrincipal User user, @RequestBody MessageDTO botMessage) {
        MessageDTO saved = botMessageService.create(botMessage,departmentService.getById(user.getDepartment().getId()));
        return ResponseEntity.ok(saved);
    }

    // READ (один по id)
    @GetMapping("/{id}")
    public ResponseEntity<BotMessage> getById(@PathVariable Long id) {
        return ResponseEntity.ok(botMessageService.getById(id));
    }

    // READ (все)
    @GetMapping("/getAll")
    public ResponseEntity<List<MessageDTO>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(botMessageService.getAll(user.getDepartment().getId()));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> update(@PathVariable Long id, @RequestBody MessageDTO botMessage) {
        return ResponseEntity.ok(botMessageService.update(id, botMessage));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        botMessageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send/department/{id}")
    public void sendMessageByDepartment(
            @PathVariable(name = "id") Long departmentId,
            @RequestBody() SendMessageDTO sendMessageDTO
    ){
        if (sendMessageDTO == null || sendMessageDTO.getMessage() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message data dont full");
        }

        Department department = departmentService.getById(departmentId);

        for (User user : department.getUsers()) {
            myBot.exec(new SendMessage(user.getId()+"", sendMessageDTO.getMessage()));
        }


    }
    @PostMapping("/send")
    public void sendMessage(
            @RequestBody() SendMessageDTO sendMessageDTO
    ){
        if (sendMessageDTO == null || sendMessageDTO.getId() == null || sendMessageDTO.getMessage() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message data dont full");
        }
        myBot.exec(new SendMessage(sendMessageDTO.getId(), sendMessageDTO.getMessage()));
    }

    @PostMapping("/send/photo")
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

    @PostMapping("/send/video")
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

    @PostMapping("/send/document")
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

    @Scheduled(cron = "0 52 15 * * *",zone = "Asia/Tashkent") // Каждый день в 9:00
    public void checkBirthdaysAndSendWishes() {
        List<User> usersBirthday = userService.checkBirthdaysAndSendWishes();
        for (User user : usersBirthday) {
            myBot.sendWishes(user);
        }
    }
    @Scheduled(cron = "0 54 15 * * *",zone = "Asia/Tashkent") // Каждый день в 9:00
    public void checkBirthdayPayment() {
        List<UserBirthday> userBirthdayList = birthdayService.findAll();
        for (UserBirthday userBirthday : userBirthdayList) {
            myBot.sendUserPayment(userBirthday.getUserBirthday().getId(), userBirthday);
        }
    }
    @Scheduled(cron = "0 53 15 * * *",zone = "Asia/Tashkent")
    public void checkSoonBirthday() {
        List<User> soonBirthday = userService.findSoonBirthday();
//        birthdayService.saveUsersOfDepartment(soonBirthday);
    }

}
