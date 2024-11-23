package com.ssafy.haru.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsResponseDto {
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class NaverNewsItemDto {
		private String title;
		
		@JsonAlias("originallink")
		private String originalLink;
		
		private String description;
		
		private String pubDate;
	}
	
	private Long total;
	private Integer start;
	private Integer display;
	private List<NaverNewsItemDto> items;
	
}
