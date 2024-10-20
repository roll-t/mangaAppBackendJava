package com.example.backend.service;

import com.example.backend.dto.response.ComicResponse;
import com.example.backend.entity.Comic;
import com.example.backend.mapper.ComicMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ComicService {

    ComicMapper comicMapper;

    RestTemplate restTemplate;

    @NonFinal
    @Value("${api.comic.url}")
    protected String apiUrl;

    public ComicResponse getComicDataComicByName(String comicName) {
        String url = String.format("%s/%s", apiUrl, "truyen-tranh/"+comicName);
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

                Map<String, Object> responseBody = response.getBody();

                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                if (data != null) {
                    Comic comic = parseComic(data);

                    return comicMapper.toComicResponse(comic);

                } else {
                    return createEmptyComicResponse();
                }
            } else {
                return createEmptyComicResponse();
            }
        } catch (Exception e) {
            return createEmptyComicResponse();
        }
    }
    private Comic parseComic(Map<String, Object> data) {
        Comic comic = new Comic();
        String domainCDN = (String) data.get("domain_cdn");
        Map<String, Object> itemData = (Map<String, Object>) data.get("item");
        Map<String, Object> seoOnData = (Map<String, Object>) data.get("seoOnPage");
        if (seoOnData != null) {
            Map<String, Object> seoSchema = (Map<String, Object>) seoOnData.get("seoSchema");
            if (seoSchema != null) {
                comic.setThumb((String) seoSchema.get("image"));
            }
        }
        if (itemData != null) {
            String chapterPath = (String) itemData.get("chapter_path");
            comic.setDomain(domainCDN + "/" + chapterPath + "/");
            comic.setTitle((String) itemData.get("name"));
            comic.setDescription((String) itemData.get("content"));
            comic.setCategory((List<Object>) itemData.get("category"));
            List rootChapter = (List) itemData.get("chapters");
            Map<String, Object> dataServer = (Map<String, Object>) rootChapter.get(0);
            if (dataServer != null) {
                comic.setChapters((List<Object>) dataServer.get("server_data"));
            }
        }
        return comic;
    }
    private ComicResponse createEmptyComicResponse() {
        return comicMapper.toComicResponse(new Comic());
    }

    public ResponseEntity<Object> getCategories() {
        String url = String.format("%s/%s", apiUrl,"the-loai");
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> getHome() {
        String url = String.format("%s/%s", apiUrl,"home");
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> getListOfCategory(String categoryName, Integer page) {

        String url = String.format("%s/%s", apiUrl,"danh-sach/"+categoryName+"?page="+page);
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public ResponseEntity<Object> getTypeBySlug(String slug, Integer page) {
        String url = String.format("%s/%s", apiUrl,"the-loai/"+slug+"?page="+page);
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> search() {
        String url = String.format("%s/%s", apiUrl,"tim-kiem");
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> searchBySlug(String slug) {
        String url = String.format("%s/%s", apiUrl,"tim-kiem?keyword="+slug);
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to fetch categories", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
