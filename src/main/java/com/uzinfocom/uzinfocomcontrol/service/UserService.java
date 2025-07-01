package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User register(Long id, String userName) {
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        return userRepository.save(user);
    }

    public void addUser(String firstName, String lastName, String userName) {

    }

    public void setDepartment(User user, Department department) {
        user.setDepartment(department);
        userRepository.save(user);
    }

    public void setFullName(User user, String fullName) {
        String[] split = fullName.split(" ");
        if (split.length > 3) {
            split[2] += " " + split[3];
        }
        user.setLastName(split[0]);
        user.setFirstName(split[1]);
        user.setPatronymic(split[2]);

        userRepository.save(user);
    }

    public void setPhoneNumber(User user, String phoneNumber) {
        user.setPhoneNumber(phoneNumber);

        userRepository.save(user);
    }

    public boolean setDateOfBirthday(User user, String dateOfBirthday) {
        String[] dateSplit = dateOfBirthday.split("/");
        int day;
        int month;
        int year;
        try {
            day = Integer.parseInt(dateSplit[0]);
            month = Integer.parseInt(dateSplit[1]);
            year = Integer.parseInt(dateSplit[2]);
        } catch (Exception e) {
            return false;
        }

        Date date = new Date();
        date.setDate(day);
        date.setMonth(month);
        date.setYear(year);
        System.out.println(date.getYear());

        user.setDateOfBirthday(date);

        userRepository.save(user);
        return true;
    }

    public void setPosition(User user, Position position) {
        user.setTelegramPosition(position);
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
