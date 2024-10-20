package com.example.backend.controller;

import com.example.backend.dto.response.ComicResponse;
import com.example.backend.service.ComicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comic")
@RequiredArgsConstructor
public class ComicController {

    private final ComicService comicService;

    @GetMapping("/detail/{comicName}")
    public ComicResponse getComicByName(@PathVariable String comicName) {
        return comicService.getComicDataComicByName(comicName);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategory() {
        return comicService.getCategories();
    }

    @GetMapping("/home")
    public ResponseEntity<Object> getHome() {
        return comicService.getHome();
    }

    @GetMapping("/list/{type}")
    public ResponseEntity<Object> getListByStatus(@PathVariable String type, @RequestParam(required = false, defaultValue = "1") Integer page) {
        if (page == null) {
            page = 1;
        }
        return comicService.getListOfCategory(type, page);
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<Object> getCategoriesBySlug(@PathVariable String slug, @RequestParam(required = false, defaultValue = "1") Integer page) {
        return comicService.getTypeBySlug(slug,page);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> setSearch() {
        return comicService.search();
    }

    @GetMapping("/search/{slug}")
    public ResponseEntity<Object> setSearchBySlug(@PathVariable String slug) {
        return comicService.searchBySlug(slug);
    }

}
