package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.UserBirthday;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBirthdayRepository extends JpaRepository<UserBirthday, Long> {

    UserBirthday findByUserBirthdayId(Long id);

    List<UserBirthday> findAllByDepartment_Id(Long departmentId);
}
