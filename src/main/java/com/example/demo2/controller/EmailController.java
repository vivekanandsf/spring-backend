package com.example.demo2.controller;

import com.example.demo2.model.EmailDetails;
import com.example.demo2.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/mail")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class EmailController {

    private final EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    // Sending email with attachment
    @PostMapping(value = "/sendMailWithAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendMailWithAttachment(
            @RequestParam("file") MultipartFile file,
            @RequestParam("recipient") String recipient,
            @RequestParam("msgBody") String msgBody,
            @RequestParam("subject") String subject
    ) {
        /*ObjectMapper mapper = new ObjectMapper();
        EmailDetails details = mapper.readValue(emailDetails, EmailDetails.class);*/
        EmailDetails details = new EmailDetails(recipient, msgBody, subject);
        String status = emailService.sendMailWithAttachment(details, file);
        return status;
    }
}
