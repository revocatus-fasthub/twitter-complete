package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.repository.ImageRepository;
import tz.co.fasthub.ona.repository.TwitterRepository;
import tz.co.fasthub.ona.service.TwitterService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TwitterServiceImpl implements TwitterService {

    private final TwitterRepository twitterRepository;

    @Autowired
    public TwitterServiceImpl(TwitterRepository twitterRepository) {
       this.twitterRepository = twitterRepository;
    }

    @Override
    public Payload savePayload(Payload payload) {
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

    public void updatePayload(Payload payload) {
     twitterRepository.save(payload);
    }

    }



