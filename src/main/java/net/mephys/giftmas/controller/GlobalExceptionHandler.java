package net.mephys.giftmas.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView  handleGeneralException(Exception ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error"); // "error" is the name of your error page.
        model.addAttribute("message", ex.getMessage());
        return modelAndView;
    }
}
