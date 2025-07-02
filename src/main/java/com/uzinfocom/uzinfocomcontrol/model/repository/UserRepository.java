package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM users u WHERE FUNCTION('DAY', u.dateOfBirthday) = :day AND FUNCTION('MONTH', u.dateOfBirthday) = :month")
    List<User> findUsersWithBirthday(@Param("day") int day, @Param("month") int month);
}
