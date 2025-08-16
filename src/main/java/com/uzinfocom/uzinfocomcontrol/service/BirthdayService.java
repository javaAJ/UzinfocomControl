package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.BirthdayPayment;
import com.uzinfocom.uzinfocomcontrol.model.DTO.PayToBirthdayDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import com.uzinfocom.uzinfocomcontrol.model.repository.BirthdayPaymentRepository;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserBirthdayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class BirthdayService {
    @Autowired
    private BirthdayPaymentRepository birthdayPaymentRepository;
    @Autowired
    private UserBirthdayRepository userBirthdayRepository;

    public List<UserBirthday> findAll() {
        return userBirthdayRepository.findAll();
    }

    public UserBirthday findById(Long id) {
        return userBirthdayRepository.findById(id).get();
    }

    public UserBirthday findByUserId(Long id) {

        return userBirthdayRepository.findByUserBirthdayId(id);
    }

    public UserBirthday save(User user, Department department) {
        UserBirthday userBirthday = new UserBirthday();
        userBirthday.setUserBirthday(user);

        List<BirthdayPayment> usersBirthdayPayment = new ArrayList<>();
        for (User departmentUser : department.getUsers()) {
            if (!departmentUser.getId().equals(user.getId())) {
                BirthdayPayment birthdayPayment = new BirthdayPayment();
                birthdayPayment.setUser(departmentUser);
                birthdayPayment.setPaymentAmount(100_000D);
                birthdayPayment.setAmountPaid(0D);
                birthdayPaymentRepository.save(birthdayPayment);
                usersBirthdayPayment.add(birthdayPayment);
            }
        }

        userBirthday.setUsersBirthdayPayment(usersBirthdayPayment);
        UserBirthday save = userBirthdayRepository.save(userBirthday);
        System.out.println(save.getUserBirthday().getFirstName());
        return save;
    }


    public boolean checkBirthday(User user) {
        return userBirthdayRepository.findByUserBirthdayId(user.getId()) == null;
    }

    public UserBirthday pay(PayToBirthdayDTO payToBirthdayDTO) {
        UserBirthday userBirthday = findById(payToBirthdayDTO.getBirthdayId());

        for (BirthdayPayment birthdayPayment : userBirthday.getUsersBirthdayPayment()) {
            if (birthdayPayment.getUser().getId().equals(payToBirthdayDTO.getUserId())) {
                birthdayPayment.setAmountPaid(payToBirthdayDTO.getPayMoney());
            }
        }


        return userBirthdayRepository.save(userBirthday);
    }

    public Double getTotalAmountPaid(UserBirthday userBirthday) {
        Double totalAmountPaid = 0.0;
        for (BirthdayPayment birthdayPayment : userBirthday.getUsersBirthdayPayment()) {
            totalAmountPaid += birthdayPayment.getAmountPaid();
        }
        return totalAmountPaid;
    }
}
