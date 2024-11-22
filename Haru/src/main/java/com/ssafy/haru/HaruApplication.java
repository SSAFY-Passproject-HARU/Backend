package com.ssafy.haru;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ComponentScan은 @SpringBootApplication이 자동으로 같은 패키지 내에 있는 파일들에 대해서 해줌

@SpringBootApplication
@MapperScan("com.ssafy.haru.model.mapper")
public class HaruApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaruApplication.class, args);
	}
}
