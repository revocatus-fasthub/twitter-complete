package tz.co.fasthub.ona.controller.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.controller.TwitterController;
import tz.co.fasthub.ona.controller.TwitterMvcController;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.service.ImageService;
import tz.co.fasthub.ona.service.TalentService;
import tz.co.fasthub.ona.service.TwitterService;
import tz.co.fasthub.ona.service.TwitterTalentService;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by daniel on 4/26/17.
 */

@Controller
public class TwitterUtilities {

    private Twitter twitter;

    @Autowired
    ImageService imageService;

    @Autowired
    TwitterService twitterService;
    public TwitterUtilities() {
    }

    private  TwitterTalentService twitterTalentService;

    private static final Logger log = LoggerFactory.getLogger(TwitterUtilities.class);

    @Inject
    public TwitterUtilities(Twitter twitter, TwitterTalentService twitterTalentService) {
        this.twitter = twitter;
        this.twitterTalentService = twitterTalentService;
    }

    public Twitter connectTwitter(String twitterDisplayName){

        try {
            TwitterTalentAccount twitterTalentAccount=twitterTalentService.getTalentByDisplayName(twitterDisplayName);

            if (twitterTalentAccount!=null) {
            //    OAuthToken token = (OAuthToken) ((Object) twitterTalentAccount.getAccessToken());
                TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(TwitterMvcController.getApiKey(), TwitterMvcController.getApiSecret());



                OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

                OAuthToken requestToken = oauthOperations.fetchRequestToken(TwitterMvcController.getCallbackUrl(), null);


                OAuth1Operations oAuthOperations = connectionFactory.getOAuthOperations();
                OAuthToken token = oAuthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken,twitterTalentAccount.getOauth_verifier()), null);


                Connection<Twitter> connection = connectionFactory.createConnection(token);
                Twitter twitter = connection.getApi();

                log.info("logging: "+twitter);

                if (!twitter.isAuthorized()) {

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
