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
public class HomeDealDto {
    String aptSeq;
    String floor;
    int dealYear;
    int dealMonth;
    int dealDay;
    double areaSize;
    String dealAmount;
}
