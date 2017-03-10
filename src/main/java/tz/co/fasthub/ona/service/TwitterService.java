package tz.co.fasthub.ona.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.domain.Payload;
import tz.co.fasthub.ona.domain.Talent;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 3/6/17.
 */
public interface TwitterService {

    Payload savePayload(Payload payload);

    Page<Payload> findPayloadPage(Pageable pageable);

    List<Payload> listAllTweets();

    void updatePayload (Payload payload);

}
