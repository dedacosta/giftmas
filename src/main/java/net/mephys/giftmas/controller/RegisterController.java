package net.mephys.giftmas.controller;

import net.mephys.giftmas.execption.ExceededEntryException;
import net.mephys.giftmas.service.ControlCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class RegisterController {

    @Autowired
    ControlCenterService controlCenterService;

    @GetMapping("/")
    public String showEmailForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, Model model) throws IOException, ExceededEntryException {
        controlCenterService.addEntry(name, email);
        model.addAttribute("message", "Email sent successfully!");
        return "register-result";
    }

}
