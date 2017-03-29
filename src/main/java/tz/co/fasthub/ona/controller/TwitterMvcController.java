package tz.co.fasthub.ona.controller;

import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private static final String CALLBACK_URL = "http://127.0.0.1:8080/tw/callback";
    private static final String REQUEST_TOKEN_NAME = "requestToken";
    private static final String TOKEN_NAME = "twitterToken";

    @RequestMapping("/tw")
    public String tw(Model model, HttpServletRequest request) {
        OAuthToken token = (OAuthToken) request.getSession().getAttribute(TOKEN_NAME);
        if(token == null) {
            return "redirect:/tw/login";
        }

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        Connection<Twitter> connection = connectionFactory.createConnection(token);
        Twitter twitter = connection.getApi();
        if( ! twitter.isAuthorized()) {
            return "redirect:/tw/login";
        }

        TwitterManualController.accessToken=token.getValue();

//        twitter.timelineOperations().
        TwitterManualController.postINITCommand(twitter);

        model.addAttribute(TOKEN_NAME,token.getValue());



        return "connect/twitterConnected";
    }

    @RequestMapping("/tw/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

        OAuthToken requestToken = oauthOperations.fetchRequestToken(CALLBACK_URL, null);
        request.getSession().setAttribute(REQUEST_TOKEN_NAME, requestToken);
        String authorizeUrl = oauthOperations.buildAuthenticateUrl(requestToken.getValue(), OAuth1Parameters.NONE);

        response.sendRedirect(authorizeUrl);
    }

    @RequestMapping("/tw/callback")
    public String callback(String oauth_token, String oauth_verifier, HttpServletRequest request) {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(API_KEY, API_SECRET);

        OAuthToken requestToken = (OAuthToken) request.getSession().getAttribute(REQUEST_TOKEN_NAME);
        OAuth1Operations oAuthOperations = connectionFactory.getOAuthOperations();
        OAuthToken token = oAuthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauth_verifier), null);

        request.getSession().setAttribute(TOKEN_NAME, token);

        return "redirect:/tw";
    }

}
