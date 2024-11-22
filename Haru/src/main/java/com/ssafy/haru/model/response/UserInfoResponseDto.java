package com.ssafy.haru.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponseDto {
	String id, name, nickname, email, sido, gugun, dong, role;
}

