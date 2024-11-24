package com.ssafy.haru.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomImageDto {
	private int imageId;    
    private int roomId;         
    private String imageUrl;
    
    public void setSaveFile(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
