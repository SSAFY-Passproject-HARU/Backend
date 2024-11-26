package com.ssafy.haru.model.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecommendRoomRequestDto {
	private BigDecimal avgPrice;
    private double avgArea;
    private double avgRoomCount;
    private double avgBathroomCount;
    private double avgRoomFloor;
    private String sido;
    private String gugun;
    private String dong;
}
