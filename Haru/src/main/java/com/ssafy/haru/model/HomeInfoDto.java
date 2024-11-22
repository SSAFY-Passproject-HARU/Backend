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
public class HomeInfoDto {
    String sido;
    String gugun;
    String dong;
    String aptSeq;
    String aptName;
    String jibun;
    String latitude;
    String longitude;
}
