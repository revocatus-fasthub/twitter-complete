package tz.co.fasthub.ona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.TwitterTalentAccount;
import tz.co.fasthub.ona.domain.twitter.TwitterResponse;
import tz.co.fasthub.ona.service.TwitterTalentService;

import java.io.File;

/**
 * Created by daniel on 3/31/17.
 */
@Controller
public class TwitterHandler {

    @Autowired
    TwitterTalentService twitterTalentService;

    public static void processVideo(Twitter twitter, Payload payload, Resource file , String mediaType){

        TwitterResponse twitterResponse=TwitterManualController.postINITCommandToTwitter(twitter, file, mediaType);

        TwitterManualController.postAPPENDCommandToTwitter(twitter,payload,twitterResponse);

        TwitterManualController.postFINALIZECommandToTwitter(twitter,twitterResponse);

    }

    public void updateTwitterParameters(Payload payload){


        payload.getTwitterTalentAccount().setImageUrl(imageUrl);
        payload.getTwitterTalentAccount().setDisplayName(displayName);
        payload.getTwitterTalentAccount().setProfileUrl(profileUrl);
        payload.getTwitterTalentAccount().setAccessToken(TwitterManualController.accessToken);
        payload.getTwitterTalentAccount().setAppsAccessToken(providerUserId);
        payload.getTwitterTalentAccount().setAppsAccessTokenSecret(accTokenSecret);
        payload.getTwitterTalentAccount().setRequestTokenSecret(requestToken.getSecret());
        payload.getTwitterTalentAccount().setRequestTokenValue(requestToken.getValue());

        twitterTalentService.save(payload.getTwitterTalentAccount());

    }

}
