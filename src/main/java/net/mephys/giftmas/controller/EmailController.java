package net.mephys.giftmas.controller;

import net.mephys.giftmas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("")
    public String showEmailForm() {
        return "email-form";
    }

    @PostMapping("send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject,
                            @RequestParam String content, Model model) {
        emailService.sendSimpleEmail(to, subject, content);
        model.addAttribute("message", "Email sent successfully!");
        return "email-result";
    }
}
