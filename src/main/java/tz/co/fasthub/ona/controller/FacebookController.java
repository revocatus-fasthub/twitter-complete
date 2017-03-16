package tz.co.fasthub.ona.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
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

    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String facebookConnection(Model model){
        if(connectionRepository.findPrimaryConnection(Facebook.class)==null){
            return "redirect:/connect/facebook";
        }
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);
        return "/facebook/feeds";
     //   return "/connect/facebookConnected";
    }

    @RequestMapping(value = "/feeds",method = RequestMethod.GET)
    public String feeds(Model model){
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
      //  model.addAttribute(facebook.userOperations().getUserProfile());
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("feed", feed);

        return "/facebook/feeds";

    }

}
