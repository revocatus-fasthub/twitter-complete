package tz.co.fasthub.ona.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 2/23/17.
 */
@Controller
public class HomeController {
    //HOMR
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/success")
    public String successManual() {
        return "success";
    }

    //TALENT
    @RequestMapping("/talent/addTalent")
    public String addTalent() {
        return "talent/addTalent";
    }

    @RequestMapping("/talent/talentForm")
    public String talentForm() {
        return "talent/talentForm";
    }

    /*@RequestMapping("/talent/talentForm")
    public String updateForm(){
        return "talent/talentForm";
    }*/

    //TWITTER
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
        return "/twitter/postTweetImage";
    }

    @RequestMapping("/twitter/postvideo/form")
    public String tweetVideo(){
        return "/twitter/postTweetVideo";
    }

    //FACEBOOK
    @RequestMapping("/connect/facebookConnected")
    public String fbConnected(){
        return "/connect/facebookConnected";
    }

    @RequestMapping("/facebook/feeds")
    public String fbfeeds(){
        return "/facebook/feeds";
    }

    @RequestMapping("/facebook/viewFriends")
    public String fbfriends(){
        return "/facebook/viewFriends";
    }

}


