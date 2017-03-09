package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.repository.TwitterRepository;
import tz.co.fasthub.ona.service.TwitterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by root on 3/6/17.
 */
@Service
public class TwitterServiceImpl implements TwitterService {

    private static String UPLOAD_ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    private final TwitterRepository twitterRepository;

    @Autowired
    public TwitterServiceImpl(ResourceLoader resourceLoader, TwitterRepository twitterRepository) {
        this.resourceLoader = resourceLoader;
        this.twitterRepository = twitterRepository;
    }

    @Override
    public Payload postTweet(Payload payload) {
            return twitterRepository.save(payload);
        }

    @Override
    public Page<Payload> findPayloadPage(Pageable pageable) {
        return twitterRepository.findAll(pageable);
    }

    @Override
    public List<Payload> listAllTweets() {
        return twitterRepository.findAll();
    }

   /*
    public Resource findOneImage(String filename) {
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
    }

    public void createImage(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
            twitterRepository.save(new Payload(file.getOriginalFilename()));
        }
    }
    */
}
