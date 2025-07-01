package com.uzinfocom.uzinfocomcontrol.model.repository;

import com.uzinfocom.uzinfocomcontrol.model.RegisterLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterLinkRepository extends JpaRepository<RegisterLink, Long> {

    public RegisterLink findByUniqId(Long uniqId);
}
