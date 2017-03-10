package tz.co.fasthub.ona.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tz.co.fasthub.ona.domain.Image;
import tz.co.fasthub.ona.repository.ImageRepository;
import tz.co.fasthub.ona.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by root on 3/9/17.
 */
@Service
public class ImageServiceImpl implements ImageService{

    private static String UPLOAD_ROOT = "upload-dir";

    private final ImageRepository imageRepository;

    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageServiceImpl(ResourceLoader resourceLoader, ImageRepository imageRepo) {
        this.resourceLoader = resourceLoader;
        imageRepository = imageRepo;
    }

    @Override
    public Page<Image> findImagePage(Pageable pageable) {
            return imageRepository.findAll(pageable);
        }

    @Override
    public Resource findOneImage(String filename) {
        return resourceLoader.getResource("file:"+ UPLOAD_ROOT + "/" +filename);
    }

    @Override
    public Image createImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
            return imageRepository.save(new Image(file.getOriginalFilename()));
        }else {
            return null;
        }
    }

    @Override
    public void deleteImage(String filename) throws IOException {
        final Image byName = imageRepository.findByName(filename);
        imageRepository.delete(byName);
            Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
        }
}
