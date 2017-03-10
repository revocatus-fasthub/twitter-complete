package tz.co.fasthub.ona.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.domain.Payload;

import java.io.IOException;

/**
 * Created by root on 3/9/17.
 */
public interface ImageService {

    Resource findOneImage(String filename);

    Image createImage(MultipartFile file) throws IOException;


}
