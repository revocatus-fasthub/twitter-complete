/*
package tz.co.fasthub.ona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.ona.service.StorageService;

import javax.inject.Inject;
import java.io.IOException;


@Controller
public class UploadController {

   private Twitter twitter;

   private ConnectionRepository connectionRepository;

    @Autowired
    private StorageService storageService;

    @Inject
    public UploadController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/home/naamini/Downloads/ona_app/src/main/resources/uploads/";//F://temp//

    @GetMapping("/twitter/upload")
    public String index() {
        return "/twitter/upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/twitter/uploadStatus";
        }

        try {
            if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
                return "redirect:/twitter/upload";
            }

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            Resource path = storageService.loadAsResource(UPLOADED_FOLDER+file.getOriginalFilename());
       //     Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
         //   Files.write(path, bytes);

            twitter.userOperations().getUserProfile();
            //log.error("cannot fetch user");
            Tweet uploadFile = twitter.timelineOperations().updateStatus("hello", path);

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/twitter/uploadStatus";
    }

    @GetMapping("/twitter/uploadStatus")
    public String uploadStatus() {
        return "/twitter/uploadStatus";
    }

}
 */