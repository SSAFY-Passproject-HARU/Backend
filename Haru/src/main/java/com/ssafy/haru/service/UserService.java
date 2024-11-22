package com.ssafy.haru.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.haru.model.UserDto;
import com.ssafy.haru.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserMapper userMapper;

	public UserDto login(String id, String password) throws SQLException {
	    if (id == null || password == null) {
	        throw new IllegalArgumentException("id 또는 password는 null일 수 없습니다.");
	    }
	    return userMapper.login(id, hashPassword(password));
	}

	public int insert(UserDto user) throws SQLException {
		user.setPassword(hashPassword(user.getPassword()));
		
		int result = 0;
		try {
			result = userMapper.insert(user);
		} catch (Exception e) {
	        log.error("회원 정보 저장 중 예외 발생", e);
	        throw new RuntimeException("회원 정보 저장에 실패했습니다.", e); 
		}
		return result;
	}

	public int delete(String id) throws SQLException {
	    if (id == null || id.trim().isEmpty()) {
	        throw new IllegalArgumentException("삭제할 id는 null 또는 빈 문자열일 수 없습니다.");
	    }
	    return userMapper.delete(id);
	}

	public int update(UserDto user) throws SQLException {
		String hashedPassword = hashPassword(user.getPassword());
		user.setPassword(hashedPassword);
		
		return userMapper.update(user);
	}

	public UserDto selectUser(String id) throws SQLException {
		return userMapper.select(id);
	}

	// 비밀번호 해시 처리 메서드
	private String hashPassword(String password) {
	    if (password == null || password.isEmpty()) {
	        return password;
	    }
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
	        log.error("SHA-256 알고리즘을 찾을 수 없습니다.", e);
	        throw new RuntimeException("비밀번호 해시 처리 중 오류가 발생했습니다.", e);
		}
	}
}
