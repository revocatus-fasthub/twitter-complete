package tz.co.fasthub.ona.service;

import tz.co.fasthub.ona.domain.Payload;

/**
 * Created by root on 3/6/17.
 */
public interface TwitterService {


    Payload postTweet(Payload payload);

}
