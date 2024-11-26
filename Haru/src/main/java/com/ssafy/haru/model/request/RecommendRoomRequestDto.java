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
    private double avgRoomCount;
    private double avgBathroomCount;
    private double avgRoomFloor;
    private String preferredRoomType;
    private String sido;
    private String gugun;
    private String dong;
    
    @Override
    public String toString() {
        return "RecommendRoomRequestDto{" +
                "avgPrice=" + avgPrice +
                ", avgArea=" + avgArea +
                ", avgRoomCount=" + avgRoomCount +
                ", avgBathroomCount=" + avgBathroomCount +
                ", avgRoomFloor=" + avgRoomFloor +
                ", preferredRoomType='" + preferredRoomType + '\'' +
                ", sido='" + sido + '\'' +
                ", gugun='" + gugun + '\'' +
                ", dong='" + dong + '\'' +
                '}';
    }
}
