package tz.co.fasthub.ona.controller.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import tz.co.fasthub.ona.controller.TwitterMvcController;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.service.TwitterTalentService;

import javax.inject.Inject;

/**
 * Created by daniel on 4/26/17.
 */

@Controller
public class TwitterUtilities {


    public TwitterUtilities() {
    }


    private  TwitterTalentService twitterTalentService;


    @Inject
    public TwitterUtilities(TwitterTalentService twitterTalentService) {
        this.twitterTalentService = twitterTalentService;
    }

    public  Twitter connectTwitter(String twitterDisplayName){

        try {
            TwitterTalentAccount twitterTalentAccount=twitterTalentService.getTalentByDisplayName(twitterDisplayName);

            if (twitterTalentAccount!=null) {
                OAuthToken token = (OAuthToken) ((Object) twitterTalentAccount.getAccessToken());
                if (token == null) {
                }

                TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(TwitterMvcController.getApiKey(), TwitterMvcController.getApiSecret());
                Connection<Twitter> connection = connectionFactory.createConnection(token);
                Twitter twitter = connection.getApi();
                if (!twitter.isAuthorized()) {
                }

                return twitter;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void  connector(String displayName){
        connectTwitter(displayName);

    }
}
