package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by root on 3/16/17.
 */
@Controller
@RequestMapping("/facebook")
public class FacebookController {

   private Facebook facebook;
   private ConnectionRepository connectionRepository;

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);


    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String facebookConnection(){
        if(connectionRepository.findPrimaryConnection(Facebook.class)==null){
            return "redirect:/connect/facebook";
        }
        return "/connect/facebookConnected";
    }

    @RequestMapping(value = "/feeds",method = RequestMethod.GET)
    public String feeds(Model model){
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "/facebook/feeds";
    }

    @RequestMapping(value = "/friends",method = RequestMethod.GET)
    public String friends(Model model){
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
      //List<Reference> friends = facebook.friendOperations().getFriends();

        PagedList<User> friends = facebook.friendOperations().getFriendProfiles();
        model.addAttribute("friends", friends);

        return "/facebook/viewFriends";
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    public String updateStatus(){
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
       // facebook.pageOperations().postPhoto()
       facebook.feedOperations().updateStatus("spring social is fun");
        return "/connect/facebookConnected";
    }

}
