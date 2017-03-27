package tz.co.fasthub.ona.controller;

import org.apache.catalina.WebResource;
import org.glassfish.jersey.client.ClientResponse;
import org.springframework.social.facebook.api.Account;
import tz.co.fasthub.ona.domain.Video;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by root on 3/20/17.
 */
public class VideoUploadController {



    private static TwitterAdsClient getTwitterAdsClient() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/application.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String consumerKey = properties.getProperty("consumer.key");
        String consumerSecret = properties.getProperty("consumer.secret");
        String accessToken = properties.getProperty("access.token");
        String accessSecret = properties.getProperty("access.secret");
        return new TwitterAdsClient(consumerKey, consumerSecret, accessToken, accessSecret);
    }

    final static String DOMAIN = "https://upload.twitter.com";
    final static String RESOURCE = "/1.1/media/upload.json";


    final static TwitterAdsClient client = getTwitterAdsClient();
    final static String ACCOUNT_ID = "18ce54aq4d5";



    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/config.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        // String filePath = "C:/temp/doge.mp4";
        String filePath = "src/test/resources/doge.mp4";



        // 1. INIT the file upload
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();

        params.add("command", "INIT");
        params.add("media_type", "video/mp4");
        params.add("media_category", "amplify_video");

        File file = new File(filePath);
        int bytes = (int) file.length();
        String totalBytes = Integer.toString(bytes);

        params.add("total_bytes", totalBytes);

        ClientResponse response = webResource.path(RESOURCE)
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, params);

        int status = response.getStatus();

        if (status != 202 && status != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);
        System.out.println(output);

        JsonObject jsonObj = new JsonParser().parse(output).getAsJsonObject();
        String mediaId = jsonObj.get("media_id_string").getAsString();

        // 2. APPEND Using the media_id perform the binary upload

        final FormDataMultiPart form = new FormDataMultiPart();

        form.field("command", "APPEND");
        form.field("media_id", mediaId);
        form.field("segment_index", "0");

        final FileDataBodyPart filePart = new FileDataBodyPart("media", file);

        final FormDataMultiPart multiPartForm = (FormDataMultiPart) form.bodyPart(filePart);

        response = webResource.path(RESOURCE)
                .type(MediaType.MULTIPART_FORM_DATA_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, multiPartForm);

        // Return code should be 204 or 200 == OK
        status = response.getStatus();
        if (status != 204 && status != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        // 3. FINALIZE video upload

        MultivaluedMap<String, String> finalizeParams = new MultivaluedMapImpl();
        finalizeParams.add("command", "FINALIZE");
        finalizeParams.add("media_id", mediaId);

        response = webResource.path(RESOURCE)
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, finalizeParams);

        status = response.getStatus();
        if (status != 200 && status != 201) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        output = response.getEntity(String.class);
        System.out.println(output);

        // STATUS

		/*
		 * Create a pause of 5 seconds to check on the video upload status
		 * this is a very small video file, adjust according to size
		 * print a dot for each second to the console.
		 */


        Runnable notifier = new Runnable() {
            public void run() {
                System.out.print(".");
            }
        };
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(notifier, 1, 1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5);

        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("command", "STATUS");
        queryParams.add("media_id", mediaId);

        response = webResource.path(RESOURCE)
                .queryParams(queryParams)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        output = response.getEntity(String.class);
        System.out.println(output);

        // kill the scheduler
        scheduler.shutdownNow();

        client.setTrace(true);
        client.setSandbox(false);

        // get the first account
        Account account = client.getAccount(ACCOUNT_ID);



        Video video = new Video(account);
        video.setDescription("test video description");
        video.setTitle("test video title");
        video.setVideo_media_id(mediaId);
        video.save();
        System.out.println("video to string" + video.toString());

    }

}
