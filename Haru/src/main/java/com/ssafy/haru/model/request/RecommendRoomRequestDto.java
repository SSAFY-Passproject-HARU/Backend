package com.ssafy.haru.model.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendRoomRequestDto {
	private BigDecimal avgPrice;
    private double avgArea;
    private int avgRoomCount;
    private int avgBathroomCount;
    private int avgRoomFloor;
    private String preferredRoomType;
    private String sido;
    private String gugun;
    private String dong;
}
