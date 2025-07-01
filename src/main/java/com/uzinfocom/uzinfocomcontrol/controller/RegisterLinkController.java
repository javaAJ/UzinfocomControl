package com.uzinfocom.uzinfocomcontrol.controller;

import com.uzinfocom.uzinfocomcontrol.model.Department;
import com.uzinfocom.uzinfocomcontrol.service.DepartmentService;
import com.uzinfocom.uzinfocomcontrol.service.RegisterLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/registerLink")
public class RegisterLinkController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RegisterLinkService registerLinkService;

    @GetMapping("/generate/{id}")
    public Long addRegisterLink(@PathVariable("id") Long departmentId){
        Department department = departmentService.getById(departmentId);
        return registerLinkService.generateLink(department);
    }
}
