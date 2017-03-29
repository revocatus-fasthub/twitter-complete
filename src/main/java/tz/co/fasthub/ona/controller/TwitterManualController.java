package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import tz.co.fasthub.ona.domain.twitter.TwitterPayload;

/**
 * Created by root on 3/28/17.
 */
@Controller
public class TwitterManualController {

   private static String url = "https://upload.twitter.com/1.1/media/upload.json";

    private static final Logger log = LoggerFactory.getLogger(TwitterManualController.class);

    private TwitterPayload twitterPayload;

    @RequestMapping(value = "/twitter/manual", method = RequestMethod.GET)
    public String querying(){

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("command", "INIT");
        parts.add("total_bytes","10240");
        parts.add("media_type","image/jpeg");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<Object> entity = new HttpEntity<Object>(parts,headers);



        RestTemplate restTemplate = new RestTemplate();
        TwitterPayload payload = restTemplate.postForObject(url,entity,TwitterPayload.class);
        log.info("success: "+payload);

        return "/success";
    }




}
