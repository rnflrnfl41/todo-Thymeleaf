package me.potato.finaltodo.controller;

import me.potato.finaltodo.service.exceptions.CommonException;
import me.potato.finaltodo.service.exceptions.NoUserInfoInSessionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandelExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView HandleException(CommonException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg",e.getMessage());
        mav.setViewName("/error");
        return mav;
    }

}
