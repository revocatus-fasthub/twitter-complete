package tz.co.fasthub.ona.controller;

import org.springframework.core.io.Resource;
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

    public static void processVideo(Twitter twitter, Payload payload, MultipartFile file, Resource resource){
        TwitterResponse statusCommandTwitterResponse;

        TwitterResponse twitterResponse=TwitterManualController.postINITCommandToTwitter(twitter, file , resource);

        TwitterManualController.postAPPENDCommandToTwitter(twitter,payload,resource,twitterResponse);

        TwitterResponse twitterResponse1=TwitterManualController.postFINALIZECommandToTwitter(twitter,twitterResponse);

        if (twitterResponse1!=null&& twitterResponse1.getProcessing_info()!=null){
            if (twitterResponse1.getProcessing_info().getState().equals("pending")||twitterResponse1.getProcessing_info().getState().equals("in_progress")){
                try {
                    Thread.sleep(5000);
                    statusCommandTwitterResponse= TwitterManualController.postSTATUSCommandToTwitter(twitter,twitterResponse);
                    while (statusCommandTwitterResponse!=null&&statusCommandTwitterResponse.getProcessing_info()!=null&&(statusCommandTwitterResponse.getProcessing_info().getState().equals("pending")||statusCommandTwitterResponse.getProcessing_info().getState().equals("in_progress"))){
                        statusCommandTwitterResponse= TwitterManualController.postSTATUSCommandToTwitter(twitter,twitterResponse);
                        Thread.sleep(10000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
