package com.example.doantotnghiep_tranhuytung.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorSysTemController {
    @GetMapping("/404")
    public String NotFound(){
        return "error/404";
    }
    @GetMapping("/403")
    public String NotAuthenticated(){
        return "error/403";
    }
}
