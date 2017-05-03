package tz.co.fasthub.ona.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.controller.twitter.TwitterUtilities;
import tz.co.fasthub.ona.domain.*;
import tz.co.fasthub.ona.service.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    private final Twitter twitter;


    private  final TwitterService twitterService;

    private final TwitterTalentService twitterTalentService;

    private final ImageService imageService;

    private final PayloadService payloadService;

    private final VideoService videoService;

    private final TalentService talentService;

    final static String DOMAIN = "https://upload.twitter.com";
    final static String RESOURCE = "/1.1/media/upload.json";

    //Save the uploaded file to this folder
    private static String IMAGE_UPLOAD_ROOT = "/var/ona_fasthub/imageUpload-dir";
    private static String VIDEO_UPLOAD_ROOT = "/var/ona_fasthub/videoUpload-dir";

    private static final String BASE_PATH = "/images";
    //private static final String BASE_PATH = "/videos";
    private static final String FILENAME = "{filename:.+}";

    private static final Logger log = LoggerFactory.getLogger(TwitterController.class);

    @Autowired
    public TwitterController(Twitter twitter, TwitterService twitterService, TwitterTalentService twitterTalentService, ImageService imageService, PayloadService payloadService, VideoService videoService, TalentService talentService) {
        this.twitter = twitter;
        this.twitterService = twitterService;
        this.twitterTalentService = twitterTalentService;
        this.imageService = imageService;
        this.payloadService = payloadService;
        this.videoService = videoService;
        this.talentService = talentService;
    }

    @RequestMapping(value = "/messages")
    public String index(Model model, Pageable pageable) throws IOException {
        final Page<Payload> page = twitterService.findPayloadPage(pageable);
        model.addAttribute("page", page);
        if (page.hasPrevious()) {
            model.addAttribute("prev", pageable.previousOrFirst());
        }
        if (page.hasNext()) {
            model.addAttribute("next", pageable.next());
        }
        return "twitter/listMessages";
    }

    @RequestMapping(value = "/images")
    public String listImages(Model model, Pageable pageable){
        final  Page<Image> imagePage = imageService.findImagePage(pageable);
        model.addAttribute("imagePage", imagePage);
        if (imagePage.hasPrevious()) {
            model.addAttribute("prev", pageable.previousOrFirst());
        }
        if (imagePage.hasNext()) {
            model.addAttribute("next", pageable.next());
        }
        return "twitter/listImage";
    }

    @RequestMapping(value = "/videos")
    public String listVideos(Model model, Pageable pageable){
        final  Page<Video> videoPage = videoService.findVideoPage(pageable);
        model.addAttribute("videoPage", videoPage);
        if (videoPage.hasPrevious()) {
            model.addAttribute("prev", pageable.previousOrFirst());
        }
        if (videoPage.hasNext()) {
            model.addAttribute("next", pageable.next());
        }
        return "twitter/listVideos";
    }

    //POSTING TWEET AND AN IMAGE FILE TO USER ACCOUNT
    @RequestMapping(value = "/postTweetImage",method = RequestMethod.POST)
    public String uploadAndTweetImage(@RequestParam("file") MultipartFile file, Payload payload, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("flash.message", "Please select a file!");
            return "redirect:/twitter/renderPostTweet/form";
        }else{
            try {
                Image image = imageService.createImage(file);

                //saving the tweet to DB
                Payload createdPayload =twitterService.savePayload(payload);

                Talent talent = talentService.getTalentById(Integer.parseInt(createdPayload.getScreenName()));

                createdPayload.setImage(image);

                twitterService.savePayload(createdPayload);

                TwitterTalentAccount twitterTalentAccount = twitterTalentService.getTalentByDisplayName(talent.getTwitterScreenName());

                TwitterUtilities.connectTwitter(payload, twitterTalentAccount, imageService.findOneImage(payload.getImage().getName()));

                log.info("Twitter  image  was assumed sent image content type is : "+ file.getContentType());

                redirectAttributes.addFlashAttribute("flash.message", "Image Successfully uploaded");

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("flash.message", "Failed to upload image" + file.getOriginalFilename() + ": " + e);
            }
        }
        return "redirect:/twitter/images";
    }

    //POSTING TWEET AND AN VIDEO FILE TO USER ACCOUNT
    @RequestMapping(value = "/postTweetVideo",method = RequestMethod.POST)
    public String uploadAndTweetVideo(@RequestParam("videofile") MultipartFile videofile, Payload payload, RedirectAttributes redirectAttributes) {
        if (videofile.isEmpty()) {
            redirectAttributes.addFlashAttribute("flash.message", "Please select a video file to post!");
            return "redirect:/twitter/postvideo/form";
        }else{
            try {
                Video video = videoService.createVideo(videofile);
                //saving the tweet to DB
                Payload createdPayload =twitterService.savePayload(payload);

                createdPayload.setVideo(video);
                twitterService.savePayload(createdPayload);

                TweetData tweetData = new TweetData(createdPayload.getMessage());
                tweetData.withMedia(videoService.findOneVideo(video.getName()));


                Tweet tweet = twitter.timelineOperations().updateStatus(tweetData);
                log.info("tweet sent");

                redirectAttributes.addFlashAttribute("flash.message", "Video Successfully uploaded");

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("flash.message", "Failed to upload Video " + videofile.getOriginalFilename() + ": " + e);
            }
            catch (Exception e) {
                redirectAttributes.addFlashAttribute("flash.message", "Uncaught Exception: " + e);
            }
        }
        return "redirect:twitter/videos";
    }

    @RequestMapping(method=RequestMethod.DELETE, value = BASE_PATH + "/" + FILENAME + "/{id}")
    public String deleteImage(@PathVariable String filename, @PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {
        try {
            imageService.deleteImage(filename,id);
            payloadService.deletePayload(id);
            redirectAttributes.addFlashAttribute("flash.message", "Image Successfully deleted " + filename );
        } catch (IOException|RuntimeException e) {
            redirectAttributes.addFlashAttribute("flash.message", "Failed to delete Image " + filename + " => " + e.getMessage());
        }
        return "redirect:/twitter/images";
    }

    @RequestMapping("/renderPostTweet/form")
    public String tweet(Model model){
        List<Talent> talents = talentService.findAll();
        model.addAttribute("talents", talents);
        return "twitter/postTweetImage";
    }



}