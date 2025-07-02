package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.BirthdayPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirthdayPaymentRepository extends JpaRepository<BirthdayPayment, Long> {
    List<Object> findByUserId(Long userId);
}
