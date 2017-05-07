package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.twitter.TwitterPayload;
import tz.co.fasthub.ona.domain.twitter.TwitterResponse;
import tz.co.fasthub.ona.service.ImageService;
import tz.co.fasthub.ona.service.VideoService;


import java.io.File;
import java.util.Arrays;

/**
 * Created by root on 3/28/17.
 */
@Controller
public class TwitterManualController {


    //   private static String url = "https://upload.twitter.com/1.1/media/upload.json";
    final static String DOMAIN = "https://upload.twitter.com";
    final static String RESOURCE = "/1.1/media/upload.json";
    final static String UPDATE_STATUS_URL="https://api.twitter.com/1.1/statuses/update.json";
    @Autowired
    private  static ImageService imageService;

    private static final Logger log = LoggerFactory.getLogger(TwitterManualController.class);

    public static String accessToken;

    private TwitterPayload twitterPayload;

    static File file;

    @RequestMapping(value = "/twitter/manual", method = RequestMethod.GET)
    public String querying(Model model) {
        return "/success";
    }

    public static TwitterResponse postINITCommandToTwitter(Twitter twitter, MultipartFile file, Resource resource) {
        TwitterResponse payload = null;
        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("command", "INIT");

            String totalBytes=Integer.toString((int)resource.contentLength());

            parts.add("total_bytes", totalBytes);
            parts.add("media_type", file.getContentType());
//            parts.add("media_category", "amplify_video");


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<?> entity = new HttpEntity<Object>(parts, headers);


            payload = twitter.restOperations().postForObject(DOMAIN + RESOURCE, entity, TwitterResponse.class);
            log.info("init command response from Twitter: " + payload.toString()+" We sent total bytes: "+totalBytes);

            log.info("media_id featched from Twitter API: "+ payload.getMedia_id());
        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception Found:  ", e);
        }
        return payload;
    }

    public static void postAPPENDCommandToTwitter(Twitter twitter, Payload payload, Resource resource, TwitterResponse twitterResponse) {
        try {

            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();

            for (int i = 0; i < 1; i++) {


                parts.add("command", "APPEND");
                parts.add("media_id",twitterResponse.getMedia_id());
                parts.add("segment_index", i+"");
                parts.add("media", resource);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<?> entity = new HttpEntity<Object>(parts, headers);

                RestTemplate restTemplate = (RestTemplate) twitter.restOperations();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());


                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.MULTIPART_FORM_DATA));


                restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

                Object responseData = restTemplate.postForObject(DOMAIN+RESOURCE , entity, Object.class);

                log.info("Append Command response: " + responseData);

            }

        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    public static TwitterResponse postFINALIZECommandToTwitter(Twitter twitter, TwitterResponse twitterResponse) {

        TwitterResponse finalizeTwitterResponse=null;
        try {
            MultiValueMap<String, Object> finalize = new LinkedMultiValueMap<String, Object>();
            finalize.add("command", "FINALIZE");
            finalize.add("media_id",twitterResponse.getMedia_id());


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<?> entity = new HttpEntity<Object>(finalize, headers);

            finalizeTwitterResponse = twitter.restOperations().postForObject(DOMAIN+RESOURCE, entity, TwitterResponse.class);
            log.info("finalize response: " + finalizeTwitterResponse);

        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        return finalizeTwitterResponse;
    }


    public static TwitterResponse postSTATUSCommandToTwitter(Twitter twitter, TwitterResponse twitterResponse) {

        TwitterResponse statusTwitterResponse=null;
        try {
            String URL = DOMAIN+RESOURCE+"?"+"command=STATUS&media_id="+twitterResponse.getMedia_id();
            statusTwitterResponse = twitter.restOperations().getForObject(URL, TwitterResponse.class);

            log.info("Status response from Twitter: " + twitterResponse);

        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
        return statusTwitterResponse;
    }




    public static void updateStatus(Twitter twitter, TwitterResponse twitterResponse, Payload payload) {
        try {
            MultiValueMap<String, Object> finalize = new LinkedMultiValueMap<String, Object>();
            finalize.add("status", payload.getMessage());
            finalize.add("media_ids",twitterResponse.getMedia_id());


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<?> entity = new HttpEntity<Object>(finalize, headers);

            Object response = twitter.restOperations().postForObject(UPDATE_STATUS_URL, entity, Object.class);
            log.info("Updating Status Response: " + response);

        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }


}
