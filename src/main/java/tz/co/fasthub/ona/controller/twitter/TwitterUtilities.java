package tz.co.fasthub.ona.controller.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import tz.co.fasthub.ona.controller.TwitterMvcController;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;

/**
 * Created by daniel on 4/26/17.
 */

public class TwitterUtilities {
    public TwitterUtilities() {
    }

    private static final Logger log = LoggerFactory.getLogger(TwitterUtilities.class);



    public static Twitter connectTwitter(Payload payload,  TwitterTalentAccount twitterTalentAccount, Resource resource) {

        try {

            if (twitterTalentAccount != null) {
                TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(TwitterMvcController.getApiKey(), TwitterMvcController.getApiSecret());

                OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();

                OAuthToken requestToken = new OAuthToken(twitterTalentAccount.getRequestTokenValue(), twitterTalentAccount.getRequestTokenSecret());

                Connection<Twitter> connection = connectionFactory.createConnection(requestToken);
                Twitter twitter = connection.getApi();

                log.info("logging: " + twitter);

                if (!twitter.isAuthorized()) {

                } else {
                    TweetData tweetData = new TweetData(payload.getMessage());

                    if (payload.getImage() != null) {
                        tweetData.withMedia(resource);
                    }


                    twitter.timelineOperations().updateStatus(tweetData);
                }
                return twitter;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
