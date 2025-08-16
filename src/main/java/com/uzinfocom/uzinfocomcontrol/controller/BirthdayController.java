package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.BirthdayPayment;
import com.uzinfocom.uzinfocomcontrol.model.DTO.PayToBirthdayDTO;
import com.uzinfocom.uzinfocomcontrol.model.DTO.UserBirthdayDTO;
import com.uzinfocom.uzinfocomcontrol.model.DTO.UserBirthdayPaymentDTO;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import com.uzinfocom.uzinfocomcontrol.service.BirthdayService;
import com.uzinfocom.uzinfocomcontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/info/{userId}")
    public UserBirthdayDTO getInfo(@PathVariable("userId") Long id) {
        System.out.println(id);
        System.out.println(birthdayService.findAll().size());

        UserBirthday byUserId = birthdayService.findByUserId(id);
        return mapping(byUserId);
    }
    @PostMapping("/pay")
    public UserBirthdayDTO payToBirthday(
            @RequestBody() PayToBirthdayDTO payToBirthdayDTO
    ) {
        System.out.println(payToBirthdayDTO.getBirthdayId());
        System.out.println(payToBirthdayDTO.getPayMoney());
        System.out.println(payToBirthdayDTO.getUserId());
        UserBirthday byUserId = birthdayService.pay(payToBirthdayDTO);
        return mapping(byUserId);
    }

    public UserBirthdayDTO mapping(UserBirthday userBirthday) {
        UserBirthdayDTO userBirthdayDTO = new UserBirthdayDTO();
        userBirthdayDTO.setId(userBirthday.getId());
        userBirthdayDTO.setBirthdayUser(userService.toUserDto(userBirthday.getUserBirthday()));
        userBirthdayDTO.setTotalAmountPaid(birthdayService.getTotalAmountPaid(userBirthday));
        List<UserBirthdayPaymentDTO> userBirthdayPaymentDTOList = new ArrayList<>();

        for (BirthdayPayment birthdayPayment : userBirthday.getUsersBirthdayPayment()) {
            UserBirthdayPaymentDTO userBirthdayPaymentDTO = new UserBirthdayPaymentDTO();
            userBirthdayPaymentDTO.setUser(userService.toUserDto(birthdayPayment.getUser()));
            userBirthdayPaymentDTO.setPaymentAmount(birthdayPayment.getPaymentAmount());
            userBirthdayPaymentDTO.setAmountPaid(birthdayPayment.getAmountPaid());
            userBirthdayPaymentDTO.setIsPaid(isPaid(birthdayPayment));
            userBirthdayPaymentDTOList.add(userBirthdayPaymentDTO);
        }

        userBirthdayDTO.setUsersBirthdayPayment(userBirthdayPaymentDTOList);
        return userBirthdayDTO;
    }

    private boolean isPaid(BirthdayPayment birthdayPayment){
        return birthdayPayment.getPaymentAmount()-birthdayPayment.getAmountPaid() <= 0;
    }
}
