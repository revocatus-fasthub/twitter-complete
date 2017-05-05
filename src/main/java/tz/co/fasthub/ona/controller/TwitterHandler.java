package tz.co.fasthub.ona.controller;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.twitter.TwitterResponse;

/**
 * Created by daniel on 3/31/17.
 */

@Controller
public class TwitterHandler {

    public static void processVideo(Twitter twitter, Payload payload, MultipartFile file ){

        TwitterResponse twitterResponse=TwitterManualController.postINITCommandToTwitter(twitter, file);

        TwitterManualController.postAPPENDCommandToTwitter(twitter,payload,file,twitterResponse);

        TwitterManualController.postFINALIZECommandToTwitter(twitter,twitterResponse);

    }

}
