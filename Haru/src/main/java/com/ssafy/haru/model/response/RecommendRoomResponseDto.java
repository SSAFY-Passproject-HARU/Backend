package com.ssafy.haru.model.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendRoomResponseDto {
    private int roomId;
    private String aptSeq;      
    private BigDecimal price;
    private double area;
    private int roomCount;
    private int bathroomCount;
    private String roomType;
    private int roomFloor;
    private int totalFloors;
}
