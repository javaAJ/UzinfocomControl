package com.uzinfocom.uzinfocomcontrol.service;

import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.model.RegisterLink;
import com.uzinfocom.uzinfocomcontrol.model.repository.RegisterLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterLinkService {
    @Autowired
    private RegisterLinkRepository registerLinkRepository;

    public Long generateLink(Department department){
        RegisterLink registerLink = new RegisterLink();
        Long uniqId;
        do {
            uniqId = Math.round(Math.random() * 100000);
        } while (!isUniq(uniqId));
        registerLink.setUniqId(uniqId);
        registerLink.setDepartment(department);
        registerLinkRepository.save(registerLink);
        return uniqId;
    }

    private boolean isUniq(Long uniqId) {
        return getByUniqId(uniqId) == null;
    }

    public RegisterLink getByUniqId(Long uniqId) {
        return registerLinkRepository.findByUniqId(uniqId);
    }
}
