package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;


public interface ImageService {

     byte[] getImage(Long id);
     Image createImage(MultipartFile file, Ads ads);


     byte[] updateImage(Long id, MultipartFile file, Authentication authentication);
}
