package tz.co.fasthub.ona.service;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.domain.Video;

import java.io.IOException;

/**
 * Created by root on 3/10/17.
 */
public interface VideoService {

    Page<Video> findVideoPage(Pageable pageable);

    Resource findOneVideo(String filename);

    Video createVideo(MultipartFile file) throws IOException;

    void deleteVideo(String filename) throws IOException;
}