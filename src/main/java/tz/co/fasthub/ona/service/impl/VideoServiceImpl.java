package tz.co.fasthub.ona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.domain.Video;
import tz.co.fasthub.ona.repository.VideoRepository;
import tz.co.fasthub.ona.service.VideoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Created by root on 3/10/17.
 */

@Service
public class VideoServiceImpl implements VideoService {

    private static String VIDEO_UPLOAD_ROOT = "/var/ona_fasthub/videoUpload-dir";

    private final VideoRepository videoRepository;

    private final ResourceLoader resourceLoader;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, ResourceLoader resourceLoader) {
        this.videoRepository = videoRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Page<Video> findVideoPage(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Resource findOneVideo(String filename){
        return resourceLoader.getResource("file:"+ VIDEO_UPLOAD_ROOT + "/" +filename);
    }

    @Override
    public Video createVideo(MultipartFile videofile) throws IOException {

        if (!videofile.isEmpty()) {
            Files.copy(videofile.getInputStream(), Paths.get(VIDEO_UPLOAD_ROOT, videofile.getOriginalFilename()));

      //      return videoRepository.save(new Video(videofile.getOriginalFilename()));
return null;
        }else {
            return null;
        }
    }

    @Override
    public void deleteVideo(String filename) throws IOException {
        final Video byName = videoRepository.findByName(filename);
        videoRepository.delete(byName);
        Files.deleteIfExists(Paths.get(VIDEO_UPLOAD_ROOT, filename));
    }

}