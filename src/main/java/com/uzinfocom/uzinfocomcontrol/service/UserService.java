package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.DTO.UserDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.enums.Position;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return allUsers.stream()
                .filter(u -> {
                    LocalDate dob = u.getDateOfBirthday(); // тип должен быть LocalDate
                    System.out.println(dob.getDayOfMonth() + "  " + dob.getMonthValue());
                    return dob.getDayOfMonth() == today.getDayOfMonth() &&
                            dob.getMonthValue() == today.getMonthValue();
                })
                .collect(Collectors.toList());
    }

    public List<User> findSoonBirthday() {
        List<User> users = userRepository.findAll();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent")).minusMonths(1);
        LocalDate afterMonth = LocalDate.now(ZoneId.of("Asia/Tashkent")).plusMonths(1);

        List<User> usersWithBirthday = new ArrayList<>();
        System.out.println("Today");
        System.out.println(today.getDayOfYear());
        System.out.println("After month");
        System.out.println(afterMonth.getDayOfYear());
        for (User user : users) {
            if (user.getDateOfBirthday().getDayOfYear() >= today.getDayOfYear() &&
                    user.getDateOfBirthday().getDayOfYear() <= afterMonth.getDayOfYear()) {
                usersWithBirthday.add(user);
            }
        }
        return usersWithBirthday;
    }

    public List<User> getAllByNotBirthday() {
        List<User> users = userRepository.findAll();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent")).minusMonths(1);
        LocalDate afterMonth = LocalDate.now(ZoneId.of("Asia/Tashkent")).plusMonths(1);

        List<User> usersWithBirthday = new ArrayList<>();
        for (User user : users) {
            System.out.println("=======================");
            System.out.println(user.getDateOfBirthday().getDayOfYear());
            if (user.getDateOfBirthday().getDayOfYear() < today.getDayOfYear() ||
                    user.getDateOfBirthday().getDayOfYear() > afterMonth.getDayOfYear()) {
                usersWithBirthday.add(user);
            }
        }
        return usersWithBirthday;
    }




    public void saveMockDate(Department department, Department department2){
        User user = new User();
        user.setFirstName("Jahongir");
        user.setLastName("Akbarov");
        user.setPatronymic("Abror O`g`li");
        user.setDateOfBirthday(LocalDate.of(2000,9,4));
        user.setTelegramPosition(Position.WAIT);
        user.setDepartment(department);
        user.setTelegramUserName("a_n_g_u_l_a_r");
        user.setUserName("admin");
        user.setPassword("123");
        user.setPhoneNumber("+9989101098");
        user.setId(6409116156L);

        userRepository.save(user);

        User user2 = new User();
        user2.setFirstName("Ali");
        user2.setLastName("Vali");
        user2.setPatronymic("Ali");
        user2.setDateOfBirthday(LocalDate.of(2000,9,4));
        user2.setTelegramPosition(Position.WAIT);
        user2.setDepartment(department);
        user2.setUserName("mmbbzzp");
        user2.setPhoneNumber("+9272626");
        user2.setId(7369216086L);

        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Ali");
        user3.setLastName("Vali");
        user3.setPatronymic("Ali");
        user3.setDateOfBirthday(LocalDate.of(2000,9,7));
        user3.setTelegramPosition(Position.WAIT);
        user3.setDepartment(department);
        user3.setUserName("mmbbzzp");
        user3.setPhoneNumber("+9272626");
        user3.setId(-1L);

        userRepository.save(user3);

        User user4 = new User();
        user4.setFirstName("Ali");
        user4.setLastName("Vali");
        user4.setPatronymic("Ali");
        user4.setDateOfBirthday(LocalDate.of(2000,7,20));
        user4.setTelegramPosition(Position.WAIT);
        user4.setDepartment(department2);
        user4.setUserName("mmbbzzp");
        user4.setPhoneNumber("+9272626");
        user4.setId(-2L);

        userRepository.save(user4);


        User user5 = new User();
        user5.setFirstName("Ali");
        user5.setLastName("Vali");
        user5.setPatronymic("Ali");
        user5.setDateOfBirthday(LocalDate.of(2000,9,20));
        user5.setTelegramPosition(Position.WAIT);
        user5.setDepartment(department2);
        user5.setUserName("user");
        user5.setPassword("111");
        user5.setPhoneNumber("+9272626");
        user5.setId(-3L);


        userRepository.save(user5);


        User user6 = new User();
        user6.setFirstName("Ahad");
        user6.setLastName("Qayum");
        user6.setPatronymic("valiyev");
        user6.setDateOfBirthday(LocalDate.of(1990,3,15));
        user6.setTelegramPosition(Position.WAIT);
        user6.setDepartment(department);
        user6.setUserName("mmbbzzp");
        user6.setPhoneNumber("+9989398484");
        user6.setId(-4L);


        userRepository.save(user6);


        User user7 = new User();
        user7.setFirstName("Zokir");
        user7.setLastName("Rahimov");
        user7.setPatronymic("BirNarsayev");
        user7.setDateOfBirthday(LocalDate.of(2006,1,5));
        user7.setTelegramPosition(Position.WAIT);
        user7.setDepartment(department2);
        user7.setUserName("zokirjon");
        user7.setPhoneNumber("+998997777777");
        user7.setId(-5L);


        userRepository.save(user7);
    }

    public List<User> getByDepartment(Long departmentId) {
        return userRepository.findAllByDepartment_Id(departmentId);
    }

    public List<User> getAllByNotBirthdayByDepartment(Long id) {
        List<User> users = userRepository.findAllByDepartment_Id(id);
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent")).minusMonths(1);
        LocalDate afterMonth = LocalDate.now(ZoneId.of("Asia/Tashkent")).plusMonths(1);

        List<User> usersWithBirthday = new ArrayList<>();
        for (User user : users) {
            if (user.getDateOfBirthday().getDayOfYear() < today.getDayOfYear() ||
                    user.getDateOfBirthday().getDayOfYear() > afterMonth.getDayOfYear()) {
                usersWithBirthday.add(user);
            }
        }
        return usersWithBirthday;
    }

    public List<User> findSoonBirthdayByDepartment(Long id) {
        List<User> users = userRepository.findAllByDepartment_Id(id);
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Tashkent")).minusMonths(1);
        LocalDate afterMonth = LocalDate.now(ZoneId.of("Asia/Tashkent")).plusMonths(1);

        List<User> usersWithBirthday = new ArrayList<>();
        for (User user : users) {
            if (user.getDateOfBirthday().getDayOfYear() >= today.getDayOfYear() &&
                    user.getDateOfBirthday().getDayOfYear() <= afterMonth.getDayOfYear()) {
                usersWithBirthday.add(user);
            }
        }
        return usersWithBirthday;
    }
}
