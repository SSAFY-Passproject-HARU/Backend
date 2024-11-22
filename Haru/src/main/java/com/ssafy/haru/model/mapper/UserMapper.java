package com.ssafy.haru.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.haru.model.UserDto;

@Mapper
public interface UserMapper {
	UserDto login(String id, String password);
	int insert(UserDto dto);
	int delete(String id);
	int update(UserDto user);
	UserDto select(String id);
}
