package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.service.TwitterService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Autowired
    TwitterService twitterService;

    //Save the uploaded file to this folder
    private static String UPLOAD_ROOT = "upload-dir";
    private static String UPLOADED_FOLDER = "/home/naamini/Downloads/ona_app/src/main/resources/uploads/";//F://temp//

    private static final String BASE_PATH = "/images";
    private static final String FILENAME = "{filename:.+}";

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    //String URL ="https://api.twitter.com";

    @Inject
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/next")
    public String index(Model model, Pageable pageable) throws IOException {
        final Page<Payload> page = twitterService.findPayloadPage(pageable);
        model.addAttribute("page", page);
        if (page.hasPrevious()) {
            model.addAttribute("prev", pageable.previousOrFirst());
        }
        if (page.hasNext()) {
            model.addAttribute("next", pageable.next());
        }
        return "/twitter/success";
    }

   /*
    @RequestMapping(value = "/listTweets")
    public String showUsers(Model model) {
   //     final Page<Payload> tweetpage = twitterService.findPayloadPage(pageable);
        model.addAttribute("tweetpage", twitterService.listAllTweets());
        return "/twitter/listTweets";
    }

    */
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
        DirectMessage directMessage = twitter.directMessageOperations().sendDirectMessage("devFastHub","this must work");
        model.addAttribute("directMessage",directMessage);

        return "/twitter/success";
    }

    //POSTING TWEET DIRECTLY TO USER ACCOUNT
/*
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
 */


    //POSTING TWEET USING A FORM
/*
    @RequestMapping(value = "/postTweet", method = RequestMethod.POST)
    public String tweet(@ModelAttribute(value = "tweet") Payload payload, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) throws Exception {
      log.info("connecting ... payload: "+payload);
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            log.error("no connection to twitter");
            return "redirect:/twitter/renderPostTweet/form";
        }
        else if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.message", "Message was Not sent  => error details: " + bindingResult.getFieldError().toString());
            return "redirect:/twitter/renderPostTweet/form";
        }

        twitterService.postTweet(payload);

        Tweet tweets = twitter.timelineOperations().updateStatus(payload.getMessage("message"));
        redirectAttributes.addFlashAttribute("flash.message","Tweet successfully posted => Tweet: "+payload.getMessage("message"));
        log.info("Tweet posted successfully");
        return "redirect:/twitter/success";
   }
 */

   //POSTING TWEET AND AN IMAGE FILE TO USER ACCOUNT
    @RequestMapping(value = "/postTweet",method = RequestMethod.POST)
    public String uploadAndTweet(@RequestParam("file") MultipartFile file, Payload payload, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            log.error("no connection to twitter");
            return "redirect:/twitter/renderPostTweet/form";
        }else if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("flash.message", "Please select a file!");
            return "redirect:/twitter/renderPostTweet/form";
        }else {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());

                    //   twitterService.createImage(file);
                    Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
                    log.error("error in creating image");
                    log.info("imebeba file");


                    //saving the tweet to DB
                    twitterService.postTweet(payload);


                    Tweet tweet = twitter.timelineOperations().updateStatus(payload.getMessage("message"), (Resource)path);
                    log.info("tweet sent");
                    log.error("not sent");
                    redirectAttributes.addFlashAttribute("flash.message", "Successfully uploaded");

                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("flash.message", "Failed to upload" + file.getOriginalFilename() + "=>" + e.getMessage());
                }
            }
        return "redirect:/twitter/next";
    }

}//main class