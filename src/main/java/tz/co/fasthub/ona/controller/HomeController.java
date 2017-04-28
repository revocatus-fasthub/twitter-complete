package tz.co.fasthub.ona.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tz.co.fasthub.ona.domain.Talent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 2/23/17.
 */
@Controller
public class HomeController {
    //HOME
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/success")
    public String successManual() {
        return "success";
    }

     @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/welcome";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
    //TALENT
    @RequestMapping("/talent/addTalent")
    public String addTalent() {
        return "talent/addTalent";
    }

    @RequestMapping("/talent/talentForm")
    public String updateForm(Model model, Talent talent){
       // model.addAttribute("talent",talent);
        return "talent/talentForm";
    }
    //TWITTER
    @RequestMapping("/twitter/connected")
    public String connected(){
        return "twitter/connected";
    }

    @RequestMapping("/twitter/profile")
    public String profile(){
        return "twitter/profile";
    }


    @RequestMapping("/twitter/viewTweets")
    public String viewTweets() {
        return "talent/viewTweets";
    }

    @RequestMapping(value = "/messages")
    public String showMessages() {
        return "/twitter/success";
    }

    @RequestMapping("/twitter/renderPostTweet/form")
    public String tweet(){
        return "twitter/postTweetImage";
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


