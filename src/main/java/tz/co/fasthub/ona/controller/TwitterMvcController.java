package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import tz.co.fasthub.ona.domain.Talent;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.repository.UsersConnectionRepository;
import tz.co.fasthub.ona.service.TalentService;
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

        populateTwitterParams(token, connection, oauth_verifier, token);

        TwitterManualController.accessToken = token.getValue();
        log.info("user's access token is: " + TwitterManualController.accessToken);

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


    private void populateTwitterParams(OAuthToken token, Connection<Twitter> connection, String oauth_verifier,OAuthToken ouathRequestToken) {

        Talent talent=talentService.findByTwitterScreenName(connection.getDisplayName());
        if (talent!=null) {
            TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(connection.getDisplayName());

            if (twitterTalentAccount != null) {
                twitterTalentAccount.setImageUrl(connection.getImageUrl());
                twitterTalentAccount.setDisplayName(connection.getDisplayName());
                twitterTalentAccount.setProfileUrl(connection.getProfileUrl());
                twitterTalentAccount.setAccessToken(token.getValue());
                twitterTalentAccount.setAppsAccessToken(connection.getKey().getProviderId());
                twitterTalentAccount.setAppsAccessTokenSecret(token.getValue());
                twitterTalentAccount.setRequestTokenValue(ouathRequestToken.getValue());
                twitterTalentAccount.setRequestTokenSecret(ouathRequestToken.getSecret());
                twitterTalentAccount.setTalent(talent);
                twitterTalentAccount.setOauth_verifier(oauth_verifier);

                twitterTalentService.save(twitterTalentAccount);
            }
        }


    }


}
