package tz.co.fasthub.ona.controller;

import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.twitter.TwitterResponse;

import java.io.File;

/**
 * Created by daniel on 3/31/17.
 */
@Controller
public class TwitterVideoHandler {

    public static void processVideo(Twitter twitter, Payload payload, Resource file , String mediaType){

        TwitterResponse twitterResponse=TwitterManualController.postINITCommandToTwitter(twitter, file, mediaType);

        TwitterManualController.postAPPENDCommandToTwitter(twitter,payload,twitterResponse);

        TwitterManualController.postFINALIZECommandToTwitter(twitter,twitterResponse);

    }
}
