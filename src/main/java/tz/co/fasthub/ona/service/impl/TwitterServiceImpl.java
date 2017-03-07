package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.repository.TwitterRepository;
import tz.co.fasthub.ona.service.TwitterService;

/**
 * Created by root on 3/6/17.
 */
@Service
public class TwitterServiceImpl implements TwitterService {

    private final TwitterRepository twitterRepository;

    @Autowired
    public TwitterServiceImpl(TwitterRepository twitterRepository) {
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

}
