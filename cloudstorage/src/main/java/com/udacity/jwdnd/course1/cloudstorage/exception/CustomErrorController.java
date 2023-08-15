package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Return the view name for your custom error page
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}