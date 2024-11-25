package com.ssafy.haru.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.ssafy.haru.config.EnvConfig;
import com.ssafy.haru.model.response.NewsResponseDto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final EnvConfig envConfig;
    
    private final String NAVER_API_DOMAIN = "openapi.naver.com";
    private final String NAVER_API_PATH = "/v1/search/news.json";
    private String CLIENT_ID; // 발급받은 Client ID
    private String CLIENT_SECRET; // 발급받은 Client Secret
    
    private RestClient restClient;
    
    @PostConstruct
    public void postConstruct() {
    	this.CLIENT_ID = envConfig.getValue("NAVER_SEARCH_API_ID");
    	this.CLIENT_SECRET = envConfig.getValue("NAVER_SEARCH_API_SECRET");
    	this.restClient = RestClient.builder()
    			.defaultHeader("X-Naver-Client-Id", CLIENT_ID)
    			.defaultHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                .build();
    }
    
    public NewsResponseDto searchNews(String query, int size, int page) {
    	log.info(query);
        try {
            return this.restClient
            		.get()
                    .uri(uriBuilder -> uriBuilder.scheme("https")
                    					         .host(NAVER_API_DOMAIN)
                								 .path(NAVER_API_PATH)
                                                 .queryParam("query", query)
                                                 .queryParam("display", size)
                                                 .queryParam("start", page * size + 1)
                                                 .build())
                    .retrieve()
                    .body(NewsResponseDto.class);
        } catch (RestClientException e) {
            throw new RuntimeException("네이버 뉴스 검색 API 호출 실패: " + e.getMessage(), e);
        }
    }
}
