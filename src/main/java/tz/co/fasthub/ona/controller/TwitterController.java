package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    @Inject
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String twitterConnection(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        twitter.timelineOperations().updateStatus("I'm tweeting from Mbeya!");
        return "/twitter/success";
       // return "/connect/twitterConnected";
    }


    @RequestMapping(value = "/viewTweets",method = RequestMethod.GET)
    public String viewTweets(Model model){
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/viewTweets";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline();
        model.addAttribute("tweets",tweets);

        return "/twitter/viewTweets";

    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String friendList(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/viewFriendList";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        model.addAttribute("friends", friends);
        return "/twitter/viewFriendList";
    }



/*

    @RequestMapping(value = "/post", method=RequestMethod.GET)
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/post";
        }
        twitter.timelineOperations().updateStatus("I'm tweeting from Mbeya!");
        return "/twitter/success";
    }


    @RequestMapping(value = "/postTweet", method = RequestMethod.GET)
    public String postTweet(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/success";
        }
        StatusDetails statusDetails = new StatusDetails();
        log.error("error");

        twitter.timelineOperations().updateStatus("I'm tweeting from Dar!", statusDetails);

        //model.addAttribute(twitter.timelineOperations().updateStatus("Spring Social is bae!"));
        log.info("sent!");
        //model.addAttribute("friends", friends);
        return "/twitter/success";
    }


 */

}
