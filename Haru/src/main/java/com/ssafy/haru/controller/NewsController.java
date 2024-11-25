package com.ssafy.haru.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.haru.model.response.NewsResponseDto;
import com.ssafy.haru.service.NewsService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    
    @CrossOrigin(origins = "http://192.168.205.76:5174", allowCredentials="true")
    @GetMapping("/api/news")
    public NewsResponseDto searchNews(@RequestParam String query,
                             @RequestParam(defaultValue = "20") int size,
                             @RequestParam(defaultValue = "0") int page) {
        return newsService.searchNews(query, size, page);
    }
    
}
