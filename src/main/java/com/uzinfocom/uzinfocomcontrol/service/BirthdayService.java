package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserBirthdayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class BirthdayService {
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

//    public UserBirthday save(User user, Department department, List<User> soonBirthday) {
//        UserBirthday userBirthday = new UserBirthday();
//        userBirthday.setUserBirthday(user);
//
//        UserBirthday save = userBirthdayRepository.save(userBirthday);
//        return save;
//    }


    public boolean checkBirthday(User user) {
        return userBirthdayRepository.findByUserBirthdayId(user.getId()) == null;
    }

    public boolean pay(Long birthdayId,User userBirthday, User userPayment, Boolean isPaid, Department department) {
        UserBirthday userBirthdayToSave;
        if(birthdayId == null){
           userBirthdayToSave = new UserBirthday();
           userBirthdayToSave.setUserBirthday(userBirthday);
           userBirthdayToSave.setUserPayment(userPayment);
           userBirthdayToSave.setIsPaid(isPaid);
           userBirthdayToSave.setPaidDate(LocalDate.now());
           userBirthdayToSave.setDepartment(department);
           userBirthdayRepository.save(userBirthdayToSave);
           return true;
        }
        userBirthdayRepository.deleteById(birthdayId);
        return true;
    }

    public List<UserBirthday> getAllByDepartment(Long departmentId) {
        List<UserBirthday> all = userBirthdayRepository.findAllByDepartment_Id(departmentId);
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent")).minusMonths(1);
        LocalDate afterMonth = LocalDate.now(ZoneId.of("Asia/Tashkent")).plusMonths(1);

        List<UserBirthday> userBirthdayList = new ArrayList<>();
        for (UserBirthday userBirthday : all) {
            if (userBirthday.getPaidDate().getDayOfYear() >= today.getDayOfYear() &&
                    userBirthday.getPaidDate().getDayOfYear() <= afterMonth.getDayOfYear()) {
                userBirthdayList.add(userBirthday);
            }
        }
        return userBirthdayList;
    }

//    public List<User> getAllByNotBirthday() {
//        List<UserBirthday> userBirthdayList = userBirthdayRepository.findAll();
//
//
//
//    }
}
