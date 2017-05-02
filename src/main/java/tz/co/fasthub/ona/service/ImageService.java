package tz.co.fasthub.ona.service;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;

import java.io.IOException;

/**
 * Created by root on 3/9/17.
 */
public interface ImageService {

    Page<Image> findImagePage(Pageable pageable);

    Resource findOneImage(String filename);

    Image createImage(MultipartFile file) throws IOException;

    void deleteImage(String filename, Long id) throws IOException;
}
