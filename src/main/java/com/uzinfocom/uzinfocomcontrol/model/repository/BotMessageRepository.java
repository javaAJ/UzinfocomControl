package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.BotMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotMessageRepository extends JpaRepository<BotMessage, Long> {
    List<BotMessage> findAllByDepartment_Id(Long departmentId);
}
