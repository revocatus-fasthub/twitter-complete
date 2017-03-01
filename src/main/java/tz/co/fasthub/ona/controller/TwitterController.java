package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 2/24/17.
 */

@Controller
public class TwitterController {

    private static final String API_KEY = "SD0V7gUiCvpAxo5crYoZJ5xAr";
    private static final String API_SECRET = "SJke2RFXOyuUMIi4iOubEWJIE3MOGxNDaNvIEvtwsg2AnSpoPi";
    private static final String CALLBACK_URL = "http://127.0.0.1:8080/twitter/connected";
    private static final String REQUEST_TOKEN_NAME = "requestToken";
    private static final String TOKEN_NAME = "twitterToken";

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    Twitter twitter;

    @RequestMapping("/twitter/viewTweets")
    public String tw(HttpServletRequest request, Model model) {
        OAuthToken token = (OAuthToken) request.getSession().getAttribute(TOKEN_NAME);
        if(token == null) {

            log.error("twitter is null");
            log.error(".. meaning twitter is not working");

            return "redirect:/twitter/connected";
        }

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        Connection<Twitter> connection = connectionFactory.createConnection(token);
        Twitter twitter = connection.getApi();
        if( ! twitter.isAuthorized()) {
            log.error("twitter is not authorized");

            return "redirect:/twitter/connected";
        }

        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline();
        model.addAttribute("tweets", tweets);

        log.error("you can view tweets");

        return "redirect:/twitter/viewTweets";
    }

    @RequestMapping("/twitter/connect")
    public void connect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        OAuthToken requestToken = oauthOperations.fetchRequestToken(CALLBACK_URL, null);
        request.getSession().setAttribute(REQUEST_TOKEN_NAME, requestToken);
        String authorizeUrl = oauthOperations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE);

        response.sendRedirect(authorizeUrl);
    }

    @RequestMapping("/callback")
    public String callback(String oauth_token, String oauth_verifier, HttpServletRequest request) {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);

        OAuthToken requestToken = (OAuthToken) request.getSession().getAttribute(REQUEST_TOKEN_NAME);
        OAuth1Operations oAuthOperations = connectionFactory.getOAuthOperations();
        OAuthToken token = oAuthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauth_verifier), null);

        request.getSession().setAttribute(TOKEN_NAME, token);

        return "redirect:/twitter/connected";
    }

}