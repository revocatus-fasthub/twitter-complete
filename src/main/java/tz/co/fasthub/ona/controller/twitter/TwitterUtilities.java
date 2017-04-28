package tz.co.fasthub.ona.controller.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tz.co.fasthub.ona.controller.TwitterMvcController;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.service.ImageService;
import tz.co.fasthub.ona.service.TwitterService;
import tz.co.fasthub.ona.service.TwitterTalentService;

import javax.inject.Inject;

/**
 * Created by daniel on 4/26/17.
 */
@Controller
public class TwitterUtilities {

    private Twitter twitter;

    public TwitterUtilities() {}

    private  TwitterTalentService twitterTalentService;

    private static final Logger log = LoggerFactory.getLogger(TwitterUtilities.class);

    @Inject
    public TwitterUtilities(Twitter twitter, TwitterTalentService twitterTalentService) {
        this.twitter = twitter;
        this.twitterTalentService = twitterTalentService;
    }

    public Twitter connectTwitter(String twitterDisplayName){

        try {
            TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(twitterDisplayName);

            if (twitterTalentAccount!=null) {
                TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(TwitterMvcController.getApiKey(), TwitterMvcController.getApiSecret());

                OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

                OAuthToken requestToken = new  OAuthToken(twitterTalentAccount.getRequestTokenValue(),twitterTalentAccount.getRequestTokenSecret());

                Connection<Twitter> connection = connectionFactory.createConnection(requestToken);
                Twitter twitter = connection.getApi();

                log.info("logging: "+twitter);

                if (!twitter.isAuthorized()) {

                }else {
                    twitter.timelineOperations().updateStatus("Hi Thursday");
                }
                return twitter;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @RequestMapping("/twitter/test/token")
    public String success() {
        connectTwitter("@devFasthub");

        return "/success";
    }

}
