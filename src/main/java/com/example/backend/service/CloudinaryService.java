package com.example.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(File file) throws IOException {
        File tempFile = File.createTempFile("compressed-", file.getName());

        Thumbnails.of(file)
                .size(800, 800)
                .outputQuality(0.8)
                .toFile(tempFile);

        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "folder", "book_cover"
        );
        Map uploadResult = cloudinary.uploader().upload(tempFile, uploadParams);
        return (String) uploadResult.get("secure_url");
    }

    public Map<String, Object> deleteImage(String publicId) throws IOException {
        // Delete image from Cloudinary based on publicId
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
