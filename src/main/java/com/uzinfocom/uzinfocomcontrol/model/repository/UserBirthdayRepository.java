package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.BirthdayPayment;
import com.uzinfocom.uzinfocomcontrol.model.User;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBirthdayRepository extends JpaRepository<UserBirthday, Long> {
    @EntityGraph(attributePaths = "usersBirthdayPayment")
    List<UserBirthday> findAll();

    UserBirthday findByUserBirthdayId(Long id);
}
