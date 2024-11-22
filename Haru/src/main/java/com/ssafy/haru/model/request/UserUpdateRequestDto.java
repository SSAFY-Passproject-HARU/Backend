package com.ssafy.haru.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
	String password, name, nickname, email, sido, gugun, dong;
}
