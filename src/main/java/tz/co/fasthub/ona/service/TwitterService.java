package tz.co.fasthub.ona.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.Talent;

/**
 * Created by root on 3/6/17.
 */
public interface TwitterService {

    Payload postTweet(Payload payload);
    Page<Payload> findPayloadPage(Pageable pageable);
}
