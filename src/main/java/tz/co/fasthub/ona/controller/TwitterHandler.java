package tz.co.fasthub.ona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
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

    public static void processVideo(Twitter twitter, Payload payload, MultipartFile file ){

        TwitterResponse twitterResponse=TwitterManualController.postINITCommandToTwitter(twitter, file);

        TwitterManualController.postAPPENDCommandToTwitter(twitter,payload,file,twitterResponse);

        TwitterManualController.postFINALIZECommandToTwitter(twitter,twitterResponse);

    }

}
