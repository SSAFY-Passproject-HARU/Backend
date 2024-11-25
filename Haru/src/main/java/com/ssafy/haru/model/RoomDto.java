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
    private int roomId;          // 방 ID
    private String userId;       // 사용자 ID
    private String title;        // 매물 제목
    private String description;  // 매물 설명
    private int price;           // 가격
    private String aptSeq;       // 아파트 시퀀스
    private int favorite;        // 찜 여부
    private double area;         // 평수 (면적)
    private int roomCount;       // 방 개수
    private int bathroomCount;   // 욕실 개수
    private String roomType; // 매물 종류 (예: 아파트, 빌라 등)
    private int roomFloor;   // 매물 층
    private int totalFloors;     // 건물 전체 층
}