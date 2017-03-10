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
    }


    @RequestMapping("/twitter/connected")
    public String connected(){
        return "twitter/connected";
    }

    @RequestMapping("/twitter/profile")
    public String profile(){
        return "twitter/profile";
    }

    @RequestMapping("/talent/viewTweets")
    public String viewTweets() {
        return "talent/viewTweets";
    }

    @RequestMapping("/twitter/success")
    public String success() {
        return "twitter/success";
    }


    @RequestMapping(value = "/messages")
    public String showMessages() {
        return "/twitter/success";
    }

    @RequestMapping("/twitter/renderPostTweet/form")
    public String tweet(){
        return "/twitter/postTweet";
    }

}


