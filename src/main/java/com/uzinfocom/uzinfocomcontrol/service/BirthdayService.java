package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.BirthdayPayment;
import com.uzinfocom.uzinfocomcontrol.model.DTO.UserBirthdayDTO;
import com.uzinfocom.uzinfocomcontrol.model.DTO.UserBirthdayPaymentDTO;
import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import com.uzinfocom.uzinfocomcontrol.model.repository.BirthdayPaymentRepository;
import com.uzinfocom.uzinfocomcontrol.model.repository.UserBirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


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
            if (departmentUser.getId().equals(user.getId())) {
                BirthdayPayment birthdayPayment = new BirthdayPayment();
                birthdayPayment.setUser(user);
                birthdayPayment.setPaymentAmount(100_000D);
                birthdayPayment.setAmountPaid(0D);
                birthdayPaymentRepository.save(birthdayPayment);
                usersBirthdayPayment.add(birthdayPayment);
            }
        }

        userBirthday.setUsersBirthdayPayment(usersBirthdayPayment);
        return userBirthdayRepository.save(userBirthday);
    }

    public UserBirthdayDTO mapping(UserBirthday userBirthday) {
        UserBirthdayDTO userBirthdayDTO = new UserBirthdayDTO();
        userBirthdayDTO.setId(userBirthday.getId());
        userBirthdayDTO.setFirstName(userBirthday.getUserBirthday().getFirstName());
        userBirthdayDTO.setLastName(userBirthday.getUserBirthday().getLastName());
        userBirthdayDTO.setPatronymic(userBirthday.getUserBirthday().getPatronymic());

        List<UserBirthdayPaymentDTO> userBirthdayPaymentDTOList = new ArrayList<>();

        for (BirthdayPayment birthdayPayment : userBirthday.getUsersBirthdayPayment()) {
            UserBirthdayPaymentDTO userBirthdayPaymentDTO = new UserBirthdayPaymentDTO();
            userBirthdayPaymentDTO.setId(birthdayPayment.getId());
            userBirthdayPaymentDTO.setUserId(birthdayPayment.getUser().getId());
            userBirthdayPaymentDTO.setLastName(birthdayPayment.getUser().getLastName());
            userBirthdayPaymentDTO.setFirstName(birthdayPayment.getUser().getFirstName());
            userBirthdayPaymentDTO.setPatronymic(birthdayPayment.getUser().getPatronymic());
            userBirthdayPaymentDTO.setPaymentAmount(birthdayPayment.getPaymentAmount());
            userBirthdayPaymentDTO.setAmountPaid(birthdayPayment.getAmountPaid());
            userBirthdayPaymentDTOList.add(userBirthdayPaymentDTO);
        }

        userBirthdayDTO.setUsersBirthdayPayment(userBirthdayPaymentDTOList);
        return userBirthdayDTO;
    }

    public void mapping(UserBirthdayDTO userBirthdayDTO) {

    }

    public boolean checkBirthday(User user) {
        return userBirthdayRepository.findByUserBirthdayId(user.getId()) == null;
    }
}
