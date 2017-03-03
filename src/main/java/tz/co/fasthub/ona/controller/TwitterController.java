package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.domain.PayLoad;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    String URL ="https://api.twitter.com/1.1/";

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
       // twitter.directMessageOperations().sendDirectMessage("devFastHub", "You going to the Dolphins game?");
        //twitter.timelineOperations().updateStatus("I'm tweeting from Mbeya!");
       // return "/twitter/success";
       return "/connect/twitterConnected";
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

    @RequestMapping(value = "/followers", method = RequestMethod.GET)
    public String followers(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/followersList";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> followers = twitter.friendOperations().getFollowers();
        model.addAttribute("followers", followers);
        return "/twitter/followersList";
    }


    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String directMsg(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/sendDirectMessage";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        DirectMessage directMessage = twitter.directMessageOperations().sendDirectMessage("devFastHub","it's nemy");
        model.addAttribute("directMessage",directMessage);

        return "/twitter/success";
    }

    @RequestMapping(value = "/tweet", method = RequestMethod.GET)
    public String postTweet(Model model, RedirectAttributes redirectAttributes) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/postTweet";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        Tweet tweets = twitter.timelineOperations().updateStatus("i'm using spring social!");
        model.addAttribute("tweets",tweets);
        redirectAttributes.addFlashAttribute("flash.message", "Message was successfully created => Message: " + tweets);

        return "/twitter/success";
    }

}//main class

  /*
    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String directMsg(@ModelAttribute("sendMessage") PayLoad payLoad, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
                return "redirect:/sendDirectMessage";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.message", "NO message entered  => error details: " + bindingResult.getFieldError().toString());
            return "redirect:/messages";
        }else {
            try {

                HttpHeaders headers = new HttpHeaders();
               // RestTemplate restTemplate = new RestTemplate();

                headers.setContentType(MediaType.APPLICATION_JSON);

                // PayLoad payLoads = new PayLoad(payLoad.getTwitterScreenName(),payLoad.getMessage());
                DirectMessage directMessage = twitter.directMessageOperations().sendDirectMessage(payLoad.getTwitterScreenName(),payLoad.getMessage());
                HttpEntity<PayLoad> entity = new HttpEntity<PayLoad>((MultiValueMap<String, String>) directMessage);
                //PayLoad responsePayload = restTemplate.postForEntity(URL,entity,PayLoad.class);


            }catch (Exception e){
                log.error("Sending Failed",e);
            }
            redirectAttributes.addFlashAttribute("flash.message", "Message was successfully sent => Message: " + payLoad);

            return "redirect:/messages";
        }

   */

       /*
         model.addAttribute(twitter.userOperations().getUserProfile());
        DirectMessage directMessage = twitter.directMessageOperations().sendDirectMessage("devFastHub","hi dev");
        model.addAttribute("directMessage",directMessage);
       // model.addAttribute(twitter.userOperations().getUserProfile());
         */
        //twitter.directMessageOperations().sendDirectMessage(, "You going to the Dolphins game?");
   //     model.addAttribute("followers", followers);
     //   return "/twitter/sendDirectMessage";