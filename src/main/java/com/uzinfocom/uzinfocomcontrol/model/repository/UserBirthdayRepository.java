package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.BirthdayPayment;
import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBirthdayRepository extends JpaRepository<UserBirthday, Long> {
    UserBirthday findByUserBirthdayId(Long id);
}
