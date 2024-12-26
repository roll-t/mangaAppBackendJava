package com.example.backend.controller;

import com.example.backend.dto.request.ApiResponse;
import com.example.backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ApiResponse<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        if (!isValidImageFormat(originalFilename)) {
            response.put("url", "image_nothing.png");
            return ApiResponse.<Map<String, String>>builder().result(response).build();
        }

        try {
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);
            String url = cloudinaryService.uploadImage(tempFile);
            response.put("url", url);

            return ApiResponse.<Map<String, String>>builder().result(response).build();
        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "image_nothing.png");
            return ApiResponse.<Map<String, String>>builder().result(response).build();
        }
    }


    @DeleteMapping("/delete/{publicId}")
    public String deleteImage(@PathVariable("publicId") String publicId) {
        try {
            cloudinaryService.deleteImage(publicId.replace("-", "/"));
            return "Image deleted successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error deleting image";
        }
    }

    private boolean isValidImageFormat(String filename) {
        if (filename == null) {
            return false;
        }
        String lowerCaseFilename = filename.toLowerCase();
        return lowerCaseFilename.endsWith(".png") || lowerCaseFilename.endsWith(".jpg") || lowerCaseFilename.endsWith(".jpeg");
    }

}