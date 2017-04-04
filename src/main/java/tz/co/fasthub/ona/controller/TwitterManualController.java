package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.twitter.TwitterPayload;
import tz.co.fasthub.ona.domain.twitter.TwitterResponse;
import tz.co.fasthub.ona.service.VideoService;


import javax.inject.Inject;
import java.io.File;

import static org.springframework.integration.support.management.graph.LinkNode.Type.output;

/**
 * Created by root on 3/28/17.
 */
@Controller
public class TwitterManualController {


    //   private static String url = "https://upload.twitter.com/1.1/media/upload.json";
    final static String DOMAIN = "https://upload.twitter.com";
    final static String RESOURCE = "/1.1/media/upload.json";
    @Autowired
    private  static   VideoService videoService;

    private static final Logger log = LoggerFactory.getLogger(TwitterManualController.class);
    public static String accessToken;

    private TwitterPayload twitterPayload;
    static File file;



    @RequestMapping(value = "/twitter/manual", method = RequestMethod.GET)
    public String querying(Model model) {

        return "/success";
    }

    public static void postTwitter(Twitter twitter) {
        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("command", "INIT");
            parts.add("total_bytes", "10240");
            parts.add("media_type", "image/jpeg");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+accessToken);

            HttpEntity<?> entity = new HttpEntity<Object>(parts, headers);

            TwitterResponse payload = twitter.restOperations().postForObject(DOMAIN + RESOURCE, entity, TwitterResponse.class);
            log.info("init: " + payload.toString());

            log.info("media_id ya Naamini: "+ payload.getMedia_id());
        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public static void postTwitterAppend(Twitter twitter, Payload payload) {
        try {

            for (int i = 0; i < 1; i++) {
                MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
                parts.add("command", "APPEND");
                parts.add("media_id", "10240");
                parts.add("media", videoService.findOneVideo(payload.getVideo().getName()));
                parts.add("segment_index", i);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<?> entity = new HttpEntity<Object>(parts, headers);

                Object responseData = twitter.restOperations().postForObject(DOMAIN , entity, Object.class);

                log.info("Append Command response: " + responseData.toString());

            }


        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

}
