package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exception.FileEmptyException;
import ru.skypro.homework.exception.SomeProblemWithFileException;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);


    public void getInformationFromFile(MultipartFile file, Image image) {
        logger.info("Processing ImageServiceImpl:getInformationFromFile()");
        byte[] photo;
        if (file.isEmpty()) {
            throw new FileEmptyException();
        }
        try {
            photo = file.getBytes();
        } catch (IOException e) {
            logger.warn("Some problem with file" + file.getName());
             throw new SomeProblemWithFileException();
        }
        image.setImage(photo);
        image.setMediaType(file.getContentType());
        image.setFileSize(file.getSize());
    }

    @Override
    public Image createImage(MultipartFile file, Ads ads) {
        logger.info("Processing ImageServiceImpl:createImage()");
        Image image = new Image();
        image.setAds(ads);
        getInformationFromFile(file, image);
        return imageRepository.save(image);

    }

    @Override
    public byte[] getImage(Long id) {
        logger.info("Processing ImageServiceImpl:getImage()");
       return  getImageForChange(id).getImage();

    }

    @Override
    public byte[] updateImage(Long id, MultipartFile file, Authentication authentication) {
        logger.info("Processing ImageServiceImpl:updateImage()");
        Image imageForUpdate= getImageForChange(id);
        userService.checkUserPermission(authentication, imageForUpdate.getAds().getUser().getUsername());
        getInformationFromFile(file, imageForUpdate);
        Image image = imageRepository.save(imageForUpdate);
        return image.getImage();
    }

    public Image getImageForChange(long id){
        return imageRepository.findById(id).orElseThrow(FileEmptyException::new);
    }


}