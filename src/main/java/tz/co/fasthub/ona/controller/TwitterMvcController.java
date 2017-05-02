package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tz.co.fasthub.ona.controller.twitter.TwitterUtilities;
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.repository.UsersConnectionRepository;
import tz.co.fasthub.ona.service.TalentService;
import tz.co.fasthub.ona.service.TwitterTalentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.List;

/**
 * Created by daniel on 3/26/17.
 */

@Controller
public class TwitterMvcController {

    private static final String API_KEY = "oR9ZSqmD9uqSz33iI8hgmptl3";
    private static final String API_SECRET = "dW69QN3GUQ54SUH2m7U5nqXNRn4wazybpkSCZAuDdrOn4iBrNt";
    private static final String CALLBACK_URL = "http://localhost:8080/tw/callback";
    private static final String REQUEST_TOKEN_NAME = "requestToken";
    private static final String TOKEN_NAME = "twitterToken";

    private static final Logger log = LoggerFactory.getLogger(TwitterMvcController.class);
    @Autowired
    TwitterTalentService twitterTalentService;

    @Autowired
    TalentService talentService;
    UsersConnectionRepository usersConnectionRepository;
    private Twitter twitter;
    private TwitterTemplate twitterTemplate;

    public TwitterMvcController(Twitter twitter) {

        this.twitter = twitter;

    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getApiSecret() {
        return API_SECRET;
    }

    public static String getCallbackUrl() {
        return CALLBACK_URL;
    }

    @RequestMapping("/tw")
    public String tw(Model model, HttpServletRequest request) {
        OAuthToken token = (OAuthToken) request.getSession().getAttribute(TOKEN_NAME);
        String oauth_verifier = (String) request.getSession().getAttribute("oauth_verifier");

        if (token == null) {
            return "redirect:/tw/login";
        }

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        Connection<Twitter> connection = connectionFactory.createConnection(token);
        Twitter twitter = connection.getApi();
        if (!twitter.isAuthorized()) {
            return "redirect:/tw/login";
        }

        populateTwitterParams(token, connection, oauth_verifier);

        model.addAttribute(TOKEN_NAME, token.getValue());

        return "connect/twitterConnected";
    }


    @RequestMapping("/tw/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        OAuthToken requestToken = oauthOperations.fetchRequestToken(CALLBACK_URL, null);

        request.getSession().setAttribute(REQUEST_TOKEN_NAME, requestToken);
        log.info("...-..." + requestToken);
        String authorizeUrl = oauthOperations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE);

        response.sendRedirect(authorizeUrl);

    }

    @RequestMapping("/tw/callback")
    public String callback(String oauth_token, String oauth_verifier, HttpServletRequest request) {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);

        OAuthToken oAuthToken = (OAuthToken) request.getSession().getAttribute(REQUEST_TOKEN_NAME);
        OAuth1Operations oAuthOperations = connectionFactory.getOAuthOperations();
        OAuthToken accessToken = oAuthOperations.exchangeForAccessToken(new AuthorizedRequestToken(oAuthToken, oauth_verifier), null);
        request.getSession().setAttribute(TOKEN_NAME, accessToken);
        request.getSession().setAttribute("oauth_verifier", oauth_verifier);

        return "redirect:/tw";
    }


    private void populateTwitterParams(OAuthToken token, Connection<Twitter> connection, String oauth_verifier) {

        Talent talent=talentService.findByTwitterScreenName(connection.getDisplayName());
        if (talent!=null) {
            TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(connection.getDisplayName());

            if (twitterTalentAccount != null) {
                twitterTalentAccount.setImageUrl(connection.getImageUrl());
                twitterTalentAccount.setDisplayName(connection.getDisplayName());
                twitterTalentAccount.setProfileUrl(connection.getProfileUrl());
                twitterTalentAccount.setAccessToken(token.getValue());
                twitterTalentAccount.setRequestTokenSecret(token.getSecret());
                twitterTalentAccount.setTalent(talent);
                twitterTalentAccount.setOauth_verifier(oauth_verifier);

                twitterTalentService.save(twitterTalentAccount);
            }
        }
    }

    @GetMapping("/tw/viewTweets/{twitterScreenName}")
    public String viewTweets(@PathVariable String twitterScreenName, Model model) {

        TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(twitterScreenName);

        if(twitterTalentAccount!=null){
            Twitter twitter1 = TwitterUtilities.getTwitter(twitterTalentAccount);
            model.addAttribute(twitter1.userOperations().getUserProfile());
            List<Tweet> tweets = twitter1.timelineOperations().getUserTimeline();
            model.addAttribute("tweets",tweets);
        }else {
            //handling errors
        }

        return "twitter/viewTweets";
    }


    @GetMapping("/tw/friends/{twitterScreenName}")
    public String friendList(@PathVariable String twitterScreenName, Model model) {
        TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(twitterScreenName);

        if(twitterTalentAccount!=null){
            Twitter twitter1 = TwitterUtilities.getTwitter(twitterTalentAccount);
            model.addAttribute(twitter1.userOperations().getUserProfile());
            CursoredList<TwitterProfile> friends = twitter1.friendOperations().getFriends();
            model.addAttribute("friends", friends);
        }else {
            //handling
        }

        return "twitter/viewFriendList";
    }


    @GetMapping("/tw/followers/{twitterScreenName}")
    public String followers(@PathVariable String twitterScreenName, Model model) {
        TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(twitterScreenName);

        if(twitterTalentAccount!=null) {
            Twitter twitter1 = TwitterUtilities.getTwitter(twitterTalentAccount);
            model.addAttribute(twitter1.userOperations().getUserProfile());
            CursoredList<TwitterProfile> followers = twitter1.friendOperations().getFollowers();
            model.addAttribute("followers", followers);
        }else {
            //handling
        }
        return "twitter/followersList";
    }

    //timeline
    @RequestMapping(value="/twitter/search", method= RequestMethod.GET)
    public String searchOperations(@RequestParam("query") String query, Model model) {
        model.addAttribute("timeline", twitter.searchOperations().search(query).getTweets());
        return "twitter/timeline";
    }

    @RequestMapping(value="/tw/timelineShow/{twitterScreenName}", method=RequestMethod.GET)
    public String showTimeline(@PathVariable String twitterScreenName, Model model) {
     //   showTimeline("Home", model);
        model.addAttribute("talent", talentService.findByTwitterScreenName(twitterScreenName));
        return "twitter/timeline";
    }

    @RequestMapping(value="/tw/timeline/{twitterScreenName}/{timelineType}", method=RequestMethod.GET)
    public String showTimeline(@PathVariable String timelineType, @PathVariable String twitterScreenName, Model model) {

        TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(twitterScreenName);

        if(twitterTalentAccount!=null) {
            Twitter twitter1 = TwitterUtilities.getTwitter(twitterTalentAccount);

            if(timelineType.equals("Home")) {
                model.addAttribute("timeline", twitter1.timelineOperations().getHomeTimeline());
            } else if(timelineType.equals("User")) {
                model.addAttribute("timeline", twitter1.timelineOperations().getUserTimeline());
            } else if(timelineType.equals("Mentions")) {
                model.addAttribute("timeline", twitter1.timelineOperations().getMentions());
            } else if(timelineType.equals("Favorites")) {
                model.addAttribute("timeline", twitter1.timelineOperations().getFavorites());
            }
            model.addAttribute("timelineName", timelineType);
        }else {
            //handling
        }

        return "twitter/timeline";
    }



    @PostMapping("/disconnectUrl")
    public String disconnectTwitter(){
        return "/connect/twitterConnect";
    }



}
