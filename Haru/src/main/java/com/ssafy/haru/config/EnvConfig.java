package com.ssafy.haru.config;

import org.springframework.stereotype.Component;
import io.github.cdimascio.dotenv.Dotenv;

@Component
public class EnvConfig {
	private final Dotenv dotenv;
	
	// .env 파일 로드
    public EnvConfig() {
        this.dotenv = Dotenv.load(); 
    }
    
    public String getValue(String key) {
        return dotenv.get(key); // 키에 해당하는 값 반환
    }
}
