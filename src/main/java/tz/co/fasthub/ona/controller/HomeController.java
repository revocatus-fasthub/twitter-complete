package tz.co.fasthub.ona.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 2/23/17.
 */
@Controller
public class HomeController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/talent/addTalent")
    public String addTalent() {
        return "talent/addTalent";
    }}
