package me.potato.finaltodo.controller;

import me.potato.finaltodo.service.exceptions.CommonException;
import me.potato.finaltodo.service.exceptions.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandelExceptionController {

    @ExceptionHandler(LoginException.class)
    public ModelAndView HandleLoginException(CommonException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg",e.getMessage());
        mav.setViewName("/error");
        return mav;
    }

}
