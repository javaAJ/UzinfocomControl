package com.uzinfocom.uzinfocomcontrol.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/birthday")
public class BirthdayController {

    @GetMapping("/info/{userId}")
    public void getInfo(@PathVariable("userId") Long id) {

    }
}
