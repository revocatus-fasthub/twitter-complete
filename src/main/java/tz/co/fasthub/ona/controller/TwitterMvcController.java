package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.repository.UsersConnectionRepository;
import tz.co.fasthub.ona.service.TwitterTalentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    private Twitter twitter;

    @Autowired
    TwitterTalentService twitterTalentService;

    UsersConnectionRepository usersConnectionRepository;

    TwitterTalentAccount twitterTalentAccount;

    private TwitterTemplate twitterTemplate;

    public TwitterMvcController(Twitter twitter) {

        this.twitter = twitter;

    }

    @RequestMapping("/tw")
    public String tw(Model model, HttpServletRequest request) {
        OAuthToken token = (OAuthToken) request.getSession().getAttribute(TOKEN_NAME);
        if(token == null) {
            return "redirect:/tw/login";
        }

//        twitterTalentAccount.getUsername(twitter.userOperations().getScreenName());
//        twitterTalentAccount.getAccessToken(token.getValue());

    //    twitterTalentService.save(twitterTalentAccount);

       // log.info("user's access token is: "+TwitterManualController.accessToken);

        model.addAttribute(TOKEN_NAME,token.getValue());

        return "connect/twitterConnected";
    }

/*

    @RequestMapping(value = "tweet/{search}/{count}",method= RequestMethod.GET)
    public String searchTwitter(Model model, @PathVariable String search, @PathVariable int count, TwitterTalentAccount twitterTalentAccount) {
        SearchResults results = twitterTemplate.searchOperations().search(
                new SearchParameters(search)
                        .resultType(SearchParameters.ResultType.RECENT)
                        .count(count));

        List<Tweet> tweets = results.getTweets();
        model.addAttribute("tweets", tweets);

        for (Tweet tweet : tweets) {
            twitterTalentAccount  = new twitterTalentAccount(tweet.getProfileImageUrl(), tweet.getCreatedAt(), tweet.getFromUser());
            twitterTalentService.save(twitterTalentAccount);
        }
        return "search";
    }

 */
    @RequestMapping("/tw/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        OAuthToken requestToken = oauthOperations.fetchRequestToken(CALLBACK_URL, null);
        request.getSession().setAttribute(REQUEST_TOKEN_NAME, requestToken);
        log.info("...-..."+requestToken);
        String authorizeUrl = oauthOperations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE);

        response.sendRedirect(authorizeUrl);

    }

    @RequestMapping("/tw/callback")
    public String callback(String oauth_token, String oauth_verifier, HttpServletRequest request) {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);

        OAuthToken requestToken = (OAuthToken) request.getSession().getAttribute(REQUEST_TOKEN_NAME);
        OAuth1Operations oAuthOperations = connectionFactory.getOAuthOperations();
        OAuthToken accessToken = oAuthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauth_verifier), null);
        request.getSession().setAttribute(TOKEN_NAME, accessToken);

        String accToken = accessToken.getValue();
        String accTokenSecret = accessToken.getSecret();

             twitter = new TwitterTemplate( API_KEY, API_SECRET, accToken, accTokenSecret );

        Connection<Twitter> connection = connectionFactory.createConnection(accessToken);
             twitter = connection.getApi();

             log.info("users's profile url: "+connection.getProfileUrl());
             String profileUrl = connection.getProfileUrl();
             log.info("user's image url: " +connection.getImageUrl());
             String imageUrl = connection.getImageUrl();
             log.info("user;s display name: " +connection.getDisplayName());
             String displayName = connection.getDisplayName();

        if( ! twitter.isAuthorized()) {
            return "redirect:/tw/login";
        }

        TwitterManualController.accessToken=accessToken.getValue();
            log.info("twitteManualController.accesstoken: "+TwitterManualController.accessToken);

        //providerUSerId==connection.getKey();
        String providerUserId = connection.getKey().getProviderUserId();
            log.info("App's Access Token: "+providerUserId);

        twitterTalentAccount.setImageUrl(imageUrl);
        twitterTalentAccount.setDisplayName(displayName);
        twitterTalentAccount.setProfileUrl(profileUrl);
        twitterTalentAccount.setAccessToken(TwitterManualController.accessToken);
        twitterTalentAccount.setAppsAccessToken(providerUserId);
        twitterTalentAccount.setAppsAccessTokenSecret(accTokenSecret);
        twitterTalentAccount.setRequestTokenSecret(requestToken.getSecret());
        twitterTalentAccount.setRequestTokenValue(requestToken.getValue());

//        usersConnectionRepository.createConnectionRepository(providerUserId);
/*
        TwitterTalentAccount userDetails = new TwitterTalentAccount(talent);
        userAuth = new SocialAuthenticationToken(connection, userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(userAuth);
*/

        log.info("IDK what this is: "+accessToken);

        log.info("App's Access Token Secret is: "+accTokenSecret);

        log.info("requestToken Secret: "+requestToken.getSecret());

        log.info("requestToken Value: "+requestToken.getValue());

        return "redirect:/tw";
    }



}
