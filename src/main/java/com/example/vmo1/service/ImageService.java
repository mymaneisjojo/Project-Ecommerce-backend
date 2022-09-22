package com.example.vmo1.service;

import com.example.vmo1.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ImageService {
    List<Image> saveImage(List<Image> image);

}
