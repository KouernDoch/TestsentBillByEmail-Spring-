package org.example.testsendemail.controller;

import lombok.RequiredArgsConstructor;
import org.example.testsendemail.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailSevice;

    @GetMapping
    public String email(@RequestParam String email) {

        return emailSevice.sendEmail(email);
    }

}
