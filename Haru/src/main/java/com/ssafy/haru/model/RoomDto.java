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
public class RoomDto {
	private int roomId;          
    private String userId;       
    private String title;        
    private String description;  
    private int price;    
    private String aptSeq;       
    private int favorite;        
}
