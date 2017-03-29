package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
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
import tz.co.fasthub.ona.domain.twitter.TwitterPayload;

import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

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

    private static final Logger log = LoggerFactory.getLogger(TwitterManualController.class);
    public static String accessToken;

    private TwitterPayload twitterPayload;
    static File file;


    @RequestMapping(value = "/twitter/manual", method = RequestMethod.GET)
    public String querying(Model model){

        return "/success";
    }

    public static void postTwitter(Twitter twitter) {
        try {
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("command", "INIT");
            parts.add("total_bytes","10240");
            parts.add("media_type","image/jpeg");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.set(HttpHeaders.AUTHORIZATION,"Bearer "+accessToken);

            HttpEntity<?> entity = new HttpEntity<Object>(parts,headers);

           Object payload = twitter.restOperations().postForObject(DOMAIN+RESOURCE,entity,Object.class);
            log.info("init: "+payload.toString());
        } catch (RestClientException e) {
            log.error("RestClientException: ", e);
        }catch (Exception e){
            log.error("Exception: ", e);
        }
    }

/*

    public static void postTwitterAPPEND(Twitter twitter){
    //String media_id = Integer.parseInt(payload.)

        try {
            final FormDataMultiPart form = new FormDataMultiPart();
            form.field("command", "APPEND");
            form.field("media_id", media_id);
            form.field("segment_index", "0");

            final FileDataBodyPart filePart = new FileDataBodyPart("media", file);

            final FormDataMultiPart multiPartForm = (FormDataMultiPart) form.bodyPart(filePart);

            Object payload = twitter.restOperations().postForObject(DOMAIN,multiPartForm,Object.class);
            log.info("appending: "+payload);

        }catch (RestClientException e) {
            log.error("RestClientException: ", e);
        }catch (Exception e){
            log.error("Exception: ", e);
        }
    }


 */
}
