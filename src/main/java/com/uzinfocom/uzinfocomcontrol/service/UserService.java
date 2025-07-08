package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.DTO.UserDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate parsedDate = LocalDate.parse(dateOfBirthday, formatter);
            user.setDateOfBirthday(parsedDate);
            userRepository.save(user);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void setPosition(User user, Position position) {
        user.setTelegramPosition(position);
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public UserDTO toUserDto(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPatronymic(user.getPatronymic());
        dto.setUserName(user.getUserName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setDateOfBirthday(user.getDateOfBirthday());
        dto.setTelegramPosition(user.getTelegramPosition());

        if (user.getDepartment() != null) {
            dto.setDepartmentId(user.getDepartment().getId());
            dto.setDepartmentName(user.getDepartment().getName());
        }

        return dto;
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }


    public List<User> checkBirthdaysAndSendWishes() {
        List<User> allUsers = userRepository.findAll();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent"));

        System.out.println(today.getDayOfMonth() + "   " + today.getMonthValue());
        System.out.println("dadsadadsadsada");
        return allUsers.stream()
                .filter(u -> {
                    LocalDate dob = u.getDateOfBirthday(); // тип должен быть LocalDate
                    System.out.println(dob.getDayOfMonth() +"  " + dob.getMonthValue());
                    return dob.getDayOfMonth() == today.getDayOfMonth() &&
                            dob.getMonthValue() == today.getMonthValue();
                })
                .collect(Collectors.toList());
    }

    public List<User> findSoonBirthday() {
        List<User> users = userRepository.findAll();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent"));
        LocalDate afterMonth = LocalDate.now(ZoneId.of("Asia/Tashkent"));
        afterMonth = afterMonth.plusMonths(1);

        List<User> usersWithBirthday = new ArrayList<>();
        for (User user : users) {
            System.out.println("=================================");
            System.out.println(user.getDateOfBirthday().getYear());
            System.out.println(user.getDateOfBirthday().getDayOfMonth());
            System.out.println(user.getDateOfBirthday().getMonthValue());
            System.out.println(user.getDateOfBirthday().isAfter(today) &&  user.getDateOfBirthday().isBefore(afterMonth));
            System.out.println("Today");
            System.out.println(today.getYear());
            System.out.println(today.getDayOfMonth());
            System.out.println(today.getMonthValue());
            System.out.println("Month");
            System.out.println(afterMonth.getYear());
            System.out.println(afterMonth.getDayOfMonth());
            System.out.println(afterMonth.getMonthValue());
            if(user.getDateOfBirthday().isAfter(today) &&  user.getDateOfBirthday().isBefore(afterMonth)) {
                usersWithBirthday.add(user);
            }
        }
        return usersWithBirthday;
    }
}
