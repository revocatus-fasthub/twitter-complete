package tz.co.fasthub.ona.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Video;

import java.io.IOException;

/**
 * Created by root on 3/10/17.
 */
public interface VideoService {

    Resource findOneVideo(String filename);

    Video createVideo(MultipartFile file) throws IOException;
}