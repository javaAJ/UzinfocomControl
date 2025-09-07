package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.DTO.*;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import com.uzinfocom.uzinfocomcontrol.service.BirthdayService;
import com.uzinfocom.uzinfocomcontrol.service.DepartmentService;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/birthday")
public class BirthdayController {
    @Autowired
    private BirthdayService birthdayService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

//    @GetMapping("/matrix")
//    public List<MatrixDTO> getMatrix() {
//        birthdayService.getMatrix()
//        return ;
//    }

    @GetMapping("/getAllByDepartment")
    public List<UserBirthdayDTO> getAllBYDepartment(@AuthenticationPrincipal User user) {

        List<UserBirthday> birthdayList = birthdayService.getAllByDepartment(user.getDepartment().getId());

        List<UserBirthdayDTO> userBirthdayDTOList = new ArrayList<>();
        for (UserBirthday userBirthday : birthdayList) {
            userBirthdayDTOList.add(mapping(userBirthday));
        }

        return userBirthdayDTOList;
    }
    @GetMapping("/getSoonBirthdayUsersByDepartment")
    public List<UserDTO> getSoonBirthdayUsers(@AuthenticationPrincipal User user) {
        List<User> soonBirthday = userService.findSoonBirthdayByDepartment(user.getDepartment().getId());

        List<UserDTO> userDTOList = new ArrayList<>();
        for (User userBirthday : soonBirthday) {
            userDTOList.add(userService.toUserDto(userBirthday));
        }

        return userDTOList;
    }
    @GetMapping("/info/{userId}")
    public UserBirthdayDTO getInfo(@PathVariable("userId") Long id) {
        System.out.println(id);
        System.out.println(birthdayService.findAll().size());

        UserBirthday byUserId = birthdayService.findByUserId(id);
        return mapping(byUserId);
    }

    @GetMapping("/matrix")
    public List<UserBirthdayDTO> getInfo() {
        List<UserBirthday> userBirthdayList = birthdayService.findAll();
        List<UserBirthdayDTO> userBirthdayDTOList = new ArrayList<>();
        for (UserBirthday userBirthday : userBirthdayList) {
            userBirthdayDTOList.add(mapping(userBirthday));
        }
        return userBirthdayDTOList;
    }

    @PostMapping("/pay")
    public boolean payToBirthday(
            @RequestBody() PayToBirthdayDTO payToBirthdayDTO,
            @AuthenticationPrincipal User user
    ) {
        User userBirthday = userService.getById(payToBirthdayDTO.getBirthdayUserId());
        User userPayment = userService.getById(payToBirthdayDTO.getUserPaymentId());
        Department department = departmentService.getById(user.getDepartment().getId());

        boolean isAdd = birthdayService.pay(payToBirthdayDTO.getId(),userBirthday, userPayment, payToBirthdayDTO.getIsPaid(),department);
        return isAdd;
    }

    public UserBirthdayDTO mapping(UserBirthday userBirthday) {
        UserBirthdayDTO userBirthdayDTO = new UserBirthdayDTO();
        userBirthdayDTO.setId(userBirthday.getId());
        userBirthdayDTO.setBirthdayUser(userService.toUserDto(userBirthday.getUserBirthday()));
        userBirthdayDTO.setIsPaid(userBirthday.getIsPaid());
        userBirthdayDTO.setPaidDate(userBirthday.getPaidDate());
        userBirthdayDTO.setUserPayment(userService.toUserDto(userBirthday.getUserPayment()));
        return userBirthdayDTO;
    }

}
